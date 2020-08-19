package com.example.mehrkalacoroutine.data.repository

import android.os.Environment
import android.util.Log
import android.util.Pair
import com.haroldadmin.cnradapter.NetworkResponse
import com.example.mehrkalacoroutine.data.network.api.ApiInterface
import com.example.mehrkalacoroutine.data.network.model.Address
import com.example.mehrkalacoroutine.data.network.model.Receipt
import com.example.mehrkalacoroutine.data.network.model.ReciverInformation
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.*

class OrdersRepository(
    private val api: ApiInterface
){
    lateinit var address: Address
    lateinit var reciver: ReciverInformation
    lateinit var receiptPdfDirection: String

    suspend fun sendPaymentInfo(refId:String) =
            sendPayment(refId)

    suspend fun getAddress()=
        api.getAddress()
    suspend fun getReciverInfo() =
        api.getInfo()
    suspend fun addAddress(address:String , postNumber:String) =
        api.addAddress(address , postNumber)
    suspend fun addInfo(name:String , number:String) =
        api.addInfo(name , number)
    suspend fun getReceipt() =
        api.getReceipt()

    private suspend fun sendPayment(refId:String ): NetworkResponse<Receipt, Receipt> {
        val result = api.sendPaymentInformation(
            addressId = address.id.toString(),
            reciverId = reciver.id.toString(),
            refId = refId
        )
        when(result){
            is NetworkResponse.Success -> receiptPdfDirection = result.body.metadata.files
        }
        return result
    }
    fun addressIsSet() =
        this::address.isInitialized
    fun reciverIsSet() =
        this::reciver.isInitialized

    fun addressIsValid(): Boolean {
        return addressIsSet() && address != Address()
    }
    fun reciverIsValid(): Boolean {
        return reciverIsSet() && reciver != ReciverInformation()
    }
    suspend fun downloadReceiptPdf():Boolean =  withContext(IO) {
        receiptPdfDirection?.let {
            val response = api.downloadReceipt(receiptPdfDirection)
            when (response) {
                is NetworkResponse.Success -> saveToDisk(response.body, "test.pdf")
                else -> false
            }
        }
    }
    private suspend fun saveToDisk(body: ResponseBody, filename: String): Boolean {
        val TAG = "SAVE_TO_DISK"
        try {
            val destinationFile = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                filename
            )
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                inputStream = body.byteStream()
                outputStream = FileOutputStream(destinationFile)
                val data = ByteArray(4096)
                var count: Int
                var progress = 0
                val fileSize = body.contentLength()
                Log.d(TAG, "File Size=$fileSize")
                while (inputStream.read(data).also { count = it } != -1) {
                    outputStream.write(data, 0, count)
                    progress += count
                    Log.d(
                        TAG,
                        "Progress: " + progress + "/" + fileSize + " >>>> " + progress.toFloat() / fileSize
                    )
                }
                outputStream.flush()
                Log.d(TAG, destinationFile.parent)
                return true
            } catch (e: IOException) {
                e.printStackTrace()
                Log.d(TAG, "Failed to save the file!")
                return false
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d(TAG, "Failed to save the file!")
            return false
        }
    }
}
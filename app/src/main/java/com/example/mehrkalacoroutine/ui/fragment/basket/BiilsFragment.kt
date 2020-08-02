package com.example.mehrkalacoroutine.ui.fragment.basket

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mehrkala.model.Item
import com.example.mehrkalacoroutine.R
import com.example.mehrkalacoroutine.data.network.model.Address
import com.example.mehrkalacoroutine.data.network.model.Receipt
import com.example.mehrkalacoroutine.data.network.model.ReciverInformation

import com.example.mehrkalacoroutine.databinding.BiilsFragmentBinding
import com.example.mehrkalacoroutine.ui.adapter.horizontalRecycler.RecyclerViewAdapter
import com.example.mehrkalacoroutine.ui.adapter.selectableRecycler.SelectableRecyclerAdapter
import com.example.mehrkalacoroutine.ui.base.ScopedFragment
import com.example.mehrkalacoroutine.ui.utils.OnItemSelected
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.android.synthetic.main.biils_fragment.*
import kotlinx.android.synthetic.main.dialog_add_address.*
import kotlinx.android.synthetic.main.dialog_choose_address.*
import kotlinx.android.synthetic.main.dialog_choose_reciver.*
import kotlinx.android.synthetic.main.dialog_reciver_informatoin.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class BiilsFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory : BillsViewModelFactory by instance()

    private lateinit var viewModel: BillsViewModel
    private lateinit var navController:NavController
    // --FOR DATA
    private lateinit var viewBinding: BiilsFragmentBinding
    private lateinit var receiptAdapter: RecyclerViewAdapter<Item>
    private var addresses:List<Address> = listOf()
    private var recivers :List<ReciverInformation> = listOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = BiilsFragmentBinding.inflate(inflater , container , false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(BillsViewModel::class.java)
        initAdapters()
        bindAdapters()
        bindUI()
        UIActions()
    }
    private fun bindUI() = launch{
        when(val callback = viewModel.addresses.await()){
            is NetworkResponse.Success -> addresses = callback.body.address
        }
        when(val callback = viewModel.reciverInformation.await()){
            is NetworkResponse.Success -> recivers = callback.body.data
        }
        val datas = viewModel.receipt.await()
        when(datas){
            is NetworkResponse.Success -> {
                receiptAdapter.datas = datas.body.item.toMutableList()
                viewBinding.receipt = datas.body
            }
        }
    }
    private fun UIActions(){
        fra_bills_add_address.setOnClickListener{
            getAddressDialog()
        }
        fra_bills_add_address_txt.setOnClickListener{
            getAddressDialog()
        }
        fra_bills_add_reciver_info.setOnClickListener{
            getReciverInfo()
        }
        fra_bills_add_reciver_info_txt.setOnClickListener{
            getReciverInfo()
        }
        fra_bills_choose_address.setOnClickListener {
            chooseAddress()
        }
        fra_bills_choose_reciver.setOnClickListener{
            chooseReciverInformation()
        }
//        fra_bills_choose_address.setOnClickListener{
//            chooseAddress()
//            chooseReciverInformation()
//        }
    }
    private fun initAdapters(){
        receiptAdapter = RecyclerViewAdapter()
    }

    private fun getAddressDialog(){
        val dialog = Dialog(activity!!)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_add_address)
            dialog.setCancelable(true)
        }
        val lp = WindowManager.LayoutParams()
        lp.apply {
            copyFrom(dialog.window?.attributes)
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.MATCH_PARENT
        }
        dialog.show()
        dialog.window?.attributes = lp
        dialog.dialog_add_address_submit.setOnClickListener {
            sendAddress(dialog.dialog_add_address.text.toString() , dialog.dialog_add_address_post_number.text.toString())
            dialog.dismiss()
        }
    }
    private fun getReciverInfo(){
        val dialog = Dialog(activity!!)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_reciver_informatoin)
            dialog.setCancelable(true)
        }
        val lp = WindowManager.LayoutParams()
        lp.apply {
            copyFrom(dialog.window?.attributes)
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }
        dialog.show()
        dialog.window?.attributes = lp
        dialog.dialog_reciver_submit.setOnClickListener {
            sendReciverInfo(dialog.dialog_reciver_name.text.toString() , dialog.dialog_reciver_phone_number.text.toString())
            Log.d("TAG" , dialog.dialog_reciver_phone_number.text.toString())
            dialog.dismiss()
        }
    }
    private fun sendAddress(address:String,postNumber:String) = launch {
        when(val callback = viewModel.addAddress(address , postNumber)){
            is NetworkResponse.Success ->
                if(callback.body.metaData.code == "105")
                    Toast.makeText(activity!! , "با موفقیت اضافه شد" , Toast.LENGTH_SHORT).show()
        }
    }
    private fun sendReciverInfo(name:String , phoneNumber:String) =launch {
        when(val callback = viewModel.addReciverInfo(name , phoneNumber)){
            is NetworkResponse.Success ->
                if(callback.body.metaData.code == "105")
                    Toast.makeText(activity!! , "با موفقیت اضافه شد" , Toast.LENGTH_SHORT).show()
        }
    }
    private fun bindAdapters(){
//        lm = LinearLayoutManager(activity)
//        fra_bills_rv.apply {
//            layoutManager = lm
//            adapter = SelectableRecyclerAdapter(generateItems() , object :OnItemSelected<Address>{
//                override fun onItemSelected(item: Address) {
//                    Toast.makeText(activity , "$item" , Toast.LENGTH_SHORT).show()
//                }
//            })
//        }
        fra_bills_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = receiptAdapter
        }
    }
    private fun chooseAddress(){
        val dialog = Dialog(activity!!)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_choose_address)
            dialog.setCancelable(true)
        }
        val lp = WindowManager.LayoutParams()
        lp.apply {
            copyFrom(dialog.window?.attributes)
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }
        dialog.show()
        dialog.window?.attributes = lp
        var city:String =""
        dialog.dialog_choose_address_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SelectableRecyclerAdapter<Address>( addresses , object :OnItemSelected<Address>{
                override fun onItemSelected(item: Address) {
                    city = item.address
                }
            })
        }
        dialog.dialog_choose_address_submit.setOnClickListener {
            GlobalScope.launch(Main) {
                fra_bills_city.text = city
                dialog.dismiss()
            }
        }
    }
    private fun chooseReciverInformation(){
        val dialog = Dialog(activity!!)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_choose_reciver)
            dialog.setCancelable(true)
        }
        val lp = WindowManager.LayoutParams()
        lp.apply {
            copyFrom(dialog.window?.attributes)
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }
        dialog.show()
        dialog.window?.attributes = lp
        var name :String = ""
        dialog.dialog_choose_reciver_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SelectableRecyclerAdapter<ReciverInformation>( recivers , object :OnItemSelected<ReciverInformation>{
                override fun onItemSelected(item: ReciverInformation) {
                    name = item.name
                }
            })
        }
        dialog.dialog_choose_reciver_submit.setOnClickListener {
            GlobalScope.launch(Main) {
                fra_bills_reciver.text = name
                dialog.dismiss()
            }
        }
    }
}

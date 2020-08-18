package com.example.mehrkalacoroutine.ui.fragment.paymentgateway

import android.app.Dialog
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mehrkala.model.Item

import com.example.mehrkalacoroutine.R
import com.example.mehrkalacoroutine.data.network.model.Receipt
import com.example.mehrkalacoroutine.databinding.PaymentGatewayFragmentBinding
import com.example.mehrkalacoroutine.ui.adapter.horizontalRecycler.RecyclerViewAdapter
import com.example.mehrkalacoroutine.ui.adapter.paging.RecyclerAdapter
import com.example.mehrkalacoroutine.ui.base.ScopedFragment
import com.haroldadmin.cnradapter.NetworkResponse
import com.zarinpal.ewallets.purchase.ZarinPal
import kotlinx.android.synthetic.main.dialog_payment_status.*
import kotlinx.android.synthetic.main.dialog_reciver_informatoin.*
import kotlinx.android.synthetic.main.payment_gateway_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class PaymentGatewayFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory:PaymentGatewayViewModelFactory by instance()

    private lateinit var viewModel: PaymentGatewayViewModel
    private lateinit var navController:NavController
    // -- FOR DATA
    private lateinit var viewBinding: PaymentGatewayFragmentBinding
    private lateinit var receiptAdapter :RecyclerViewAdapter<Item>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = PaymentGatewayFragmentBinding.inflate(inflater , container , false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(PaymentGatewayViewModel::class.java)
        initAdapters()
        bindAdapters()
        bindUI()
        UIActions()
        initPayment()
    }
    private fun initAdapters(){
        receiptAdapter = RecyclerViewAdapter<Item>()
    }
    private fun bindAdapters(){
        fra_payment_gateway_rv.apply{
            layoutManager  = LinearLayoutManager(context)
            adapter = receiptAdapter
        }
    }
    private fun bindUI() = launch{
    }
    private fun UIActions(){

    }
    private fun getReceipt(refId:String) = launch {
        val data = viewModel.sendPaymentInfo(refId)
        when(data){
            is NetworkResponse.Success -> {
                receiptAdapter.datas = data.body.item.toMutableList()
                viewBinding.receipt = data.body
                viewBinding.address = viewModel.address()
                viewBinding.reciver = viewModel.reciver()
                viewModel.resetOrderInformation()
            }
        }
    }
    private fun initPayment(){
        val data = activity!!.intent.data
        ZarinPal.getPurchase(context).verificationPayment(data){
                isPaymentSuccess, refID, paymentRequest ->
            if(isPaymentSuccess){
                showPaymentStatus(refID)
                getReceipt(refID)
            }else{
                showPaymentFailed()
            }
        }
    }
    private fun showPaymentStatus(refId: String){
        val dialog = Dialog(activity!!)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_payment_status)
            dialog.setCancelable(true)
        }
        val lp = WindowManager.LayoutParams()
        lp.apply {
            copyFrom(dialog.window?.attributes)
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }
        dialog.dialog_ref_id.text = refId
        dialog.show()
        dialog.window?.attributes = lp
        dialog.dialog_show_receipt.setOnClickListener{
            dialog.dismiss()
        }
        dialog.dialog_exit.setOnClickListener{
            dialog.dismiss()
            activity?.onBackPressed()
        }
    }
    private fun showPaymentFailed(){
        val dialog = Dialog(activity!!)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_payment_fail)
            dialog.setCancelable(true)
        }
        val lp = WindowManager.LayoutParams()
        lp.apply {
            copyFrom(dialog.window?.attributes)
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }
        dialog.show()
        dialog.window?.attributes = lp
        dialog.dialog_exit.setOnClickListener{
            dialog.dismiss()
            activity?.onBackPressed()
        }
    }

}

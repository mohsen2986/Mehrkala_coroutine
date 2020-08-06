package com.example.mehrkalacoroutine.ui.fragment.paymentgateway

import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.example.mehrkalacoroutine.R
import com.example.mehrkalacoroutine.databinding.PaymentGatewayFragmentBinding
import com.example.mehrkalacoroutine.ui.base.ScopedFragment
import com.zarinpal.ewallets.purchase.ZarinPal
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
    private lateinit var viewBinding: PaymentGatewayFragmentBinding

    // --FOR DATA
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
        bindUI()
        UIActions()
        initPayment()
    }
    private fun bindUI() = launch{

    }
    private fun UIActions(){

    }
    private fun initPayment(){
        val data = activity!!.intent.data
        ZarinPal.getPurchase(context).verificationPayment(data){
                isPaymentSuccess, refID, paymentRequest ->
            if(isPaymentSuccess){
                GlobalScope.launch {
                    viewModel.sendPaymentInfo(refID.toString())
                }
                Toast.makeText(context , "$refID" , Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context, "$refID", Toast.LENGTH_LONG).show()
            }
        }
    }

}

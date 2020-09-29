package com.example.mehrkalacoroutine.ui.fragment.basket

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
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
import com.zarinpal.ewallets.purchase.ZarinPal
import kotlinx.android.synthetic.main.biils_fragment.*
import kotlinx.android.synthetic.main.dialog_add_address.*
import kotlinx.android.synthetic.main.dialog_choose_address.*
import kotlinx.android.synthetic.main.dialog_choose_reciver.*
import kotlinx.android.synthetic.main.dialog_reciver_informatoin.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Default
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
    private lateinit var receipt: Receipt
    private val stateList: MutableList<String> = ArrayList()
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
        getStateData()
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
                receipt = datas.body
                fra_bills_progress.visibility = View.GONE
                fra_bills_loading_layout.visibility = View.GONE
            }
        }
    }
    private fun getStateData(){
        GlobalScope.launch (Default){
            stateList.addAll(resources.getStringArray(R.array.states))
            stateList.add("[Select one]")
        }
    }
    private fun UIActions(){
        fra_bills_buy.setOnClickListener{
            navController.navigate(R.id.action_biilsFragment_to_sendBillsBasketFragment)
        }
//        fra_bills_choose_address.setOnClickListener{
//            chooseAddress()
//            chooseReciverInformation()
//        }
    }
    private fun initAdapters(){
        receiptAdapter = RecyclerViewAdapter()
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
    private fun startPayment( cost :Int){
        val purchase = ZarinPal.getPurchase(activity)
        val payment = ZarinPal.getSandboxPaymentRequest()
        payment.merchantID = "71c705f8-bd37-11e6-aa0c-000c295eb8fc"
        payment.description = "صحفه پرداخت مهر کالا"
        payment.amount = cost.toLong()
        payment.setCallbackURL("payment://zarrinpal/")
        purchase.startPayment(payment){
            status, authority, paymentGatewayUri, intent ->
            if (status == 100) {
//                activity!!.startActivity(intent)
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(context!!, paymentGatewayUri)
            }
            else{
                Toast.makeText(context, "خطا در ایجاد درخواست پرداخت" , Toast.LENGTH_LONG).show()
            }
        }
    }
}

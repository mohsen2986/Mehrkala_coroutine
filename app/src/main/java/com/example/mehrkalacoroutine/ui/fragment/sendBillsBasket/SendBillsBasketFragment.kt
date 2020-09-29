package com.example.mehrkalacoroutine.ui.fragment.sendBillsBasket

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mehrkalacoroutine.R
import com.example.mehrkalacoroutine.data.model.PacketType
import com.example.mehrkalacoroutine.data.network.model.Address
import com.example.mehrkalacoroutine.data.network.model.Receipt
import com.example.mehrkalacoroutine.data.network.model.ReciverInformation
import com.example.mehrkalacoroutine.databinding.SendBillsBasketFragmentBinding
import com.example.mehrkalacoroutine.ui.adapter.selectableRecycler.SelectableRecyclerAdapter
import com.example.mehrkalacoroutine.ui.base.ScopedFragment
import com.example.mehrkalacoroutine.ui.utils.OnItemSelected
import com.haroldadmin.cnradapter.NetworkResponse
import com.zarinpal.ewallets.purchase.ZarinPal
import kotlinx.android.synthetic.main.biils_fragment.*
import kotlinx.android.synthetic.main.dialog_add_address.*
import kotlinx.android.synthetic.main.dialog_choose_address.*
import kotlinx.android.synthetic.main.dialog_choose_packet_type.*
import kotlinx.android.synthetic.main.dialog_choose_reciver.*
import kotlinx.android.synthetic.main.dialog_reciver_informatoin.*
import kotlinx.android.synthetic.main.send_bills_basket_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class SendBillsBasketFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: SendBillsBasketViewModelFactory by instance()

    private lateinit var viewModel: SendBillsBasketViewModel
    private lateinit var navController:NavController
    // -- FOR DATA
    private lateinit var viewBinding: SendBillsBasketFragmentBinding
    private var addresses: List<Address> = listOf()
    private var recivers: List<ReciverInformation> = listOf()
    private lateinit var receipt: Receipt
    private val stateList: MutableList<String> = ArrayList()
    private var type: PacketType? = null // packet type
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = SendBillsBasketFragmentBinding.inflate(inflater , container , false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(SendBillsBasketViewModel::class.java)
        bindUI()
        UIActions()
        getStateData()
    }
    private fun bindUI() = launch {
        when(val callback = viewModel.addresses.await()){
            is NetworkResponse.Success -> addresses = callback.body.address
        }
        when(val callback = viewModel.reciverInformation.await()){
            is NetworkResponse.Success ->
                recivers = callback.body.data
        }
        val datas = viewModel.receipt.await()
        when(datas){
            is NetworkResponse.Success -> {
                receipt = datas.body
                viewBinding.receipt = receipt
                disableLoading()
            }
        }
//        recivers.forEach {
//            Log.d(":debug" , "reload${it.name}")
//        }
    }

    private fun initAdapters(){

    }
    private fun getStateData(){
        GlobalScope.launch (Dispatchers.Default){
            stateList.addAll(resources.getStringArray(R.array.states))
            stateList.add("[Select one]")
        }
    }
    private fun UIActions(){
        fra_send_bills_add_address.setOnClickListener{
            getAddressDialog()
        }
        fra_send_bills_add_address_txt.setOnClickListener{
            getAddressDialog()
        }
        fra_send_bills_add_reciver_info.setOnClickListener{
            getReciverInfo()
        }
        fra_send_bills_add_reciver_info_txt.setOnClickListener{
            getReciverInfo()
        }
        fra_send_bills_choose_address.setOnClickListener {
            chooseAddress()
        }
        fra_send_bills_choose_reciver.setOnClickListener{
            chooseReciverInformation()
        }
        fra_send_bills_packet_type.setOnClickListener{
            choosePacketType()
        }
        fra_send_bills_buy.setOnClickListener{
            if(receipt?.receipt.receiptOffer.toInt() > 0){
                if(!viewModel.addressIsValid())
                    Toast.makeText(context , "ادرس  را مشخص کنید." , Toast.LENGTH_LONG).show()
                else if(!viewModel.reciverIsValid())
                    Toast.makeText(context , "گیرنده را مشخص کنید." , Toast.LENGTH_LONG).show()
                else if(type == null)
                    Toast.makeText(context , "نوع پست را مشخص کنید." , Toast.LENGTH_LONG).show()
                else if(viewModel.addressIsValid() && viewModel.reciverIsValid())
                    viewModel.addressIsValid() && viewModel.reciverIsValid()
                else{
                    Toast.makeText(context , "ایجاد خطا در سیستم." , Toast.LENGTH_LONG).show()
                }

            }
            if(receipt?.receipt.receiptOffer.toInt() > 0 && viewModel.addressIsValid() && viewModel.reciverIsValid())
                startPayment(receipt.receipt.receiptOffer.toInt())
            else
                Toast.makeText(context , "ادرس و گیرنده را مشخص کنید" , Toast.LENGTH_LONG).show()

        }
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
//        dialog.show()
//        dialog.window?.attributes = lp
        // TODO BIG DISS
        val adapter = object : ArrayAdapter<String>(dialog.context, android.R.layout.simple_spinner_item, stateList as List<String?>) {
            override fun getCount(): Int {
                return stateList.size - 1 // Truncate the list
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dialog.dialog_state_spinner.adapter = adapter
        dialog.dialog_state_spinner.setSelection(stateList.size-1)
        dialog.dialog_state_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(dialog.context , "u clicked noting!!" , Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(adapter: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if (position != (stateList.size-1))
                    Toast.makeText(dialog.context , "${adapter?.getItemAtPosition(position)}" , Toast.LENGTH_LONG).show()
            }
        }
        //
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
                if(callback.body.metaData.code == "105") {
                    Toast.makeText(activity!!, "با موفقیت اضافه شد", Toast.LENGTH_SHORT).show()
                    updateAdresses()
                }
        }

    }
    private fun sendReciverInfo(name:String , phoneNumber:String) =launch {
        when(val callback = viewModel.addReciverInfo(name , phoneNumber)){
            is NetworkResponse.Success ->
                if(callback.body.metaData.code == "105") {
                    Toast.makeText(activity!!, "با موفقیت اضافه شد", Toast.LENGTH_SHORT).show()
                    updateReciversInformations()
                }
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
        var address:Address? = null
        dialog.dialog_choose_address_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SelectableRecyclerAdapter<Address>( addresses , object :
                OnItemSelected<Address> {
                override fun onItemSelected(item: Address) {
                    address = item
                }
            })
        }
        dialog.dialog_choose_address_submit.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                address?.let {
                    fra_send_bills_city.text = it.address
                    viewModel.setAddress(it)
                }
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
        var reciver: ReciverInformation? = null
        dialog.dialog_choose_reciver_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SelectableRecyclerAdapter<ReciverInformation>( recivers , object :
                OnItemSelected<ReciverInformation> {
                override fun onItemSelected(item: ReciverInformation) {
                    reciver = item
                }
            })
        }
        dialog.dialog_choose_reciver_submit.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                reciver?.let {
                    fra_send_bills_reciver.text = it.name
                    viewModel.setReciver(it)
                }
                dialog.dismiss()
            }
        }
    }
    private fun choosePacketType(){
        val dialog = Dialog(activity!!)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_choose_packet_type)
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

        val packetTypes :MutableList<PacketType> = arrayListOf()
        resources.getStringArray(R.array.packet_types).forEach {
            packetTypes.add(PacketType(it))
        }
        dialog.dialog_choose_packet_type_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SelectableRecyclerAdapter<PacketType>( packetTypes , object :
                OnItemSelected<PacketType> {
                override fun onItemSelected(item: PacketType) {
                    type = item
                }
            })
        }
        dialog.dialog_choose_packet_type_submit.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                type?.let {
                    fra_send_bills_packet_type.text = it.type
                    type?.let {
                        enableLoading()
                            if(type == packetTypes[0])
                                sendPacketInfo(1)
                            else
                                sendPacketInfo(2)
                    }
                }
                dialog.dismiss()
            }
        }
    }
    private fun sendPacketInfo(packetType: Int) = launch {
        when(val callback = viewModel.getPostReceipt( packetType, "خراسان جنوبی")){
            is NetworkResponse.Success -> {
                viewBinding.receipt = callback.body
                disableLoading()
            }
        }
    }
    private fun enableLoading(){
        fra_send_bills_progress.visibility = View.VISIBLE
        fra_send_bills_loading_layout.visibility = View.VISIBLE
    }
    private fun disableLoading(){
        fra_send_bills_progress.visibility = View.GONE
        fra_send_bills_loading_layout.visibility = View.GONE
    }
    private fun updateReciversInformations() = launch{
        when(val callback = viewModel.getReciverInformation()){
            is NetworkResponse.Success ->
                recivers = callback.body.data
        }
    }
    private fun updateAdresses() = launch{
        when(val callback = viewModel.getAddresses()){
            is NetworkResponse.Success ->{
                addresses = callback.body.address
            }
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
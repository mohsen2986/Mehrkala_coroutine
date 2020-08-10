package com.example.mehrkalacoroutine.ui.fragment.showOrdersHistory

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mehrkala.model.Item

import com.example.mehrkalacoroutine.R
import com.example.mehrkalacoroutine.databinding.ShowOrdersHistoryFragmentBinding
import com.example.mehrkalacoroutine.ui.adapter.horizontalRecycler.RecyclerViewAdapter
import com.example.mehrkalacoroutine.ui.adapter.paging.RecyclerAdapter
import com.example.mehrkalacoroutine.ui.base.ScopedFragment
import com.example.mehrkalacoroutine.ui.utils.OnClickHandler
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.android.synthetic.main.show_orders_history_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ShowOrdersHistory : ScopedFragment() , KodeinAware{
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory:ShowOrdersHistoryViewModelFactory by instance()

    private lateinit var viewModel: ShowOrdersHistoryViewModel
    private lateinit var navController:NavController
    private lateinit var viewBinding:ShowOrdersHistoryFragmentBinding
    // -- FOR DATA
    private lateinit var adapter : RecyclerViewAdapter<Item>
    private lateinit var paymentId:String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding =  ShowOrdersHistoryFragmentBinding.inflate(inflater , container , false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController =  Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(ShowOrdersHistoryViewModel::class.java)
        paymentId = arguments?.getString("paymentId")!!
        configureAdapters()
        bindUI()
        UIActions()
    }
    private fun bindUI() = launch{
        val datas = viewModel.getOrderItems(paymentId)
        when(datas){
            is NetworkResponse.Success -> adapter.datas = datas.body.datas.toMutableList()
        }
    }
    private fun UIActions(){

    }
    private fun configureAdapters(){
        adapter = RecyclerViewAdapter()
        adapter.itemType = 2
        adapter.onClick = object: OnClickHandler<Item>{
            override fun onClick(element: Item) {
                val bundle = bundleOf("item" to element)
                navController.navigate(R.id.action_showOrdersHistory_to_showItemDetailsFragment ,
                    bundle
                )
            }
            override fun onClickView(view: View, element: Item) {}
        }
        fra_show_orders_history_rv.adapter = adapter
    }

}

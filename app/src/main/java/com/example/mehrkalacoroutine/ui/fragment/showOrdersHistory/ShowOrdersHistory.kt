package com.example.mehrkalacoroutine.ui.fragment.showOrdersHistory

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
import com.example.mehrkalacoroutine.databinding.ShowOrdersHistoryFragmentBinding
import com.example.mehrkalacoroutine.ui.base.ScopedFragment
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
        Toast.makeText(context , "$id" , Toast.LENGTH_LONG).show()
        bindUI()
        UIActions()
    }
    private fun bindUI() = launch{
        val datas = viewModel.getOrderItems(paymentId)
        Log.i("debug:", "$datas")
    }
    private fun UIActions(){

    }

}

package com.example.mehrkalacoroutine.ui.fragment.splashScreen.loading

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController

import com.example.mehrkalacoroutine.R
import com.example.mehrkalacoroutine.data.database.model.UserInformation
import com.example.mehrkalacoroutine.data.repository.UserInformationRepository
import com.example.mehrkalacoroutine.ui.base.ScopedFragment
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class LoadingFragment : ScopedFragment() , KodeinAware{
    override val kodein: Kodein by closestKodein()
    private  val viewModelFactory: LoadingViewModelFactory by instance()

    private lateinit var viewModel: LoadingViewModel
    private lateinit var navController: NavController
    // todo add dataBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.loading_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(LoadingViewModel::class.java)
        bindUI()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController(view)
    }
    private fun bindUI() = launch{

        val (isLogin , isOnline) = viewModel.loginState.await()
        // show loading
        delay(1_000)
        if(isLogin){
            if (isOnline)
                navController.navigate(R.id.action_loadingFragment_to_mainPageFragment)
            else {
                Toast.makeText(context, "عدم ارتباط با سرور", Toast.LENGTH_SHORT).show()
                activity!!.finish()
            }
        }else{
            navController.navigate(R.id.action_loadingFragment_to_signupFragment)
        }
    }

}

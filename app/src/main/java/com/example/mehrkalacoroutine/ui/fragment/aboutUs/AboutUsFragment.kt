package com.example.mehrkalacoroutine.ui.fragment.aboutUs

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.mehrkalacoroutine.R
import com.example.mehrkalacoroutine.databinding.AboutUsFragmentBinding
import com.example.mehrkalacoroutine.ui.utils.OnItemClickHandler

class AboutUsFragment : Fragment() {

    companion object {
        fun newInstance() = AboutUsFragment()
    }

    private lateinit var viewModel: AboutUsFragmentViewModel
    private lateinit var navController: NavController
    private lateinit var viewBinding:AboutUsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = AboutUsFragmentBinding.inflate(layoutInflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AboutUsFragmentViewModel::class.java)
        onItemClickListeners()
    }

    private fun onItemClickListeners() {
        viewBinding.onClick = object : OnItemClickHandler {
            override fun onClick(view: View) {
                when(view.id){
                    R.id.fra_aboutUS_back ->
                        navController.navigate(R.id.action_aboutUsFragment_to_mainPageFragment)
                }
            }
        }
    }

}
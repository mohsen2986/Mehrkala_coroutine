package com.example.mehrkalacoroutine.ui.fragment.showImage

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.example.mehrkalacoroutine.R
import com.example.mehrkalacoroutine.databinding.ShowImageFragmentBinding
import com.example.mehrkalacoroutine.ui.base.ScopedFragment
import com.example.mehrkalacoroutine.ui.utils.OnItemClickHandler
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ShowImageFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: ShowImageViewModelFactory by instance()
    private lateinit var viewModel: ShowImageViewModel
    private lateinit var navController: NavController
    private lateinit var viewBinding:ShowImageFragmentBinding
    // FOR DATA--
    private lateinit var url:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = ShowImageFragmentBinding.inflate(inflater , container , false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(ShowImageViewModel::class.java)
        url = arguments!!.getString("url").toString()
        bindUI()
        initOnClick()
    }
    private fun bindUI() = launch{
        viewBinding.url = url
    }
    private fun initOnClick(){
        viewBinding.onClick = object: OnItemClickHandler {
            override fun onClick(view: View) {
                activity!!.onBackPressed()
            }
        }
    }

}

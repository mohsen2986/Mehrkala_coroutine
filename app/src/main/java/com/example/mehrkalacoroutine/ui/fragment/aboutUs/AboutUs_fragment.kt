package com.example.mehrkalacoroutine.ui.fragment.aboutUs

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mehrkalacoroutine.R

class AboutUs_fragment : Fragment() {

    companion object {
        fun newInstance() = AboutUs_fragment()
    }

    private lateinit var viewModel: AboutUsFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.about_us_fragment_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AboutUsFragmentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
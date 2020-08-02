package com.example.mehrkalacoroutine.ui.fragment.splashScreen.singup

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.example.mehrkalacoroutine.R
import com.example.mehrkalacoroutine.ui.base.ScopedFragment
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.android.synthetic.main.signup_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class SignupFragment : ScopedFragment() , KodeinAware{
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory:SignUpViewModelFactory by instance()

    private lateinit var viewModel: SignupViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signup_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(SignupViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        bindUI()
        UIActions()
    }
    private fun bindUI() = launch{

//        val state :Boolean = viewModel.signUp("mmoohhsseenn" , "123123")
//        println("debug: $state")

    }
    fun onClick() = launch {
        val state = viewModel.signUp(fra_signup_username.text.toString() , fra_signup_password.text.toString() , fra_signup_phone.text.toString())
        when(state){
            is NetworkResponse.Success ->{
               if(state.body.code.equals("105"))
                   navController.navigate(R.id.action_signupFragment_to_mainPageFragment)
                else
                   Toast.makeText(context , "این نام کاربری وجود دارد",Toast.LENGTH_SHORT).show()
            }
        }
//        if (state){
//            navController.navigate(R.id.action_signupFragment_to_mainPageFragment)
//        }else{
//            Toast.makeText(context , "این نام کاربری وجود دارد",Toast.LENGTH_SHORT).show()
//        }
    }
    fun UIActions(){
        fra_signup_login.setOnClickListener {
            if (checkColumns()){
                if (checkSpaces()&&checkLength()) {
                        onClick()
                }
            }
            else
                Toast.makeText(context , " همه فیلد ها را کامل کنید" , Toast.LENGTH_SHORT).show()
        }
        fra_signup_go_login.setOnClickListener{
            navController.navigate(R.id.action_signupFragment_to_loginFragment)
        }
    }
    private fun checkColumns() :Boolean =
        fra_signup_username.text?.isNotEmpty()!!&&
                fra_signup_phone.text?.isNotEmpty()!! &&
                fra_signup_password.text?.isNotEmpty()!!

    private fun checkSpaces() :Boolean {
        val pattern = "[\\s]+".toRegex()
        if (pattern.containsMatchIn(fra_signup_username.text.toString())){
            Toast.makeText(context , "نام کاربری دارای فاصله است", Toast.LENGTH_SHORT).show()
            return false
        }
        if (pattern.containsMatchIn(fra_signup_password.text.toString())){
            Toast.makeText(context , "رمز عبور دارای فاصله است", Toast.LENGTH_SHORT).show()
            return false
        }
        if (pattern.containsMatchIn(fra_signup_phone.text.toString())){
            Toast.makeText(context , "شماره تلفن دارای فاصله است", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun checkLength() :Boolean {
        var pattern = "[a-zA-Z_0-9]{8,}+".toRegex()
        if(!pattern.matches(fra_signup_username.text.toString())){
            Toast.makeText(context , "نام کاربری دارای طول کم تر از ۸ کاراکتر است" , Toast.LENGTH_SHORT).show()
            return false
        }
        if(!pattern.matches(fra_signup_password.text.toString())){
            Toast.makeText(context , "رمز عبور دارای طول کم تر از ۸ کاراکتر است" , Toast.LENGTH_SHORT).show()
            return false
        }
        pattern = "[0-9]{11}+".toRegex()
        if(!pattern.matches(fra_signup_phone.text.toString())){
            Toast.makeText(context , "شماره تلفن دارای طول ناردست میباشد(۱۱ کاراکتر)" , Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}

package com.example.mehrkalacoroutine.ui.fragment.splashScreen.login

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mehrkalacoroutine.R
import com.example.mehrkalacoroutine.data.network.api.ApiInterface
import com.example.mehrkalacoroutine.ui.base.ScopedFragment
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class LoginFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: LoginViewModelFactory by instance()
    private val service: ApiInterface by instance() // todo test ;

    private lateinit var viewModel: LoginViewModel
    private lateinit var navController: NavController
    // UI animations
    private lateinit var animation: Animation
    private lateinit var fadeOut: Animation
    private lateinit var fadeIn: Animation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(LoginViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        test()
        bindUI()
        anims()
    }
    private fun test(){
        GlobalScope.launch {
            val result = service.login("moshen_123123" , "12341234" ,"login")
            Log.d("TAG" , "$result")
        }
    }
    private fun bindUI() = launch {
        ac_login_show_status.setAnimation(R.raw.waiting)
        ac_login_show_status.setOnClickListener { btnLoginVisible() }
        ac_login_btn.setOnClickListener{
            if (checkColumns())
                onClick()
        }

    }

    private fun anims() {
        animation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_transition_register
        )
        fadeOut =
            AnimationUtils.loadAnimation(context, R.anim.fade_out)
        fadeIn =
            AnimationUtils.loadAnimation(context, R.anim.fade_in)
        ac_login_edt_username.startAnimation(animation)
        ac_login_edt_password.startAnimation(animation)
    }
    private fun btnLoginGone() {
        Handler().postDelayed({
            ac_login_btn.startAnimation(fadeOut)
            ac_login_btn.setVisibility(View.GONE)
        }, 400)
    }
    private fun btnLoginVisible() {
        ac_login_show_status.startAnimation(fadeOut)
        ac_login_show_status.setVisibility(View.GONE)
        Handler().postDelayed({
            ac_login_btn.startAnimation(fadeIn)
            ac_login_btn.setVisibility(View.VISIBLE)
        }, 400)
    }
    private fun showSuccess() {
        ac_login_show_status.setVisibility(View.VISIBLE)
        ac_login_show_status.setAnimation(R.raw.success)
        ac_login_show_status.setRepeatCount(0)
        ac_login_show_status.playAnimation()
    }
    private fun showFailed() {
        ac_login_show_status.setVisibility(View.VISIBLE)
        ac_login_show_status.setAnimation(R.raw.fail)
        ac_login_show_status.setRepeatCount(0)
        ac_login_show_status.playAnimation()
    }
    private fun onClick() = launch {
        val state = viewModel.login(ac_login_edt_username.text.toString() , ac_login_edt_password.text.toString())
        btnLoginGone()
        when (state) {
            is NetworkResponse.Success -> {
                if (state.body.code.equals("105")){
                    showSuccess()
                    delay(2_000)
                    navController.navigate(R.id.action_loginFragment_to_mainPageFragment)
                }
                else{
                    showFailed()
                    delay(3_000)
                    btnLoginVisible()
                    Toast.makeText(context , "این نام کاربری وجود ندارد", Toast.LENGTH_SHORT).show()
                }
            }
            is NetworkResponse.ServerError -> Toast.makeText(context , "اشکال در ارتباظ با سرور" , Toast.LENGTH_SHORT).show()
            is NetworkResponse.NetworkError -> Toast.makeText(context , "اشکال در ارتباظ با سرور" , Toast.LENGTH_SHORT).show()
            is NetworkResponse.UnknownError -> Toast.makeText(context , "اشکال در ارتباظ با سرور" , Toast.LENGTH_SHORT).show()
        }
//        if (state){
//            showSuccess()
//            delay(2_000)
//            navController.navigate(R.id.action_loginFragment_to_mainPageFragment)
//        }else{
//            showFailed()
//            delay(3_000)
//            btnLoginVisible()
//            Toast.makeText(context , "این نام کاربری وجود دارد", Toast.LENGTH_SHORT).show()
//        }
    }
    private fun checkColumns():Boolean{
        if (checkSpaces()&&checkLength())
            return true
        return false
    }
    private fun checkSpaces() :Boolean {
        val pattern = "[\\s]+".toRegex()
        if (pattern.containsMatchIn(ac_login_edt_username.text.toString())){
            Toast.makeText(context , "نام کاربری دارای فاصله است", Toast.LENGTH_SHORT).show()
            return false
        }
        if (pattern.containsMatchIn(ac_login_edt_password.text.toString())){
            Toast.makeText(context , "رمز عبور دارای فاصله است", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun checkLength() :Boolean {
        var pattern = "[a-zA-Z_0-9]{8,}+".toRegex()
        if(!pattern.matches(ac_login_edt_username.text.toString())){
            Toast.makeText(context , "نام کاربری دارای طول کم تر از ۸ کاراکتر است" , Toast.LENGTH_SHORT).show()
            return false
        }
        if(!pattern.matches(ac_login_edt_password.text.toString())){
            Toast.makeText(context , "رمز عبور دارای طول کم تر از ۸ کاراکتر است" , Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}

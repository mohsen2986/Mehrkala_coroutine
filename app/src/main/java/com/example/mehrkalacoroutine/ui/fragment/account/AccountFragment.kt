package com.example.mehrkalacoroutine.ui.fragment.account

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mehrkalacoroutine.R
import com.example.mehrkalacoroutine.databinding.AccountFragmentBinding
import com.example.mehrkalacoroutine.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.account_fragment.*
import kotlinx.android.synthetic.main.dialog_info.*
import kotlinx.android.synthetic.main.dialog_logout.*
import kotlinx.android.synthetic.main.dialog_logout.dialog_no
import kotlinx.android.synthetic.main.dialog_logout.dialog_yes
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class AccountFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory:AccountViewModelFactory by instance()

    private lateinit var viewModel: AccountViewModel
    private lateinit var navController:NavController
    private lateinit var viewBinding:AccountFragmentBinding
    // -- FOR DATA
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = AccountFragmentBinding.inflate(inflater , container , false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(AccountViewModel::class.java)
        bindUI()
        UIActions()
    }
    private fun bindUI() = launch {
        viewBinding.user = viewModel.userInformation()

    }
    private  fun UIActions() {
        fra_account_back.setOnClickListener {
            activity?.onBackPressed()
        }
        fra_account_logout.setOnClickListener {
            logOutDialog()
        }
        fra_account_save_ic.setOnClickListener{
            saveInformationDialog()
        }
        fra_account_save_txt.setOnClickListener{
            saveInformationDialog()
        }
        fra_account_logout_txt.setOnClickListener{
            logOutDialog()
        }
    }
    private fun updateAccount() = launch {
        viewModel.updateUserInformation(fra_account_username.text.toString() ,
                                        fra_account_password.text.toString() ,
                                        fra_account_phone.text.toString())
        activity!!.onBackPressed()
    }
    private fun logOut() = launch {
        viewModel.logOut()
    }
    private fun logOutDialog(){
        val dialog = Dialog(context!!)
            dialog.apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.dialog_logout)
                dialog.setCancelable(true)
            }
        val lp = WindowManager.LayoutParams()
        lp.apply {
            copyFrom(dialog.window?.attributes)
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }
        dialog.show()
        dialog.window?.attributes = lp
        dialog.dialog_no.setOnClickListener {
            dialog.dismiss()
        }
        dialog.dialog_yes.setOnClickListener {
            logOut()
            val i: Intent? = activity!!.baseContext.packageManager
                .getLaunchIntentForPackage(activity!!.baseContext.packageName)
            i?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
            activity!!.finish()
        }
    }
    private fun  saveInformationDialog(){
        val dialog = Dialog(context!!)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_save_account)
            dialog.setCancelable(true)
        }
        val lp = WindowManager.LayoutParams()
        lp.apply {
            copyFrom(dialog.window?.attributes)
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }
        dialog.show()
        dialog.window?.attributes = lp
        dialog.dialog_no.setOnClickListener {
            dialog.dismiss()
        }
        dialog.dialog_yes.setOnClickListener {
            launch {
                updateAccount()
                dialog.dismiss()
            }

        }
    }
}

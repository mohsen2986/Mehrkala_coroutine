package com.example.mehrkalacoroutine.ui.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.mehrkalacoroutine.R
import com.example.mehrkalacoroutine.data.network.Service
import com.example.mehrkalacoroutine.data.network.api.ApiInterface
import com.zarinpal.ewallets.purchase.ZarinPal
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity() , KodeinAware{
    override val kodein: Kodein by closestKodein()
    val service: ApiInterface by instance()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this , R.id.ac_main_fragment)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }
}

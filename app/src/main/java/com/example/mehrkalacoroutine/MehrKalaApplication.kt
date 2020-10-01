package com.example.mehrkalacoroutine

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.example.mehrkalacoroutine.data.database.UserInformationDatabase
import com.example.mehrkalacoroutine.data.database.UserInformationDatabase_Impl
import com.example.mehrkalacoroutine.data.database.model.UserInformation
import com.example.mehrkalacoroutine.data.network.ApiOkHttpClient
import com.example.mehrkalacoroutine.data.network.Service
import com.example.mehrkalacoroutine.data.network.api.ApiInterface
import com.example.mehrkalacoroutine.data.repository.ItemRepository
import com.example.mehrkalacoroutine.data.repository.OrdersRepository
import com.example.mehrkalacoroutine.data.repository.TokenRepository
import com.example.mehrkalacoroutine.data.repository.UserInformationRepository
import com.example.mehrkalacoroutine.ui.fragment.ShowItemDetailsFragment.ShowItemDetailsFragmentViewModelFactory
import com.example.mehrkalacoroutine.ui.fragment.aboutUs.AboutUsViewModelFactory
import com.example.mehrkalacoroutine.ui.fragment.account.AccountViewModelFactory
import com.example.mehrkalacoroutine.ui.fragment.basket.BillsViewModelFactory
import com.example.mehrkalacoroutine.ui.fragment.mainPage.MainPageViewModelFactory
import com.example.mehrkalacoroutine.ui.fragment.paymentgateway.PaymentGatewayViewModelFactory
import com.example.mehrkalacoroutine.ui.fragment.sendBillsBasket.SendBillsBasketViewModelFactory
import com.example.mehrkalacoroutine.ui.fragment.showImage.ShowImageViewModelFactory
import com.example.mehrkalacoroutine.ui.fragment.showItems.ShowItemsViewModleFactory
import com.example.mehrkalacoroutine.ui.fragment.showOrdersHistory.ShowOrdersHistoryViewModel
import com.example.mehrkalacoroutine.ui.fragment.showOrdersHistory.ShowOrdersHistoryViewModelFactory
import com.example.mehrkalacoroutine.ui.fragment.splashScreen.loading.LoadingViewModelFactory
import com.example.mehrkalacoroutine.ui.fragment.splashScreen.login.LoginViewModelFactory
import com.example.mehrkalacoroutine.ui.fragment.splashScreen.singup.SignUpViewModelFactory
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.bindings.Singleton
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MehrKalaApplication(): MultiDexApplication() , KodeinAware{
    override val kodein:Kodein = Kodein.lazy {
        import(androidXModule(this@MehrKalaApplication))

        bind() from singleton { UserInformationDatabase(instance()) }
        bind() from singleton {instance<UserInformationDatabase>().userInformationDao()}
        bind() from singleton { UserInformationRepository(instance() , instance()) }
        bind() from singleton { TokenRepository(instance())}
        bind() from singleton { ItemRepository(instance())}
        bind() from singleton { OrdersRepository(instance()) }
        bind<OkHttpClient>() with singleton {ApiOkHttpClient(instance()).invoke()}
        bind<ApiInterface>() with singleton { Service(instance()).invoke()}

        bind() from provider { LoadingViewModelFactory(instance()) }
        bind() from provider { SignUpViewModelFactory(instance())}
        bind() from provider { LoginViewModelFactory(instance())}
        bind() from provider { MainPageViewModelFactory(instance())}
        bind() from provider { ShowItemsViewModleFactory(instance(),instance() , instance())}
        bind() from provider { ShowItemDetailsFragmentViewModelFactory(instance())}
        bind() from provider { ShowImageViewModelFactory() }
        bind() from provider { AccountViewModelFactory(instance()) }
        bind() from provider { BillsViewModelFactory(instance())}
        bind() from provider { PaymentGatewayViewModelFactory(instance())}
        bind() from provider { ShowOrdersHistoryViewModelFactory(instance()) }
        bind() from provider { SendBillsBasketViewModelFactory(instance()) }
        bind() from provider { AboutUsViewModelFactory() }
    }
}
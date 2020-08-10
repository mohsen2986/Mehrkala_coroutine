package com.example.mehrkalacoroutine.ui.fragment.showItems

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.arlib.floatingsearchview.FloatingSearchView
import com.example.mehrkala.model.Item
import com.example.mehrkalacoroutine.R
import com.example.mehrkalacoroutine.data.network.NetworkState
import com.example.mehrkalacoroutine.data.network.model.Category
import com.example.mehrkalacoroutine.data.network.model.OrdersHistory
import com.example.mehrkalacoroutine.ui.adapter.horizontalRecycler.RecyclerViewAdapter
import com.example.mehrkalacoroutine.ui.adapter.paging.RecyclerAdapter
import com.example.mehrkalacoroutine.ui.base.ScopedFragment
import com.example.mehrkalacoroutine.ui.utils.OnClickHandler
import kotlinx.android.synthetic.main.dialog_info.*
import kotlinx.android.synthetic.main.showitems_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class ShowitemsFragment : ScopedFragment() , KodeinAware  , RecyclerAdapter.OnClickListener{
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: ShowItemsViewModleFactory by instance()
    // FOR DATA ---
    private lateinit var adapter : RecyclerAdapter<Any>
    private val categoryAdapter = RecyclerViewAdapter<Category>()
    private lateinit var viewModel: ShowitemsViewModel
    private lateinit var navController:NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.showitems_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(ShowitemsViewModel::class.java)
        configViewModel(arguments!!.getString("query").toString())
        configureObservables()
        configureRecyclerView()
        configureSearchView()
        bindUI()
    }

    private fun configureRecyclerView() {
        // show items rv
        adapter =
            RecyclerAdapter(this)

        adapter.onClickHandler =
                object : OnClickHandler<Any> {
                    override fun onClick(element: Any) {
                        when(element) {
                            is OrdersHistory ->{
                                val bundle = bundleOf("paymentId" to element.id)
                                navController.navigate(
                                    R.id.action_showitemsFragment_to_showOrdersHistory ,
                                    bundle
                                )
                            }
                            is Item -> {
                                launch {
                                    viewModel.visitItem(element.id)
                                }
                                val bundle = bundleOf("item" to element)
                                navController.navigate(
                                    R.id.action_showitemsFragment_to_showItemDetailsFragment,
                                    bundle
                                )
                            }
                        }
                    }

                    override fun onClickView(view: View, element: Any) {
                        when(element) {
                            is Item -> {
                                when (view.id) {
                                    R.id.row_basket_plus -> {
                                        element.setCountItem(element.count + 1)
                                        launch { viewModel.addItemCount(element.id) }
                                    }
                                    R.id.row_basket_minus -> {
                                        if (element.count > 1) {
                                            element.setCountItem(element.count - 1)
                                            launch { viewModel.minusItemCount(element.id) }
                                        }
                                    }
                                    R.id.row_basket_delete -> {
                                        DeleteDialog(element.id)
                                    }
                                }
                            }
                        }
                    }
        }

        fra_show_items_rv.adapter = adapter

        fra_show_items_rv.apply{
            addItemDecoration(object: RecyclerView.ItemDecoration(){
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    with(outRect){
                        top = 10
                        left = 8
                        right = 8
                        bottom = 10
                    }
                }
            })
        }
        // -- category adapter
        categoryAdapter.onClick = object :OnClickHandler<Category>{
            override fun onClick(element: Category) {
                configViewModel(element.category_id)
                fra_show_items_drawer.closeDrawers()
            }
            override fun onClickView(view: View, element: Category) {}
        }
    }

    private fun configureObservables() {
        viewModel.networkState.observe(viewLifecycleOwner, Observer { adapter.updateNetworkState(it) })
        viewModel.users.observe(viewLifecycleOwner, Observer { adapter.submitList(it as PagedList<Any>) })
    }

    private fun bindUI() = launch{
        fra_show_items_sr.setOnRefreshListener {
            viewModel.refreshAllList()
            updateCategories()
            fra_show_items_sr.isRefreshing = false
        }
        // categories
        val categories = mutableListOf<Category>()
        categories.add(Category(0 , "همه محصولات" , "product"))
        categories.addAll(viewModel.categories.await())
        categoryAdapter.datas = categories

        fra_show_details_buy.setOnClickListener {
//            showBasket()
            if(fra_show_items_rv.getChildAt(0) != null)
                navController.navigate(R.id.action_showitemsFragment_to_biilsFragment)
            else
                Toast.makeText(context , "کالایی د ر سبد خرید وجود ندارد." , Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateCategories() = launch{
        val categories = mutableListOf<Category>()
        categories.add(Category(0 , "همه محصولات" , "product"))
        categories.addAll(viewModel.categories.await())
        categoryAdapter.datas = categories
    }
    private fun configViewModel(bundle:String){
        viewModel.fetchQuery(bundle)
        when(bundle){
            "basket" -> {
                fra_show_items_drawer.visibility = View.VISIBLE
                fra_show_items_search.setLeftActionMode(FloatingSearchView.LEFT_ACTION_MODE_SHOW_SEARCH)
            }
            "payment" ->{
//                fra_show_items_drawer.visibility = View.INVISIBLE
                fra_show_items_search.visibility = View.INVISIBLE
                fra_show_details_buy.visibility = View.GONE
            }
            "search" -> {
                fra_show_items_search.setSearchFocused(true)
                fra_show_details_buy.visibility = View.GONE
            }
            else ->
                fra_show_details_buy.visibility = View.GONE
        }


    }
    override fun onRefresh() {
    }

    override fun whenListIsUpdated(size: Int, networkState: NetworkState?) {
    }
     private fun  DeleteDialog(id:Int){
        val dialog = Dialog(context!!)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_info)
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
                 viewModel.deleteItem(id)
                 viewModel.refreshAllList()
                 dialog.dismiss()
             }

         }
    }
    private fun configureSearchView(){
        fra_show_items_search.attachNavigationDrawerToMenuButton(fra_show_items_drawer)
        fra_show_items_categories_rv.apply {
            adapter = categoryAdapter

        }
        fra_show_items_search.setOnQueryChangeListener { oldQuery, newQuery ->
            if (newQuery.contains("\n"))
                fra_show_items_search.setSearchFocused(false)
            configViewModel(newQuery)

        }

    }

}

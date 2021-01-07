package com.example.mehrkalacoroutine.ui.fragment.mainPage

import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mehrkala.model.Boarder
import com.example.mehrkala.model.Item

import com.example.mehrkalacoroutine.R
import com.example.mehrkalacoroutine.data.network.model.CategoryImage
import com.example.mehrkalacoroutine.databinding.MainPageFragmentBinding
import com.example.mehrkalacoroutine.ui.adapter.imageSlider.ImageSliderAdapter
import com.example.mehrkalacoroutine.ui.adapter.horizontalRecycler.RecyclerViewAdapter
import com.example.mehrkalacoroutine.ui.base.ScopedFragment
import com.example.mehrkalacoroutine.ui.utils.OnClickHandler
import com.example.mehrkalacoroutine.ui.utils.OnItemClickHandler
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.android.synthetic.main.main_page_fragment.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class MainPageFragment : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: MainPageViewModelFactory by instance()

    private lateinit var viewModel: MainPageViewModel
    private lateinit var navController: NavController
    private lateinit var viewBinding: MainPageFragmentBinding

    // FOR DATA--
    private lateinit var imageSliderAdapter: ImageSliderAdapter<Item>
    private lateinit var topItemRecyclerAdapter: RecyclerViewAdapter<Item>
    private lateinit var topSalesRecyclerAdapter: RecyclerViewAdapter<Item>
    private lateinit var categoryRecyclerAdapter: RecyclerViewAdapter<CategoryImage>
    private lateinit var offersRecyclerViewAdapter: RecyclerViewAdapter<Item>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = MainPageFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainPageViewModel::class.java)
        initAdapters()
        bindAdapters()
        onItemClickListeners()
        bindUI()
    }


    private fun bindUI() = launch {
        val datas = viewModel.topSales.await()
        // ImageSlider
        imageSliderAdapter.datas = datas!!
        // TopItems
        Log.d("_debug", "${viewModel.boarderItems.await()}")
        topItemRecyclerAdapter.boarder = viewModel.boarderItems?.await()!![0]
        topItemRecyclerAdapter.datas = viewModel.newItems.await()?.toMutableList()!!
//        Log.d("_debug", "${viewModel.newItems.await()}")
        // TopSales
        Log.d("_debug", "${viewModel.topSales.await()}")
        topSalesRecyclerAdapter.boarder = viewModel.boarderItems.await()!![1]
        topSalesRecyclerAdapter.datas = viewModel.topSales.await()?.toMutableList()!!
        // category
        categoryRecyclerAdapter.datas = viewModel.category.await().toMutableList()
        // OFFERS
        offersRecyclerViewAdapter.datas = viewModel.offers.await() as MutableList<Item>
    }

    private fun initAdapters() {
        // IMAGE SLIDER ADAPTER
        imageSliderAdapter =
            ImageSliderAdapter()
        imageSliderAdapter.onClick = object : OnClickHandler<Item> {
            override fun onClick(element: Item) {
                val bundle = bundleOf("item" to element)
                navController.navigate(
                    R.id.action_mainPageFragment_to_showItemDetailsFragment,
                    bundle
                )
            }

            override fun onClickView(view: View, element: Item) {
            }
        }
        // TOP ITEM ADAPTER
        topItemRecyclerAdapter =
            RecyclerViewAdapter()
        topItemRecyclerAdapter.onClick = object : OnClickHandler<Item> {
            override fun onClick(element: Item) {
                val bundle = bundleOf("item" to element)
                navController.navigate(
                    R.id.action_mainPageFragment_to_showItemDetailsFragment,
                    bundle
                )
            }

            override fun onClickView(view: View, element: Item) {

            }
        }
        topItemRecyclerAdapter.onBoarderClick = object : OnClickHandler<Boarder> {
            override fun onClick(element: Boarder) {
                val bundle = bundleOf("query" to "topItem")
                navController.navigate(
                    R.id.action_mainPageFragment_to_showitemsFragment,
                    bundle
                )
            }

            override fun onClickView(view: View, element: Boarder) {

            }
        }
        // TOP SALES ADAPTER
        topSalesRecyclerAdapter =
            RecyclerViewAdapter()
        topSalesRecyclerAdapter.onClick = object : OnClickHandler<Item> {
            override fun onClick(element: Item) {
                val bundle = bundleOf("item" to element)
                navController.navigate(
                    R.id.action_mainPageFragment_to_showItemDetailsFragment,
                    bundle
                )
            }

            override fun onClickView(view: View, element: Item) {

            }
        }
        topSalesRecyclerAdapter.onBoarderClick = object : OnClickHandler<Boarder> {
            override fun onClick(element: Boarder) {
                val bundle = bundleOf("query" to "topSale")
                navController.navigate(
                    R.id.action_mainPageFragment_to_showitemsFragment,
                    bundle
                )
            }

            override fun onClickView(view: View, element: Boarder) {
            }
        }
        // CATEGORY
        categoryRecyclerAdapter = RecyclerViewAdapter()
        categoryRecyclerAdapter.onClick = object : OnClickHandler<CategoryImage> {
            override fun onClick(element: CategoryImage) {
                val bundle = bundleOf("query" to "${element.category_id}")
                navController.navigate(
                    R.id.action_mainPageFragment_to_showitemsFragment,
                    bundle
                )
            }

            override fun onClickView(view: View, element: CategoryImage) {}
        }

//     OFFERS
        offersRecyclerViewAdapter = RecyclerViewAdapter()
        offersRecyclerViewAdapter.itemType = 3;
        offersRecyclerViewAdapter.onClick = object : OnClickHandler<Item> {
            override fun onClick(element: Item) {
                val bundle = bundleOf("item" to element)
                navController.navigate(
                    R.id.action_mainPageFragment_to_showItemDetailsFragment,
                    bundle
                )
            }

            override fun onClickView(view: View, element: Item) {}
        }
    }

    private fun bindAdapters() {
        // -- TOP BOARDER IMAGE SLIDER
        fra_main_image_slider.apply {
            sliderAdapter = imageSliderAdapter
        }
        // -- TOP ITEM RV
        fra_main_top_rv.apply {
            adapter = topItemRecyclerAdapter
            layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    with(outRect) {
                        top = 2
                        left = 10
                        right = 10
                        bottom = 2
                    }
                }
            })
        }
        // TOP SALES RV
        fra_main_top_sale_rv.apply {
            adapter = topSalesRecyclerAdapter
            layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    with(outRect) {
                        top = 2
                        left = 10
                        right = 10
                        bottom = 2
                    }
                }
            })
        }
        // CATEGORY
        fra_main_category_rv.apply {
            adapter = categoryRecyclerAdapter
        }
        // OFFERS RV
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        fra_main_offers_rv.apply {
            adapter = offersRecyclerViewAdapter
            layoutManager = GridLayoutManager(
                activity!!,
                if ((displayMetrics.widthPixels / displayMetrics.xdpi) > 4) 3 else 2
            )
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    with(outRect) {
                        top = 17
                        left = 22
                        right = 22
                        bottom = 17
                    }
                }
            })
        }
    }

    private fun onItemClickListeners() {
        viewBinding.onClick = object : OnItemClickHandler {
            override fun onClick(view: View) {
                when (view.id) {
                    R.id.fra_main_account,
                    R.id.fra_main_account_text ->
                        navController.navigate(R.id.action_mainPageFragment_to_accountFragment)

                    R.id.fra_main_basket,
                    R.id.fra_main_basket_text -> {
                        val bundle = bundleOf("query" to "basket")
                        navController.navigate(
                            R.id.action_mainPageFragment_to_showitemsFragment,
                            bundle
                        )
                    }
                    R.id.fra_main_all_items,
                    R.id.fra_main_all_items_text -> {
                        if (fra_main_category_rv.visibility == View.VISIBLE)
                            collapse(fra_main_category_rv)
                        else
                            expandAnimation(fra_main_category_rv)
                    }
                    R.id.fra_main_search -> {
                        val bundle = bundleOf("query" to "search")
                        navController.navigate(
                            R.id.action_mainPagefragment_to_showItemsFragment_search,
                            bundle
                        )
                    }

                    R.id.fra_main_aboutUs,
                    R.id.fra_main_aboutUs_text -> {
                        navController.navigate(R.id.action_mainPageFragment_to_aboutUsFragment)
                    }
                }
            }
        }
    }

    private fun expandAnimation(view: View) {
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val actualheight = view.measuredHeight

        val animation: Animation = object : Animation() {
            override fun applyTransformation(
                interpolatedTime: Float,
                t: Transformation
            ) {
                view.layoutParams.height =
                    if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (actualheight * interpolatedTime).toInt()
                view.requestLayout()
            }
        }
        animation.duration =
            ((actualheight / view.context.resources.displayMetrics.density) * 2).toLong()
        view.layoutParams.height = 0
        view.startAnimation(animation)
        GlobalScope.launch(Main) {
            delay(200)
            view.visibility = View.VISIBLE
        }
//        view.visibility = View.VISIBLE
    }

    private fun collapse(view: View) {
        val actualHeight = view.measuredHeight
        val animation: Animation = object : Animation() {
            override fun applyTransformation(
                interpolatedTime: Float,
                t: Transformation
            ) {
                if (interpolatedTime == 1f) {
                    view.visibility = View.GONE
                } else {
                    view.layoutParams.height =
                        actualHeight - (actualHeight * interpolatedTime).toInt()
                    view.requestLayout()
                }
            }
        }
        animation.duration = (actualHeight / view.context.resources.displayMetrics.density).toLong()
        view.startAnimation(animation)
    }

}


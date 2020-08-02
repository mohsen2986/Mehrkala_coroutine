package com.example.mehrkalacoroutine.ui.fragment.ShowItemDetailsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mehrkala.model.Item

import com.example.mehrkalacoroutine.R
import com.example.mehrkalacoroutine.databinding.ShowItemDetailsFragmentBinding
import com.example.mehrkalacoroutine.ui.adapter.imageSlider.ImageSliderAdapter
import com.example.mehrkalacoroutine.ui.base.ScopedFragment
import com.example.mehrkalacoroutine.ui.utils.OnClickHandler
import com.example.mehrkalacoroutine.ui.utils.OnItemClickHandler
import kotlinx.android.synthetic.main.show_item_details_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ShowItemDetailsFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private  val viewModelFactory: ShowItemDetailsFragmentViewModelFactory by instance()
    private lateinit var viewModel: ShowItemDetailsViewModel
    // --FOR DATA
    private lateinit var item:Item
    private lateinit var imageSliderAdapter: ImageSliderAdapter<String>
    private lateinit var viewBinding:ShowItemDetailsFragmentBinding
    private lateinit var navController:NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = ShowItemDetailsFragmentBinding.inflate(inflater , container , false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(ShowItemDetailsViewModel::class.java)
        // GET DATA FROM ARGS
        item = arguments?.getParcelable("item")!!
        initAdapters()
        configureRecyclerView()
        initOnClickListeners()
        bindUI()
    }
    private fun bindUI() = launch{
        viewBinding.item = item
    }
    private fun configureRecyclerView(){
        fra_show_details_image_slider.apply{
            sliderAdapter = imageSliderAdapter
        }
    }
    private fun initAdapters(){
        imageSliderAdapter =
            ImageSliderAdapter()
        imageSliderAdapter.onClick = object: OnClickHandler<String> {
            override fun onClick(element: String) {
                val bundle = bundleOf("url" to element)
                navController.navigate(R.id.action_showItemDetailsFragment_to_showImageFragment ,
                    bundle
                )
            }

            override fun onClickView(view: View, element: String) {

            }
        }
        imageSliderAdapter.datas = listOf(item.image , item.imageTwo , item.imageThree)
    }
    private fun initOnClickListeners(){
        viewBinding.onClick = object:OnItemClickHandler{
            override fun onClick(view: View) {
                when(view.id){
                    R.id.fra_show_details_back -> activity!!.onBackPressed()
                    R.id.floatingActionButton -> {
                        viewModel.addToBasket(item.id)
                        Toast.makeText(context , "محصول به سبد خرید اضافه شد.", Toast.LENGTH_SHORT).show()
                    }
                    R.id.fra_show_items_bag ->{
                        val bundle = bundleOf("query" to "basket")
                        navController.navigate(R.id.action_showItemDetailsFragment_to_showItemsFragment ,
                            bundle
                        )
                    }
                }
            }
        }
    }

}

package com.example.dailybruh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dailybruh.R
import com.example.dailybruh.adapters.VerticalPagerAdapter
import com.example.dailybruh.const.constNews
import com.example.dailybruh.database.Database
import com.example.dailybruh.databinding.FragmentNewsPageBinding
import com.example.dailybruh.enum.From
import com.example.dailybruh.enum.Sort
import com.example.dailybruh.extension.disableView
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.fragment.dialog.FragmentDialogSearch
import com.example.dailybruh.viewmodel.NewsViewModel
import com.example.dailybruh.web.from
import com.example.dailybruh.web.recentRequest
import com.example.dailybruh.web.sorting
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch

class FragmentNewsPage : StandardFragment<FragmentNewsPageBinding>() {

    private val viewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsPageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewpagerMain.apply {
                val database = when(Firebase.auth.currentUser){
                    null -> Database().newInstance(lifecycleOwner = viewLifecycleOwner)
                    else -> Database().newInstance(Firebase.auth.currentUser!!.phoneNumber!!,viewLifecycleOwner)
                }

                viewLifecycleOwner.lifecycleScope.launch(CoroutineName("setNews")) {
                        viewModel.getNews(recentRequest!!.request)
                }
                viewModel.status.observe(viewLifecycleOwner) {
                    adapter = VerticalPagerAdapter(database,parentFragmentManager,viewLifecycleOwner.lifecycle,viewModel.news)
                }
            }
            profileButton.setOnClickListener {
                view.disableView()
                when (Firebase.auth.currentUser) {
                    null -> view.navigateTo(R.id.newspage_to_auth_phone)
                    else -> view.navigateTo(R.id.newspage_to_profile)
                }
            }
            navMenuButton.setOnClickListener {
                FragmentDialogSearch(viewModel).show(childFragmentManager,"dialog_search")
            }
            popularFilterField.apply {
                setAdapter(R.array.popular_filter)
                setOnItemClickListener { _, _, clickedItem, _ ->
                    when (clickedItem) {
                        0 -> {
                            changeSortParam(Sort.POPULARITY)
                        }

                        1 -> {
                            changeSortParam(Sort.PUBLISHEDAT)
                        }

                        2 -> {
                            changeSortParam(Sort.RELEVANCY)
                        }
                    }
                }
            }

            dateFilterField.apply {
                setAdapter(R.array.date_filter)
                setOnItemClickListener { _,_,clickedItem,_ ->
                    when(clickedItem) {
                        0 -> {
                            changeFromParam(From.FROM_TODAY)
                        }
                        1 -> {
                            changeFromParam(From.FROM_WEEK)
                        }
                        2 -> {
                            changeFromParam(From.FROM_MONTH)
                        }
                    }
                }
            }
        }
    }

    private fun AutoCompleteTextView.setAdapter(arrayId: Int) {
        this.apply {
            setDropDownBackgroundDrawable(ResourcesCompat.getDrawable(resources,R.drawable.item_filter_background_inset,null))
        }.setAdapter(ArrayAdapter(requireContext(),R.layout.menu_list_item,resources.getStringArray(arrayId)))

    }
    private fun changeSortParam(sort: Sort) {
        sorting(sort.get())
        recentRequest!!.changeSort(sort.getParam())
        viewModel.getNews(recentRequest!!.request)
    }
    private fun changeFromParam(from: From) {
        from(from.get())
        recentRequest!!.changeFrom(from.getParam())
        viewModel.getNews(recentRequest!!.request)
    }
}
package com.example.dailybruh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.dailybruh.R
import com.example.dailybruh.adapters.VerticalPagerAdapter
import com.example.dailybruh.database.Database
import com.example.dailybruh.databinding.FragmentNewsPageBinding
import com.example.dailybruh.dataclasses.News
import com.example.dailybruh.enum.From
import com.example.dailybruh.enum.Sort
import com.example.dailybruh.extension.disableView
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.fragment.dialog.search.FragmentDialogSearch
import com.example.dailybruh.interfaces.MainPageView
import com.example.dailybruh.presenter.MainPagePresenter
import com.example.dailybruh.viewmodel.DatabaseViewModel
import com.example.dailybruh.viewmodel.NewsViewModel
import com.example.dailybruh.web.from
import com.example.dailybruh.web.recentRequest
import com.example.dailybruh.web.sorting
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FragmentNewsPage : StandardFragment<FragmentNewsPageBinding>(),MainPageView {

    private val newsModel: NewsViewModel by viewModels()
    private val databaseViewModel: DatabaseViewModel by viewModels()
    private lateinit var presenter: MainPagePresenter

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
        presenter = MainPagePresenter(
            viewState = this,
            viewLifecycleOwner = viewLifecycleOwner,
            newsViewModel = newsModel,
            databaseViewModel = databaseViewModel
        ).also { it.loadData() }
        binding.apply {
            profileButton.setOnClickListener {
                view.disableView()
                when (Firebase.auth.currentUser) {
                    null -> view.navigateTo(R.id.newspage_to_auth_phone)
                    else -> view.navigateTo(R.id.newspage_to_profile)
                }
            }
            navMenuButton.setOnClickListener {
                FragmentDialogSearch(newsModel).show(childFragmentManager,"dialog_search")
            }
            popularFilterField.apply {
                setAdapter(R.array.popular_filter)
                hint = Sort.POPULARITY.get()
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
                hint = From.FROM_MONTH.get()
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
        newsModel.getNews(recentRequest!!.request)
    }
    private fun changeFromParam(from: From) {
        from(from.get())
        recentRequest!!.changeFrom(from.getParam())
        newsModel.getNews(recentRequest!!.request)
    }

    override fun setNews(news: News, database: Database, likesMap: HashMap<String,String>) {
        binding.viewpagerMain.adapter = VerticalPagerAdapter(
            database = database,
            fragmentManager = parentFragmentManager,
            lifecycle = viewLifecycleOwner.lifecycle,
            news = news,
            likesMap = likesMap
        )
    }
}
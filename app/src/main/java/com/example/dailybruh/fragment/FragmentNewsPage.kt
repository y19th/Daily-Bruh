package com.example.dailybruh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.dailybruh.R
import com.example.dailybruh.adapters.VerticalPagerAdapter
import com.example.dailybruh.const.loadStatus
import com.example.dailybruh.database.Database
import com.example.dailybruh.databinding.FragmentNewsPageBinding
import com.example.dailybruh.dataclasses.News
import com.example.dailybruh.extension.makeGone
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.fragment.dialog.search.FragmentDialogSearch
import com.example.dailybruh.interfaces.mainpage.MainPageView
import com.example.dailybruh.presenter.MainPagePresenter
import com.example.dailybruh.viewmodel.NewsViewModel
import com.example.dailybruh.web.From
import com.example.dailybruh.web.Sort
import com.example.dailybruh.web.from
import com.example.dailybruh.web.recentRequest
import com.example.dailybruh.web.sorting
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FragmentNewsPage : StandardFragment<FragmentNewsPageBinding>(), MainPageView {

    private val newsModel: NewsViewModel by viewModels()
    private lateinit var dialogSearch: DialogFragment

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
        MainPagePresenter(
            viewState = this,
            viewLifecycleOwner = viewLifecycleOwner,
            newsViewModel = newsModel,
            databaseViewModel = databaseViewModel
        ).also { it.loadData() }
        binding.apply {
            profileButton.setOnClickListener {
                when (Firebase.auth.currentUser) {
                    null -> view.navigateTo(R.id.newspage_to_auth_phone)
                    else -> view.navigateTo(R.id.newspage_to_profile)
                }
            }
            navMenuButton.setOnClickListener {
                dialogSearch = FragmentDialogSearch(
                    reset = { header ->
                    newsModel.also {
                        it.status.observe(viewLifecycleOwner) {
                            dialogDismiss(dialogSearch)
                        }
                        recentRequest.changeHeader(header)
                    }.getNews(recentRequest.request)
                }
                ).also {
                    it.show(childFragmentManager,"searchDialog")
                }
            }
            popularFilterField.apply {
                setAdapter(R.array.popular_filter)
                hint = sorting.value
                setOnItemClickListener { _, _, clickedItem, _ ->
                    when (clickedItem) {
                        0 -> changeSortParam(Sort.Popularity(context))

                        1 -> changeSortParam(Sort.PublishedAt(context))

                        2 -> changeSortParam(Sort.Relevancy(context))

                    }
                }
            }

            dateFilterField.apply {
                setAdapter(R.array.date_filter)
                hint = from.value
                setOnItemClickListener { _,_,clickedItem,_ ->
                    when(clickedItem) {
                        0 -> changeFromParam(From.Today(context))

                        1 -> changeFromParam(From.Week(context))

                        2 -> changeFromParam(From.Month(context))
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

    private fun dialogDismiss(dialog : DialogFragment) {
        dialog.dismiss()
    }
    private fun changeSortParam(sort: Sort) {
        sorting(sort.getString)
        recentRequest.changeSort(sort.getParam)
        newsModel.getNews(recentRequest.request)
    }
    private fun changeFromParam(from: From) {
        from(from.getString)
        recentRequest.changeFrom(from.getTime)
        newsModel.getNews(recentRequest.request)
    }


    override fun setNews(
        news: News,
        database: Database,
        likesMap: HashMap<String,String>,
        savesMap: HashMap<String,String>
    ) {
        binding.viewpagerMain.adapter = VerticalPagerAdapter(
            database = database,
            fragmentManager = parentFragmentManager,
            lifecycle = viewLifecycleOwner.lifecycle,
            news = news,
            likesMap = likesMap,
            savesMap = savesMap
        )
    }
}
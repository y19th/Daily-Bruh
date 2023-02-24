package com.example.dailybruh.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.dailybruh.R
import com.example.dailybruh.const.NEWS_DATA
import com.example.dailybruh.databinding.FragmentMainBinding
import com.example.dailybruh.databinding.RecyclerItemNewsPageBinding
import com.example.dailybruh.dataclasses.Article
import com.example.dailybruh.dataclasses.News
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.viewmodel.MainViewModel
import com.example.dailybruh.web.MoshiParse

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding
    private val model: MoshiParse by viewModels()
    private lateinit var news: News

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val observer = Observer<String> {
                news = model.news.value!!
                navigateUp()
            }
            model.apply {
                getNews("everything?q=Apple&from=2023-02-22&sortBy=popularity")
                status.observe(viewLifecycleOwner, observer)
            }
        }
    }
    private fun navigateUp() {
        val bundle = Bundle()
        bundle.putSerializable(NEWS_DATA,news)
        view?.navigateTo(R.id.newspage,bundle)
    }
}
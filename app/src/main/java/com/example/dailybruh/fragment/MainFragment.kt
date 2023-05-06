package com.example.dailybruh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dailybruh.R
import com.example.dailybruh.databinding.FragmentMainBinding
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.viewmodel.NewsViewModel
import com.example.dailybruh.web.Request
import com.example.dailybruh.web.language
import com.example.dailybruh.web.recentRequest
import com.example.dailybruh.web.setDefaultSettings
import com.example.dailybruh.web.sorting
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: NewsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDefaultSettings()
        recentRequest = Request("everything","Apple", null,null, sorting.value,
            language.value)

        view.navigateTo(R.id.newspage)
        /*viewModel.getNews(recentRequest!!.request)
        viewModel.status.observe(viewLifecycleOwner) {
            view.navigateTo(R.id.newspage)
        }*/

    }
}
package com.example.dailybruh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.dailybruh.R
import com.example.dailybruh.databinding.FragmentMainBinding
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.web.MoshiParse
import com.example.dailybruh.web.Request
import com.example.dailybruh.web.language
import com.example.dailybruh.web.recentRequest
import com.example.dailybruh.web.setDefaultSettings
import com.example.dailybruh.web.sorting


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val model: MoshiParse by viewModels()

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
        binding.apply {
            val observer = Observer<String> {
                view.navigateTo(R.id.newspage)
            }
            model.apply {
                getNews(recentRequest!!.request)
                status.observe(viewLifecycleOwner, observer)
            }
        }
    }
}
package com.example.dailybruh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.dailybruh.R
import com.example.dailybruh.const.BASE_ARTICLE
import com.example.dailybruh.const.BASE_ENDPOINT
import com.example.dailybruh.databinding.FragmentMainBinding
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.web.Request
import com.example.dailybruh.web._recentRequest
import com.example.dailybruh.web.language
import com.example.dailybruh.web.setDefaultSettings
import com.example.dailybruh.web.sorting


class MainFragment : StandardFragment<FragmentMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDefaultSettings()
        _recentRequest = Request(
            BASE_ENDPOINT, BASE_ARTICLE, null,null, sorting.value,
            language.value)
        view.navigateTo(R.id.newspage)
    }
}
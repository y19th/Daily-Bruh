package com.example.dailybruh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dailybruh.R
import com.example.dailybruh.adapters.VerticalPagerAdapter
import com.example.dailybruh.const.NEWS_DATA
import com.example.dailybruh.const.constNews
import com.example.dailybruh.database.Database
import com.example.dailybruh.databinding.FragmentNewsPageBinding
import com.example.dailybruh.dataclasses.News
import com.example.dailybruh.extension.disableView
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.extension.navigateToWithSerializable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FragmentNewsPage : Fragment() {

    private lateinit var binding: FragmentNewsPageBinding
    private lateinit var news: News

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsPageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        news = constNews.value!!
        binding.apply {
            viewpagerMain.apply {
                val database = when(Firebase.auth.currentUser){
                    null -> Database(lifecycleOwner = viewLifecycleOwner)
                    else -> Database(Firebase.auth.currentUser!!.phoneNumber!!,viewLifecycleOwner)
                }
                adapter = VerticalPagerAdapter(news,database, parentFragmentManager, lifecycle)

            }
            profileButton.setOnClickListener {
                view.disableView()
                when (Firebase.auth.currentUser) {
                    null -> view.navigateTo(R.id.newspage_to_auth_phone)
                    else -> view.navigateTo(R.id.newspage_to_profile)
                }
            }
        }
    }
}
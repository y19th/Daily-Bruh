package com.example.dailybruh.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.dailybruh.R
import com.example.dailybruh.adapters.bindImage
import com.example.dailybruh.calendar.parseDate
import com.example.dailybruh.const.constNews
import com.example.dailybruh.database.Database
import com.example.dailybruh.databinding.RecyclerItemNewsPageBinding
import com.example.dailybruh.dataclasses.News
import com.example.dailybruh.extension.ifNull
import com.example.dailybruh.extension.toastLong
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class FragmentViewPagerItem(
    private val database: Database,
    private val position: Int,
    private val newsFlow: StateFlow<News>
): Fragment() {

    private var _binding: RecyclerItemNewsPageBinding? = null
    private val binding: RecyclerItemNewsPageBinding
    get() = _binding!!

    private var likes = MutableLiveData<Long>()
    private val newsy = constNews.value!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecyclerItemNewsPageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val filledHeart = AppCompatResources.getDrawable(requireContext(),R.drawable.icon_heart_filled)
        val unfilledHeart = AppCompatResources.getDrawable(requireContext(),R.drawable.icon_heart_unfilled)


        binding.apply {

            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main + CoroutineName("setUI")) {
                titlePage.text = newsFlow.value.articles[position].title
                publishedatPage.text = parseDate(newsFlow.value.articles[position].time)
                authorPage.text = newsFlow.value.articles[position].author.ifNull("Без автора")
                bindImage(urlPhoto, newsFlow.value.articles[position].image)
                descPage.text = resizeDueTextLength()
                likes.observe(viewLifecycleOwner) {
                    likeCount.text = it.toString()
                }
                database.isLiked(newsFlow.value.articles[position].id)
                    .observe(viewLifecycleOwner) { isLiked ->

                        when (isLiked) {
                            true -> {
                                likeButton.setImageDrawable(filledHeart)
                                likeButton.setOnClickListener {
                                    if (Firebase.auth.currentUser?.phoneNumber != null) {
                                        database.transactionUnlike(newsFlow.value.articles[position])
                                        // likeButton.setImageDrawable(unfilledHeart)
                                        likeButton.tag = "unfilled"
                                    } else {
                                        toastLong(
                                            requireContext(),
                                            "Для оценки новости нужно авторизироваться"
                                        )
                                    }
                                }
                            }

                            false -> {
                                likeButton.setImageDrawable(unfilledHeart)
                                likeButton.setOnClickListener {
                                    likeButton.drawable
                                    if (Firebase.auth.currentUser?.phoneNumber != null) {
                                        database.transactionLike(newsFlow.value.articles[position])
                                        //likeButton.setImageDrawable(filledHeart)
                                        likeButton.tag = "filled"
                                    } else {
                                        toastLong(
                                            requireContext(),
                                            "Для оценки новости нужно авторизоваться"
                                        )
                                    }
                                }
                            }
                        }
                    }
                database.article(newsFlow.value.articles[position])
                database.likes.observe(viewLifecycleOwner) {
                    if (database.likes.value!!.id == newsFlow.value.articles[position].id) likes.value =
                        it.likes
                }
            }
        }
    }

    private fun resizeDueTextLength(): String {
        //val string = resizeDescription()
        val string = newsy.articles[position].desc!!
        resizeTitle()
        return string
    }
    private fun resizeDescription(): String {
        val str = newsy.articles[position].desc
        return if(str!!.length > 150) str.substring(0, str.indexOf(" ", 130)).plus("...")
            else return str
    }
    private fun resizeTitle() {
        binding.apply {
            when(Build.VERSION.SDK_INT) {               //if phone has <28 sdk
                in 23..27 -> {                     //we resize text size due resolution
                    titlePage.textSize = 18F
                    descPage.textSize = 14F
                }
                else -> {
                    titlePage.textSize = 20F
                    descPage.textSize = 16F
                }
            }
            if(newsy.articles[position].title!!.length > 50) {
                titlePage.textSize = 16F
            }
        }
    }
}
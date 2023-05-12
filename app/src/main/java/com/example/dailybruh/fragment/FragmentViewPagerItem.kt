package com.example.dailybruh.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import com.example.dailybruh.R
import com.example.dailybruh.adapters.bindImage
import com.example.dailybruh.calendar.parseDate
import com.example.dailybruh.const.constNews
import com.example.dailybruh.database.Database
import com.example.dailybruh.databinding.RecyclerItemNewsPageBinding
import com.example.dailybruh.dataclasses.News
import com.example.dailybruh.extension.dec
import com.example.dailybruh.extension.ifNull
import com.example.dailybruh.extension.inc
import com.example.dailybruh.interfaces.PagerItemView
import com.example.dailybruh.presenter.PagerItemPresenter

class FragmentViewPagerItem(
    private val database: Database,
    private val position: Int,
    private val news: News,
    private val mapLikes: HashMap<String,String>
): StandardFragment<RecyclerItemNewsPageBinding>(),PagerItemView {

    private val newsy = constNews.value!!
    private lateinit var presenter: PagerItemPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecyclerItemNewsPageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        presenter = PagerItemPresenter(
            viewState = this,
            database = database,
            itemId = news.articles[position].id,
            mapLikes = mapLikes
        )
        presenter.getLikes()


        binding.apply {
                titlePage.text = news.articles[position].title
                publishedatPage.text = parseDate(news.articles[position].time)
                authorPage.text = news.articles[position].author.ifNull("Без автора")
                bindImage(urlPhoto, news.articles[position].image)
                descPage.text = resizeDueTextLength()
                database.article(news.articles[position])
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

    override fun setLikes(count: Long) {
        binding.apply {
            likeCount.text = count.toString()
            likeButton.setOnClickListener {
                when(presenter.isLiked) {
                    false -> likeCount.text = likeCount.text.inc()
                    true -> likeCount.text = likeCount.text.dec()
                }
                presenter.changeLikes(count)
            }

        }

    }

    override fun checkIsLiked(isLiked: Boolean) {
        when(isLiked) {
            true -> {
                binding.likeButton.setImageDrawable(AppCompatResources
                    .getDrawable(requireContext(),R.drawable.icon_heart_filled))
            }
            false -> {
                binding.likeButton.setImageDrawable(AppCompatResources
                    .getDrawable(requireContext(),R.drawable.icon_heart_unfilled))
            }
        }
    }
}


/*database.isLiked(news.articles[position].id)
                    .observe(viewLifecycleOwner) { isLiked ->

                        when (isLiked) {
                            true -> {
                                likeButton.setImageDrawable(filledHeart)
                                likeButton.setOnClickListener {
                                    if (Firebase.auth.currentUser?.phoneNumber != null) {
                                        database.transactionUnlike(news.articles[position])
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
                                        database.transactionLike(news.articles[position])
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
                    }*/
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
import com.example.dailybruh.database.Database
import com.example.dailybruh.databinding.RecyclerItemNewsPageBinding
import com.example.dailybruh.dataclasses.News
import com.example.dailybruh.extension.dec
import com.example.dailybruh.extension.ifNull
import com.example.dailybruh.extension.inc
import com.example.dailybruh.extension.makeGone
import com.example.dailybruh.interfaces.mainpage.pager.PagerItemView
import com.example.dailybruh.presenter.PagerItemPresenter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FragmentViewPagerItem(
    private val database: Database,
    private val position: Int,
    private val news: News,
    private val mapLikes: HashMap<String,String>,
    private val mapSaves: HashMap<String,String>
): StandardFragment<RecyclerItemNewsPageBinding>(), PagerItemView {

    private lateinit var presenter: PagerItemPresenter

    override val fragment: FragmentViewPagerItem
        get() = this

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecyclerItemNewsPageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when(Firebase.auth.currentUser) {
            null -> {
                binding.apply {
                    likeButton.makeGone()
                    likeCount.makeGone()
                    saveButton.makeGone()
                }
            }
            else -> {
                presenter = PagerItemPresenter(
                    viewState = this,
                    database = database,
                    itemId = news.articles[position].id,
                    mapLikes = mapLikes,
                    mapSaves = mapSaves
                )
                presenter.also { it.getSaves() }.getLikes()
            }
        }
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
        resizeTitle()
        return resizeDescription()
    }
    private fun resizeDescription(): String {
        val str = news.articles[position].desc
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
            if(news.articles[position].title!!.length > 50) {
                titlePage.textSize = 16F
            }
        }
    }

    override fun setLikes(count: Long) {
        binding.apply {
            with(likeCount) {
                this.text = count.toString()
                likeButton.setOnClickListener {
                    when (presenter.isLiked) {
                        false -> this.text = this.text.inc()
                        true -> this.text = this.text.dec()
                    }
                    presenter.changeLikes(this.text.toString().toLong())
                }
            }

        }

    }

    override fun setSaves() {
        binding.saveButton.setOnClickListener {
            presenter.changeSaveState()
        }
    }
    override fun checkIsSaved(isSaved: Boolean) {
        when(isSaved) {
            true -> binding.saveButton.setImageDrawable(AppCompatResources
                .getDrawable(requireContext(),R.drawable.icon_save_news_filled))
            false -> binding.saveButton.setImageDrawable(AppCompatResources
                .getDrawable(requireContext(),R.drawable.icon_save_news_unfilled))
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
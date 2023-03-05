package com.example.dailybruh.fragment

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.dailybruh.R
import com.example.dailybruh.adapters.VerticalPagerAdapter
import com.example.dailybruh.calendar.monthToCal
import com.example.dailybruh.const.NEWS_DATA
import com.example.dailybruh.databinding.FragmentNewsPageBinding
import com.example.dailybruh.dataclasses.News
import com.example.dailybruh.fragment.dialog.FragmentDialogSearch
import com.example.dailybruh.fragment.dialog.settings.FragmentDialogSettings
import com.google.android.material.navigation.NavigationView
import java.text.SimpleDateFormat
import java.util.*

class FragmentNewsPage : Fragment(),NavigationView.OnNavigationItemSelectedListener {

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
        news = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(NEWS_DATA,News::class.java) as News
        } else {
            arguments?.getSerializable(NEWS_DATA) as News
        }
        binding.apply {
            viewpagerMain.apply {
                adapter = VerticalPagerAdapter(news, parentFragmentManager, lifecycle)

            }
            navView.apply {
                y += 60
                getHeaderView(0).apply {
                    findViewById<TextView>(R.id.title_date).text = getDate()
                    findViewById<TextView>(R.id.title_time).text = getTime()
                }
                setNavigationItemSelectedListener {
                    when(it.itemId) {
                        R.id.search -> FragmentDialogSearch().show(childFragmentManager,"search_dialog")
                        R.id.settings -> FragmentDialogSettings().show(childFragmentManager,"settings_dialog")
                    }
                    binding.mainLayout.closeDrawer(GravityCompat.START)
                    true
                }
            }
            navMenuButton.setOnClickListener {
                mainLayout.openDrawer(GravityCompat.START)
            }
        }
        timer(getSec())
    }

    private fun getDate(): String {
        val cal = Calendar.getInstance()
        //str = SimpleDateFormat("d MMMM", Locale.getDefault()).format(Calendar.getInstance().time) //english date
        val month = monthToCal((cal[Calendar.MONTH] + 1).toString())
        return "${cal[Calendar.DAY_OF_MONTH]} $month"
    }
    private fun getTime(): String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Calendar.getInstance().time)
    private fun getSec(): Int = Calendar.getInstance().get(Calendar.SECOND)

    private fun timer(sec: Int) {
        object: CountDownTimer(((60 - sec)*1000).toLong(),1000) {
            override fun onTick(p0: Long){
                if(p0 < 1000)onFinish()
            }

            override fun onFinish() {
                binding.navView.getHeaderView(0).findViewById<TextView>(R.id.title_time).text = getTime()
                timer()
            }
        }.start()
    }
    private fun timer() {
        object: CountDownTimer(60000,1000) {
            override fun onTick(p0: Long){}

            override fun onFinish() {
                binding.navView.getHeaderView(0).findViewById<TextView>(R.id.title_time).text = getTime()
                timer()
            }
        }.start()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.search -> FragmentDialogSearch().show(parentFragmentManager,"search_dialog")
        }
        binding.mainLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
package com.example.dailybruh.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dailybruh.R
import com.example.dailybruh.adapters.VerticalPagerAdapter
import com.example.dailybruh.const.constNews
import com.example.dailybruh.database.Database
import com.example.dailybruh.databinding.FragmentNewsPageBinding
import com.example.dailybruh.dataclasses.News
import com.example.dailybruh.enum.From
import com.example.dailybruh.enum.Sort
import com.example.dailybruh.extension.disableView
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.extension.toastLong
import com.example.dailybruh.fragment.dialog.FragmentDialogSearch
import com.example.dailybruh.web.MoshiParse
import com.example.dailybruh.web.from
import com.example.dailybruh.web.recentRequest
import com.example.dailybruh.web.sorting
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FragmentNewsPage : Fragment() {

    private lateinit var binding: FragmentNewsPageBinding
    private lateinit var news: News
    private val model: MoshiParse by viewModels()

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
        binding.apply {
            viewpagerMain.apply {
                val database = when(Firebase.auth.currentUser){
                    null -> Database().newInstance(lifecycleOwner = viewLifecycleOwner)
                    else -> Database().newInstance(Firebase.auth.currentUser!!.phoneNumber!!,viewLifecycleOwner)
                }

//                val database = if(constDatabase.value?.phone == STANDARD_PHONE || constDatabase.value?.phone == null) {   //have trouble with
//                    when(Firebase.auth.currentUser) {                                                                     //observing user likes
//                        null -> Database().newInstance(lifecycleOwner = viewLifecycleOwner)
//                        else -> Database().newInstance(Firebase.auth.currentUser!!.phoneNumber!!,viewLifecycleOwner)
//                   }
//                } else constDatabase.value!!
                constNews.observe(viewLifecycleOwner) {
                    adapter = VerticalPagerAdapter(database, parentFragmentManager, lifecycle)
                }
                //adapter = VerticalPagerAdapter(database, parentFragmentManager, lifecycle)

            }
            profileButton.setOnClickListener {
                view.disableView()
                when (Firebase.auth.currentUser) {
                    null -> view.navigateTo(R.id.newspage_to_auth_phone)
                    else -> view.navigateTo(R.id.newspage_to_profile)
                }
            }
            navMenuButton.setOnClickListener {
                FragmentDialogSearch().show(childFragmentManager,"dialog_search")
            }
            popularFilterField.apply {
                setAdapter(R.array.popular_filter)
                setOnItemClickListener { adapterView, view, clickedItem, l ->
                    when (clickedItem) {
                        0 -> {
                            changeSortParam(Sort.POPULARITY)
                        }

                        1 -> {
                            changeSortParam(Sort.PUBLISHEDAT)
                        }

                        2 -> {
                            changeSortParam(Sort.RELEVANCY)
                        }
                    }
                }
            }

            dateFilterField.apply {
                setAdapter(R.array.date_filter)
                setOnItemClickListener { _,_,clickedItem,_ ->
                    when(clickedItem) {
                        0 -> {
                            changeFromParam(From.FROM_TODAY)
                        }
                        1 -> {
                            changeFromParam(From.FROM_WEEK)
                        }
                        2 -> {
                            changeFromParam(From.FROM_MONTH)
                        }
                    }
                }
            }
        }
    }

    private fun AutoCompleteTextView.setAdapter(arrayId: Int) {
        this.apply {
            setDropDownBackgroundDrawable(ResourcesCompat.getDrawable(resources,R.drawable.item_filter_background_inset,null))
        }.setAdapter(ArrayAdapter(requireContext(),R.layout.menu_list_item,resources.getStringArray(arrayId)))
        val ada = ArrayAdapter(requireContext(),R.layout.menu_list_item,resources.getStringArray(arrayId))

    }
    private fun changeSortParam(sort: Sort) {
        sorting(sort.get())
        recentRequest!!.changeSort(sort.getParam())
        model.getNews(recentRequest!!.request)
    }
    private fun changeFromParam(from: From) {
        from(from.get())
        recentRequest!!.changeFrom(from.getParam())
        model.getNews(recentRequest!!.request)
    }

}
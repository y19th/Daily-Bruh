package com.example.dailybruh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.dailybruh.R
import com.example.dailybruh.adapters.VerticalPagerAdapter
import com.example.dailybruh.const.constNews
import com.example.dailybruh.database.Database
import com.example.dailybruh.databinding.FragmentNewsPageBinding
import com.example.dailybruh.dataclasses.News
import com.example.dailybruh.extension.disableView
import com.example.dailybruh.extension.navigateTo
import com.example.dailybruh.fragment.dialog.FragmentDialogSearch
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
            popularFilterField.setAdapter(R.array.popular_filter)
            dateFilterField.setAdapter(R.array.date_filter)
        }
    }

    private fun AutoCompleteTextView.setAdapter(arrayId: Int) {
        this.apply {
            setDropDownBackgroundDrawable(ResourcesCompat.getDrawable(resources,R.drawable.item_filter_background_inset,null))
        }.setAdapter(ArrayAdapter(requireContext(),R.layout.menu_list_item,resources.getStringArray(arrayId)))
    }
}
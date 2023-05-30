package com.example.dailybruh.interfaces.profile

import com.example.dailybruh.fragment.FragmentProfile
import com.example.dailybruh.interfaces.StandardView

interface ProfileView : StandardView<FragmentProfile> {
    fun setName(result: String)
    fun setNickname(result: String)
}
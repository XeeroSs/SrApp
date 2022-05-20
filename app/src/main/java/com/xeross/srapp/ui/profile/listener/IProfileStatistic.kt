package com.xeross.srapp.ui.profile.listener

import com.xeross.srapp.data.models.User
import com.xeross.srapp.ui.profile.ProfileViewModel

interface IProfileStatistic {
    fun getAccountCreatedAt(viewModel: ProfileViewModel, user:User)
    fun getLastBest(viewModel: ProfileViewModel,user:User)
    fun getTotalBest(viewModel: ProfileViewModel,user:User)
    fun getBestRate(viewModel: ProfileViewModel,user:User)
    fun getTotalTimes(viewModel: ProfileViewModel,user: User)
}
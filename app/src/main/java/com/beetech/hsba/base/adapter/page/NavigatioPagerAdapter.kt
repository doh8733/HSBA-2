package com.beetech.hsba.base.adapter.page

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.beetech.hsba.ui.home.HomeFragment
import com.beetech.hsba.ui.main.MainFragment
import com.beetech.hsba.ui.service.ServiceFragment
import com.beetech.hsba.ui.specialist.SpecialistFragment

class NavigatioPagerAdapter(fragment : MainFragment) : FragmentStateAdapter(fragment)  {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> HomeFragment()
            1 -> ServiceFragment()
            else -> {
                HomeFragment()
            }
        }
    }
}
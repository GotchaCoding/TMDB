package org.techtown.diffuser.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.techtown.diffuser.fragment.home.HomeFragment
import org.techtown.diffuser.fragment.recommend.RecommendFragment
import org.techtown.diffuser.fragment.search.SearchFragment

class MyViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val fragments: List<Fragment> = listOf(HomeFragment(),RecommendFragment.newInstance(), SearchFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment = fragments[position]
}
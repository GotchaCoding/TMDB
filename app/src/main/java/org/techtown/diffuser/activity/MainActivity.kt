package org.techtown.diffuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.databinding.ActivityMainBinding
import org.techtown.diffuser.fragment.home.HomeFragment
import org.techtown.diffuser.fragment.search.SearchFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    NavigationBarView.OnItemSelectedListener  {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //페이저에 어뎁터 연결
        binding.pager.adapter = MyViewPagerAdapter(this)
        binding.pager.isUserInputEnabled = false

        //슬라이드하여 페이지 변경되면 바텀네비게이션의 탭도 그 페이지로 활성화
        binding.pager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.bottomNavigationView.menu.getItem(position).isChecked = true
                }
            }
        )
        //리스너 연결
        binding.bottomNavigationView.setOnItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_home -> {
                binding.pager.currentItem = 0
                return true
            }
            R.id.item_search -> {
                binding.pager.currentItem = 1
                return true
            }
            else -> {
                return false
            }
        }
    }
}

class MyViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    val fragments: List<Fragment>

    init {
        fragments = listOf(HomeFragment(), SearchFragment())
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment = fragments[position]
}
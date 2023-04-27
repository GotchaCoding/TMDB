package org.techtown.diffuser

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.activity.MyViewPagerAdapter
import org.techtown.diffuser.databinding.ActivityMainBinding

@AndroidEntryPoint  //Hilt Di 사용이 가능하게 annotation
class MainActivity : AppCompatActivity(),
    NavigationBarView.OnItemSelectedListener {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //페이저에 어뎁터 연결
        binding.pager.adapter = MyViewPagerAdapter(this)
        binding.pager.isUserInputEnabled = false  // 뷰페이져의 스크롤 전환가능여부를 세팅

        //페이지 변경되면 바텀네비게이션의 탭도 그 페이지로 활성화
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
            R.id.item_home -> {  //네비게이션 아이콘 클릭시 navigation에 등록된 id 정보를 이용해 페이져 아이탬 번호 등록.
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


package org.techtown.diffuser

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
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
        attachBackPressedCallback()
    }

    private fun attachBackPressedCallback() {   //뒤로가기 클릭시  검색프래그먼트인 경우 홈프래그먼트로 이동.  홈에선 엑티비티 종료
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.pager.currentItem == 1 || binding.pager.currentItem ==2) {
                    binding.pager.currentItem = 0
                } else {
                    finish()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_home -> {  //네비게이션 아이콘 클릭시 뷰페이저 아이템번호로 프래그먼트 전환
                binding.pager.currentItem = 0  //currentItem  현재 선택한 페이지를 설정. 뷰페이저가 이미 첫번재 레이아웃을 완료햇다면 지정된 아이탬 프래그먼트로 전환됨.
                return true
            }
            R.id.item_recommend -> {
                binding.pager.currentItem =1
                return true
            }
            R.id.item_search -> {
                binding.pager.currentItem = 2
                return true
            }
            else -> {
                return false
            }
        }
    }
}


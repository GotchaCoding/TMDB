package org.techtown.diffuser.fragment.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import org.techtown.diffuser.activity.detailpage.PopularDetailActivity
import org.techtown.diffuser.activity.moreview.comming.CommingMoreActivity
import org.techtown.diffuser.activity.moreview.nowplay.NowplayMoreActivity
import org.techtown.diffuser.activity.moreview.popular.PopularMoreActivity
import org.techtown.diffuser.constants.Constants
import org.techtown.diffuser.databinding.FragmentHomeBinding
import org.techtown.diffuser.fragment.BaseFragment

@AndroidEntryPoint  //프래그먼트 힐트 주입 어노테이션
class HomeFragment : BaseFragment<FragmentHomeBinding>(), SwipeRefreshLayout.OnRefreshListener {  //프래그먼트 상속, 스와이프리프레시 리스너 상속
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private lateinit var adapter: HomeAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        fetchAll()

    }

    private fun initObserver() { //
        viewModel.items.observe(viewLifecycleOwner) { items ->  // 뷰모델의 items를 관찰 : LiveData<List<ItemModel>
            adapter.submitList(items)  //변경이 생기면 ListAdapter diffutil로  데이터 전송. --> 아이템과 컨텐츠 동일여부 판독.
        }
    }

    private fun initView() = with(binding) {
        swipe.setOnRefreshListener {      //스와이프리프레시리스너
            onRefresh()    // 스와이프리프레시리스너 동작시 실행할 내용.
            swipe.isRefreshing = false   // 로딩화면 제거
        }
        val layoutManager = LinearLayoutManager(context)

        adapter = HomeAdapter(  //HomeAdapter 객체 만들때  생성자 부분의 인터페이스와 람다함수 초기화
            itemClickListener = { _, viewType, movie, theMore -> // 사용안하는 매개변수 '_' 처리.
                Log.e("kmh!!!", "클릭 리스너 처음")
                if (movie == null) {
                    Log.e("kmh!!!", "클릭 리스너 if 문")
                    when (viewType) {  // 실패뷰 떳을때 클릭시 뷰타입별로 패치
                        Constants.VIEW_TYPE_POPULAR_MOVIE -> {
                            viewModel.fetch()
                        }
                        Constants.VIEW_TYPE_NOW_MOVIE -> {
                            viewModel.fetchNowPlay()
                        }
                        Constants.VIEW_TYPE_UPCOMMING -> {
                            viewModel.fetchUpComming()
                        }
                    }

                    when (theMore) {
                        TheMore.THEMORE_POPULAR -> {  //enum class TheMore 패턴매칭으로 알맞은 엑티비티 실행.
                            val intent = Intent(context, PopularMoreActivity::class.java)
                            startActivity(intent)
                        }
                        TheMore.THEMORE_NOW -> {
                            val intent = Intent(context, NowplayMoreActivity::class.java)
                            startActivity(intent)
                        }
                        TheMore.THEMORE_COMMING -> {
                            val intent = Intent(context, CommingMoreActivity::class.java)
                            startActivity(intent)
                        }
                        else -> {}
                    }
                } else {//클릭시 movie 정보는 반드시 필요.
                    Log.e("kmh!!!", "클릭 리스너 처음")
                    val intent = Intent(context, PopularDetailActivity::class.java)
                    intent.putExtra(
                        "movie_id",
                        movie.id
                    )  //movie.id 인텐트로 송부하고 PopularDetailActivity 엑티비티 실행.
                    startActivity(intent)
                }
            }
        )
        recyclerview.adapter = adapter
        recyclerview.layoutManager = layoutManager
    }

    private fun fetchAll() {
        viewModel.fetch()
        viewModel.fetchNowPlay()
        viewModel.fetchUpComming()
    }

    override fun onRefresh() {
        fetchAll()
    }

    companion object {
        fun newInstance(): Fragment {
            return HomeFragment()
        }
    }

}

enum class TheMore {
    THEMORE_POPULAR,
    THEMORE_NOW,
    THEMORE_COMMING
}

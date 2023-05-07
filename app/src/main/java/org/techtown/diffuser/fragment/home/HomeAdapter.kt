package org.techtown.diffuser.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import org.techtown.diffuser.BaseAdapter
import org.techtown.diffuser.R
import org.techtown.diffuser.model.Movie
import org.techtown.diffuser.model.TitleModel
import org.techtown.diffuser.model.WrappingModel

class HomeAdapter(
    itemClickListener: (View, Int, Movie?, TheMore?) -> Unit, // MoreViewClick 인터페이스 속성 생성자 포함 : (View, Int, Movie?) 매개변수로 람다함수를 실행
) : BaseAdapter(itemClickListener) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {  // 매개변수로 뷰타입을 받아와 뷰타입별로 어떤 레이아웃을 사용할지 정하고 각자 다른 뷰홀더를 리턴함.
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            VIEW_TYPE_TITLE -> {
                val itemView = inflater.inflate(R.layout.item_titlepopualr, parent, false)
                return TitleViewHolder(itemView, itemClickListener)
            }
            VIEW_TYPE_POPULAR_MOVIE -> {
                val itemView = inflater.inflate(R.layout.item_popularmovies, parent, false)
                return HorizontalPopularMoviesViewHolder(itemView, itemClickListener)
            }
            VIEW_TYPE_NOW_MOVIE -> {
                val itemView = inflater.inflate(R.layout.item_popularmovies, parent, false)
                return NowMovieViewHolder(itemView, itemClickListener)
            }
            VIEW_TYPE_UPCOMMING -> {
                val itemView = inflater.inflate(R.layout.item_popularmovies, parent, false)
                return HorizontalPopularMoviesViewHolder(itemView, itemClickListener)
            }
            else -> {  // 지정한 뷰타입이 아닐 시 에러
                throw Exception()
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) { // 뷰홀더에 데이터 온바인드.  리사이클러뷰 멀티뷰이기때문에 holder :RecyclerView.ViewHolder
        val itemModel =
            currentList[position]  // ListAdapter의 깐부.   그냥 리사이클러뷰 엿다면  ArrayList<Person1> itemslist = new ArrayList<Person1>(); 이런식의 리스트[position]에 접근하기 위해 사용했엇음.
        when (itemModel.viewType) {
            VIEW_TYPE_TITLE -> {
                if (itemModel is TitleModel) {  //타입 검사 호환성 체크 :  TitleModel 은 itemModel은 상속하니  true /  downcast 를 위한 빌드업.
                    (holder as TitleViewHolder).setItem(itemModel) //holder: RecyclerView.ViewHolder를 TitleViewHolder로 다운캐스트.  타이틀뷰홀더에 있는 SetItem 메서드 호출.
                }
            }
            VIEW_TYPE_POPULAR_MOVIE -> { //스마트캐스트
                if (holder is HorizontalPopularMoviesViewHolder && itemModel is WrappingModel) { //holder 와  itemModel  호환성 체크. & 자동캐스트 : 변수가 원하는 타입인지 is로 검사하고 나면 스마트캐스트 됨.
                    holder.setItem(itemModel) //HorizontalPopularMoviesViewHolder.setItem 호출
                }
            }
            VIEW_TYPE_NOW_MOVIE -> {
                if (holder is NowMovieViewHolder && itemModel is WrappingModel) {
                    holder.setItem(itemModel)
                }
            }
            VIEW_TYPE_UPCOMMING -> {
                if (holder is HorizontalPopularMoviesViewHolder && itemModel is WrappingModel) {
                    holder.setItem(itemModel)
                }
            }
        }
    }


    class HorizontalPopularMoviesViewHolder(
        itemView: View,
        private val itemClickListener: (View, Int, Movie?, TheMore?) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private var rvMain: RecyclerView =
            itemView.findViewById(R.id.rvMain)    // 뷰홀더에 리사이클러뷰가 잇음 --> 멀티리사이클러뷰
        private var vLoading: LottieAnimationView = itemView.findViewById(R.id.vLoading) //로딩 에니메이션
        private var viewFailure: TextView = itemView.findViewById(R.id.onFailure)  //실패뷰

        var adapter = HorizontalPopularMoviesRecyclerAdapter(itemClickListener)  // 멀티리사이클러뷰 어뎁터 설정.

        init {
            vLoading.setAnimation(R.raw.loading)    //에니메이션뷰 세팅
            vLoading.repeatCount = -1
            vLoading.playAnimation()
            rvMain.adapter = adapter              // 리사이클러뷰 어뎁터 등록.
            rvMain.layoutManager = LinearLayoutManager( //레이아웃 메니저 세팅
                itemView.context,
                LinearLayoutManager.HORIZONTAL,    //방향 수평으로 세팅
                false
            )
        }

        fun setItem(item: WrappingModel) {
            if (item.isFailure) {  // 통신 실패시
                viewFailure.isVisible = true // 실패뷰 보이게 설정.
                vLoading.isVisible = item.isLoading  //이경우 로딩뷰 안보이게
            } else {  //통신 성공시
                viewFailure.isVisible = false  //실패뷰 안보이게.
                vLoading.isVisible = item.isLoading   //이경우 로딩뷰 안보이게

                if (item.model != null) {
                    adapter.setMovies(item.model.movies)  //어뎁터의 setMovies 메서드 호출 --> 어뎁터 내 관리하고있는 리스트 업데이트 후 갱신.
                }
            }

            viewFailure.setOnClickListener {  //실패뷰  클릭리스너.  클릭시
                itemClickListener(
                    it,
                    item.viewType,
                    null,
                    null
                )
            }
        }

    }

    class TitleViewHolder(
        itemView: View,
        private val itemClickListener: (View, Int, Movie?, TheMore?) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private var tvMore: TextView = itemView.findViewById(R.id.tvMoreview)

        fun setItem(titleModel: TitleModel) {
            tvTitle.text = titleModel.title
            tvMore.setOnClickListener { view ->    //  더보기 클릭리스너.  매개변수를 사용하고 싶지 않을 경우 ' _ ' 로 대체.
                titleModel.theMore?.let {     // [let] null 체크 후 코드를 실행해야하는경우 let 사용.  null이 아닐때 실행.
                    itemClickListener(
                        view,
                        titleModel.viewType,
                        null,
                        it
                    )   //매개변수로 TheMore를 받아오며 , 행동은 위임.
                }
            }
        }
    }

    class NowMovieViewHolder(
        itemView: View,
        private val itemClickListener: (View, Int, Movie?, TheMore?) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private var rvMain: RecyclerView = itemView.findViewById(R.id.rvMain)
        private var vLoading: LottieAnimationView = itemView.findViewById(R.id.vLoading)
        private var viewFailure: TextView = itemView.findViewById(R.id.onFailure)

        var adapter = HorizontalNowPlayingAdapter(itemClickListener)

        init {
            vLoading.setAnimation(R.raw.loading)
            vLoading.repeatCount = -1
            vLoading.playAnimation()
            rvMain.adapter = adapter
            rvMain.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        }

        fun setItem(item: WrappingModel) {
            if (item.isFailure) {
                viewFailure.isVisible = true
                vLoading.isVisible = item.isLoading
            } else {
                viewFailure.isVisible = false
                vLoading.isVisible = item.isLoading

                if (item.model != null) {
                    adapter.submitList(item.model.movies)
                }
            }

            viewFailure.setOnClickListener {
                itemClickListener(it, item.viewType, null, null)
            }
        }
    }

    companion object {    // 뷰타입을 지정할 상수 세팅
        const val VIEW_TYPE_TITLE = 0
        const val VIEW_TYPE_POPULAR_MOVIE = 1
        const val VIEW_TYPE_NOW_MOVIE = 2
        const val VIEW_TYPE_UPCOMMING = 3
    }
}


package org.techtown.diffuser.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.techtown.diffuser.BaseAdapter
import org.techtown.diffuser.R
import org.techtown.diffuser.constants.Constants.VIEW_TYPE_NOW_MOVIE
import org.techtown.diffuser.constants.Constants.VIEW_TYPE_POPULAR_MOVIE
import org.techtown.diffuser.constants.Constants.VIEW_TYPE_TITLE
import org.techtown.diffuser.constants.Constants.VIEW_TYPE_UPCOMMING
import org.techtown.diffuser.databinding.ItemHorizontalbasemoviesBinding
import org.techtown.diffuser.databinding.ItemTitlepopualrBinding
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
                val binding: ItemTitlepopualrBinding =
                    DataBindingUtil.inflate(inflater, R.layout.item_titlepopualr, parent, false)
                return TitleViewHolder(binding, itemClickListener)
            }

            VIEW_TYPE_POPULAR_MOVIE -> {
                val binding: ItemHorizontalbasemoviesBinding =
                    DataBindingUtil.inflate(
                        inflater, R.layout.item_horizontalbasemovies, parent,
                        false
                    )
                return HorizontalPopularMoviesViewHolder(binding, itemClickListener)
            }

            VIEW_TYPE_NOW_MOVIE -> {
                val binding: ItemHorizontalbasemoviesBinding =
                    DataBindingUtil.inflate(
                        inflater, R.layout.item_horizontalbasemovies, parent,
                        false
                    )
                return NowMovieViewHolder(binding, itemClickListener)
            }

            VIEW_TYPE_UPCOMMING -> {
                val binding: ItemHorizontalbasemoviesBinding =
                    DataBindingUtil.inflate(
                        inflater, R.layout.item_horizontalbasemovies, parent,
                        false
                    )
                return HorizontalPopularMoviesViewHolder(binding, itemClickListener)
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
                    (holder as TitleViewHolder).apply {
                        setItem(itemModel)
                        binding.executePendingBindings()
                    } //holder: RecyclerView.ViewHolder를 TitleViewHolder로 다운캐스트.  타이틀뷰홀더에 있는 SetItem 메서드 호출.

                }
            }

            VIEW_TYPE_POPULAR_MOVIE -> { //스마트캐스트
                if (holder is HorizontalPopularMoviesViewHolder && itemModel is WrappingModel) { //holder 와  itemModel  호환성 체크. & 자동캐스트 : 변수가 원하는 타입인지 is로 검사하고 나면 스마트캐스트 됨.
                    holder.apply {
                    setItem(itemModel) //HorizontalPopularMoviesViewHolder.setItem 호출
                    binding.executePendingBindings()
                    }
                }
            }

            VIEW_TYPE_NOW_MOVIE -> {
                if (holder is NowMovieViewHolder && itemModel is WrappingModel) {
                    holder.apply {
                    setItem(itemModel)
                    binding.executePendingBindings()
                    }
                }
            }

            VIEW_TYPE_UPCOMMING -> {
                if (holder is HorizontalPopularMoviesViewHolder && itemModel is WrappingModel) {
                    holder.apply{
                        setItem(itemModel)
                        binding.executePendingBindings()
                    }
                }
            }

        }
    }


    class HorizontalPopularMoviesViewHolder(
        val binding: ItemHorizontalbasemoviesBinding,
        private val itemClickListener: (View, Int, Movie?, TheMore?) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        var adapter = HorizontalPopularMoviesRecyclerAdapter(itemClickListener)  // 멀티리사이클러뷰 어뎁터 설정.

        init {
            with(binding) {
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
        }

        fun setItem(item: WrappingModel) {
            with(binding) {
                if (item.isFailure) {  // 통신 실패시
                    onFailure.isVisible = true // 실패뷰 보이게 설정.
                    vLoading.isVisible = item.isLoading  //이경우 로딩뷰 안보이게

                } else {  //통신 성공시
                    onFailure.isVisible = false  //실패뷰 안보이게.
                    vLoading.isVisible = item.isLoading   //이경우 로딩뷰 안보이게

                    if (item.model != null) {
                        adapter.setMovies(item.model.movies)  //어뎁터의 setMovies 메서드 호출 --> 어뎁터 내 관리하고있는 리스트 업데이트 후 갱신.
                    }
                }

                onFailure.setOnClickListener {  //실패뷰  클릭리스너.  클릭시
                    itemClickListener(
                        it,
                        item.viewType,
                        null,
                        null
                    )
                }

            }
        }

    }

    class TitleViewHolder(
        val binding: ItemTitlepopualrBinding,
        private val itemClickListener: (View, Int, Movie?, TheMore?) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setItem(titleModel: TitleModel) {
            binding.tvTitle.text = titleModel.title
            binding.tvMoreview.setOnClickListener { view ->    //  더보기 클릭리스너.  매개변수를 사용하고 싶지 않을 경우 ' _ ' 로 대체.
                titleModel.theMore?.let {     // null이 아닐때 실행.
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
        val binding: ItemHorizontalbasemoviesBinding,
        private val itemClickListener: (View, Int, Movie?, TheMore?) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        var adapter = HorizontalNowPlayingAdapter(itemClickListener)

        init {
            with(binding) {
                vLoading.setAnimation(R.raw.loading)
                vLoading.repeatCount = -1
                vLoading.playAnimation()
                rvMain.adapter = adapter
                rvMain.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            }
        }

        fun setItem(item: WrappingModel) {
            with(binding) {
                if (item.isFailure) {
                    onFailure.isVisible = true
                    vLoading.isVisible = item.isLoading
                } else {
                    onFailure.isVisible = false
                    vLoading.isVisible = item.isLoading

                    if (item.model != null) {
                        adapter.submitList(item.model.movies)
                    }
                }

                onFailure.setOnClickListener {
                    itemClickListener(it, item.viewType, null, null)
                }
            }
        }
    }

}


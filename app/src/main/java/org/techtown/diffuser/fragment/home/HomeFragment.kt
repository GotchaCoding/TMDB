package org.techtown.diffuser.fragment.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.diffuser.activity.PopularDetailActivity
import org.techtown.diffuser.databinding.ActivityHomeFragmentBinding
import org.techtown.diffuser.fragment.home.HomeAdapter.Companion.VIEW_TYPE_TITLE
import org.techtown.diffuser.listener.PopularClickListener
import org.techtown.diffuser.model.HorizontalPopularModel
import org.techtown.diffuser.model.ItemModel
import org.techtown.diffuser.model.Movie
import org.techtown.diffuser.model.Title
import org.techtown.diffuser.response.PopularMoviesResponse
import org.techtown.diffuser.retrofit.RetrofitClient.Companion.retrofit
import org.techtown.diffuser.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    lateinit var binding: ActivityHomeFragmentBinding
    private lateinit var adapter: HomeAdapter

    private var service = retrofit.create(RetrofitInterface::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityHomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        fetch()

    }

    private fun initView() {
        with(binding) {
            val layoutManager = LinearLayoutManager(context)
            adapter = HomeAdapter(object : PopularClickListener {
                override fun onClick(v: View) {
                   Log.e( "log" , "initView() clicklistener 인터페이스 온클릭" )
                    val intent = Intent(context , PopularDetailActivity::class.java)
                    startActivity(intent)
                }

            })
            recyclerview.adapter = adapter
            recyclerview.layoutManager = layoutManager
        }
    }

    private fun fetch() {
        service.getPopularMovie(
            "ko",
            1
        ).enqueue(object : Callback<PopularMoviesResponse> {
            override fun onResponse(
                call: Call<PopularMoviesResponse>,
                response: Response<PopularMoviesResponse>
            ) {
                val result = response.body()
                val items = arrayListOf<ItemModel>()
                items.add(Title("인기영화2",HomeAdapter.VIEW_TYPE_TITLE))
                val list = result!!.results.map {
                    Movie(
                        it.title,
                        it.releaseDate,
                        it.posterPath
                    )
                }
                val horizontalPopularModel = HorizontalPopularModel(list, HomeAdapter.VIEW_TYPE_POPULAR_MOVIE)
                items.add(horizontalPopularModel)
                items.add(HorizontalPopularModel(list, 1))
                val test = Title("d", 0)
                items.add(test)

                adapter.addItems(items)
            }

            override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}
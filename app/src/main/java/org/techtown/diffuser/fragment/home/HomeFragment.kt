package org.techtown.diffuser.fragment.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.diffuser.R
import org.techtown.diffuser.application.DiffuserApp
import org.techtown.diffuser.databinding.ActivityHomeFragmentBinding
import org.techtown.diffuser.databinding.ActivityMainBinding
import org.techtown.diffuser.model.Movie
import org.techtown.diffuser.response.PopularMoviesResponse
import org.techtown.diffuser.retrofit.RetrofitClient
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
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = HomeAdapter()
            recyPopularMovie.adapter = adapter
            recyPopularMovie.layoutManager = layoutManager
        }
    }

    private fun fetch() {
        service.getPopularMovie(
            DiffuserApp.API_KEY,
            "ko",
            1
        ).enqueue(object : Callback<PopularMoviesResponse> {
            override fun onResponse(
                call: Call<PopularMoviesResponse>,
                response: Response<PopularMoviesResponse>
            ) {
                val result = response.body()
                val list = result!!.results.map {
                    Movie(
                        it.title,
                        it.releaseDate,
                        it.posterPath
                    )
                }
                adapter.addMovie(list)
            }

            override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}
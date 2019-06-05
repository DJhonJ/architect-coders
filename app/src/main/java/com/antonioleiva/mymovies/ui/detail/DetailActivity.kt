package com.antonioleiva.mymovies.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.antonioleiva.mymovies.R
import com.antonioleiva.mymovies.databinding.ActivityDetailBinding
import com.antonioleiva.mymovies.ui.common.app
import com.antonioleiva.mymovies.ui.common.getViewModel
import com.antonioleiva.mymovies.ui.common.loadUrl

class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    private lateinit var component: DetailActivityComponent
    private val viewModel by lazy { getViewModel { component.detaiViewModel } }
    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        component = app.component.plus(DetailActivityModule(intent.getIntExtra(MOVIE, -1)))

        viewModel.model.observe(this, Observer(::updateUi))

        binding.movieDetailFavorite.setOnClickListener { viewModel.onFavoriteClicked() }
    }

    private fun updateUi(model: DetailViewModel.UiModel) = with(binding) {
        val movie = model.movie
        movieDetailToolbar.title = movie.title
        movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780${movie.backdropPath}")
        movieDetailSummary.text = movie.overview
        movieDetailInfo.setMovie(movie)

        val icon = if (movie.favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
        movieDetailFavorite.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, icon))
    }
}
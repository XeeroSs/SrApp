package com.xeross.srapp.controller.main

import android.content.Intent
import com.xeross.srapp.R
import com.xeross.srapp.adapter.GameAdapter
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.controller.celeste.CelesteActivity
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.model.Game
import com.xeross.srapp.model.SpeedrunType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), ClickListener<Game> {

    override fun getViewModelClass() = MainViewModel::class.java
    override fun getFragmentId() = R.layout.activity_main

    private lateinit var adapter: GameAdapter
    private val games = ArrayList<Game>()
    private var viewModel: MainViewModel? = null

    override fun build() {
        viewModel = (vm as MainViewModel)
        viewModel?.build()
        adapter = GameAdapter(this, games, this).also {
            activity_main_recyclerview.setRecyclerViewAdapter(it, false)
        }
        getGames()
    }

    private fun getGames() {
        viewModel?.getCeleste(this)?.observe(this, {
            it?.let { game ->
                games.add(game)
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onClick(o: Game) {
        when (o.idSRC) {
            SpeedrunType.CELESTE -> {
                val intent = Intent(this, CelesteActivity::class.java)
                startActivity(intent)
                return
            }
        }
    }

    override fun onLongClick(o: Game) {}
}
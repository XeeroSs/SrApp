package com.xeross.srapp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jackandphantom.blurimage.BlurImage
import com.xeross.srapp.adapter.LeaderBoardAdapter
import com.xeross.srapp.controller.celeste.CelesteViewModel
import com.xeross.srapp.injection.ViewModelFactory
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.model.LeaderBoard
import kotlinx.android.synthetic.main.fragment_celeste_run.view.*

abstract class BaseFragment : Fragment(), ClickListener<LeaderBoard> {

    abstract fun getFragmentId(): Int
    abstract fun getSheetsName(): String
    abstract fun getImageLevelId(): Int
    abstract fun getLevelName(): String
    private lateinit var ui: View
    private var adapter: LeaderBoardAdapter? = null
    private val leaderBoards = ArrayList<LeaderBoard>()

    // abstract fun getViewModelClass(): Class<*>
    private var viewModel: CelesteViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = inflater.inflate(getFragmentId(), container, false)
        configureUI()
        activity?.let {
            viewModel = configureViewModel().also {
                it.build(getSheetsName())
            }
        }
        return ui
    }

    private fun configureUI() {
        context?.let { c ->
            BlurImage.with(c).load(getImageLevelId()).intensity(0f).into(ui.fragment_celeste_image_level)
            BlurImage.with(c).load(getImageLevelId()).intensity(5f).Async(true).into(ui.fragment_celeste_image_level)
            ui.fragment_celeste_title_level.text = getLevelName()
            adapter = LeaderBoardAdapter(c, leaderBoards, this).also { ui.fragment_celeste_list_leaderboard.setRecyclerViewAdapter(it, false) }
        }
    }

    override fun onClick(o: LeaderBoard) {}

    override fun onLongClick(o: LeaderBoard) {}

    @SuppressWarnings("unchecked")
    private fun configureViewModel(): CelesteViewModel {
        val factory = ViewModelFactory(this.requireContext())
        return ViewModelProvider(this, factory).get(CelesteViewModel::class.java)
    }

    private fun RecyclerView.setRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>, isCustom: Boolean) {
        setHasFixedSize(true)
        if (!isCustom) layoutManager = LinearLayoutManager(context)
        itemAnimator = DefaultItemAnimator()
        this.adapter = adapter
    }

}
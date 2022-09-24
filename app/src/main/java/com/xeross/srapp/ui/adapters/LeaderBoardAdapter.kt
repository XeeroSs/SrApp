package com.xeross.srapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseAdapter
import com.xeross.srapp.data.models.LeaderBoard
import com.xeross.srapp.data.models.types.DifferenceType
import com.xeross.srapp.databinding.RankingCellBinding
import com.xeross.srapp.listener.TimeListener
import com.xeross.srapp.utils.extensions.UtilsExtensions.toHexColorString

class LeaderBoardAdapter(context: Context, objectList: ArrayList<LeaderBoard>, private val timeListener: TimeListener) :
    BaseAdapter<LeaderBoardAdapter.ViewHolder, LeaderBoard>(context, objectList, null) {
    
    private val glide = Glide.with(context)
    
    class ViewHolder(binding: RankingCellBinding) : RecyclerView.ViewHolder(binding.root) {
        val menu: ImageButton = binding.rankingCellButtonMenu
        val difference: TextView = binding.rankingCellDifference
        val profileImage: ImageView = binding.rankingCellProfileImage
        val name: TextView = binding.rankingCellName
        val time: TextView = binding.rankingCellTime
        val position: TextView = binding.rankingCellPosition
    }
    
    
    override fun onClick(holder: ViewHolder, dObject: LeaderBoard) {
    }
    
    override fun updateItem(holder: ViewHolder, dObject: LeaderBoard) {
        
        getDifferenceText(dObject.time, holder)
        holder.position.text = context.getString(R.string.game_details_ranking_position, dObject.place)
        holder.name.text = dObject.name
        holder.time.text = context.getString(R.string.game_details_ranking_runner_time, dObject.time)

/*        holder.difference.text = "Diff Best: -3s / PB: +6s"
        holder.name.text = dObject.name
        holder.time.text = "Temps: " + dObject.time
        holder.position.text = dObject.position.toString() + "."
        //   holder.position.text = context.getString(R.string.game_position, dObject.position)
        glide.load(dObject.profileImage).into(holder.profileImage)*/
    }
    
    private fun getDifferenceText(timeOpponentInString: String, holder: ViewHolder) {

/*        val timeInSecondsOpponent = timeOpponentInString.convertTimeToSeconds()
        
        val personnelBestTime = timeListener.onPersonnelBestTime()
        val bestTime = timeListener.onBestTime()
        
        val differenceBetweenBest = (bestTime - timeInSecondsOpponent)
        val differenceBetweenPB = (personnelBestTime - timeInSecondsOpponent)
        
        val differenceTypeBest = getDifferenceType(differenceBetweenBest)
        val differenceTypePB = getDifferenceType(differenceBetweenPB)
        
        val differencePBToString = getDifferenceTextWithColorInHtmlFormat(differenceTypePB, differenceBetweenPB)
        val differenceBestToString = getDifferenceTextWithColorInHtmlFormat(differenceTypeBest, differenceBetweenBest)
        
        holder.difference.text = Html.fromHtml(context.resources.getString(R.string.game_details_ranking_difference, differencePBToString, differenceBestToString), HtmlCompat.FROM_HTML_MODE_LEGACY)*/
    }
    
    private fun getDifferenceTextWithColorInHtmlFormat(differenceType: DifferenceType, difference: Long): String {
        return differenceType.resColor?.let { resColor ->
            val builder = StringBuilder().apply {
                append("<font color='")
                append(ContextCompat.getColor(context, resColor).toHexColorString())
                append("'>")
                append(differenceType.prefix).append(difference).append("s")
                append("</font>")
            }
            builder.toString()
        } ?: differenceType.prefix + difference
    }
    
    private fun getDifferenceType(differenceInSeconds: Long): DifferenceType {
        if (differenceInSeconds > 0) return DifferenceType.POSITIVE
        if (differenceInSeconds < 0) return DifferenceType.NEGATIVE
        return DifferenceType.EQUAL
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(RankingCellBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    
    
}
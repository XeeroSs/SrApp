package com.xeross.srapp

import com.xeross.srapp.components.ui.models.DataBar
import junitparams.JUnitParamsRunner
import junitparams.naming.TestCaseName
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.abs
import kotlin.math.round

@RunWith(JUnitParamsRunner::class)
class GraphicBarTest {
    
    @Test
    @TestCaseName("[{index}] | '{params}'")
    fun `On draw 3`() {
        
        val widthBar = (22f)
        val maxHeightBar = 70f
        val topHeightBar = 10f
        val cornerBar = (5 * 3)
        val space = 21f
        val spaceBetweenBar = (widthBar / 2)
        val measuredWidth = 300
        
        var barAmount = 0
        
        val availableWidth = abs(measuredWidth - (space * 2)).toInt()
        
        val totalBars = (availableWidth / (widthBar + spaceBetweenBar)).toInt()
        val widthWithoutOverflow = (totalBars * (widthBar + spaceBetweenBar))
        val overflow = (availableWidth - widthWithoutOverflow)
        
        var startBar = (space + ((overflow / 2) + (spaceBetweenBar / 2)))
        
        var indexBars = 0
        
        val bars = ArrayList<DataBar>(
            listOf(
                DataBar(1000),
                DataBar(300),
                DataBar(500),
                DataBar(1),
                DataBar(2),
            )
        )
        
        if (!bars.isNullOrEmpty()) {
            val barHighest = bars.reduce { acc, dataBar ->
                if (acc.data > dataBar.data) acc else dataBar
            }.data
            val barLowest = bars.reduce { acc, dataBar ->
                if (acc.data < dataBar.data) acc else dataBar
            }.data
            
            for (bar in bars) {
                val data = bar.data
                println("-----------------------")
                var percent = (((data - barLowest.toDouble()) /
                        (barHighest.toDouble() - barLowest)) * 100)
                
                // TODO("if(percent == 0.0)")
                
                println("$percent%")
            }
            
            repeat(totalBars) {
                barAmount++
                indexBars++
                startBar += (widthBar + spaceBetweenBar)
            }
            
        }
    }
    
    @Test
    @TestCaseName("[{index}] | '{params}'")
    fun `On draw 2`() {
        
        val widthBar = (22f)
        val maxHeightBar = 70f
        val topHeightBar = 10f
        val cornerBar = (5 * 3)
        val space = 21f
        val spaceBetweenBar = (widthBar / 2)
        val measuredWidth = 300
        
        val availableWidth = abs(measuredWidth - (space * 2)).toInt()
        
        val totalBars = (availableWidth / (widthBar + spaceBetweenBar)).toInt()
        val widthWithoutOverflow = (totalBars * (widthBar + spaceBetweenBar))
        val overflow = (availableWidth - widthWithoutOverflow)
        
        var startBar = (space + ((overflow / 2) + (spaceBetweenBar / 2)))
        
        var total = startBar
        
        repeat(totalBars) {
            println()
            startBar += (widthBar + spaceBetweenBar)
            total += (widthBar + spaceBetweenBar)
        }
        
        total += (space + (overflow / 2))
        
        println(total)
    }
    
    @Test
    @TestCaseName("[{index}] | '{params}'")
    fun `On draw`() {
        // Largeur de la vue
        val measuredWidth = 300
        // Heuteur de la vue
        val measuredHeight = 100
        // Largeur d'une graphic bar
        val widthBar = (15 * 3) // to pixel
        // Escpace entre les graphics bars
        val spaceBetweenBar = (5 * 3)
        // Hauteur minimum d'une graphic bars
        val minimumHeight = (widthBar * 10)
        
        // Nombre total de bars en fonction de la largeur de la vue (Ne prends pas en compte les espaces entre les graphics bars)
        val totalBars = abs(measuredWidth / widthBar)
        
        // Nombre total de bars en fonction de la largeur de la vue avec les espaces
        println(totalBars - round(spaceBetweenBar * (totalBars - 1f)))
        
        // espace entre la première graphics et la derniere
        val margin = abs(measuredWidth - (totalBars * widthBar))
        
        
        val defaultHeight = measuredHeight
        
        println("totalBars = ${totalBars}")
        println("margin = ${margin}")
        
        var start = margin
        
        // Draw
        for (i in 0..totalBars) {
            start = (start + widthBar)
            
        }
        
    }
}
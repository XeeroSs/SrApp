package com.xeross.srapp

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
package com.example.battlecity2_0.drawers

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.example.battlecity2_0.CELL_SIZE
import com.example.battlecity2_0.R

class GridDrawer (val container: FrameLayout){
    private  val alllines = mutableListOf<View>()
    fun  removeGrid(){
        alllines.forEach{
            container.removeView(it)
        }
    }
    fun drawGrid() {

        drawHorizontalLines()
        drawVerticalLines()
    }

    private fun drawHorizontalLines() {
        var topMargin = 0
        while (topMargin <= container!!.layoutParams.height){
            val horisontalLine=View(container.context)
            val layoutParams=FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,1)
            topMargin+= CELL_SIZE
            layoutParams.topMargin=topMargin
            horisontalLine.layoutParams=layoutParams
            horisontalLine.setBackgroundColor(container.context.resources.getColor(android.R.color.white))
            alllines.add(horisontalLine)
            container.addView(horisontalLine)
        }

    }

    private fun drawVerticalLines() {
        var leftMargin = 0
        while (leftMargin <= container!!.layoutParams.width){
            val verticalLine=View(container.context)
            val layoutParams=FrameLayout.LayoutParams(1,FrameLayout.LayoutParams.MATCH_PARENT)
            leftMargin+= CELL_SIZE
            layoutParams.leftMargin=leftMargin
            verticalLine.layoutParams=layoutParams
            verticalLine.setBackgroundColor(container.context.resources.getColor(android.R.color.white))
            alllines.add(verticalLine)
            container.addView(verticalLine)
        }
    }
}
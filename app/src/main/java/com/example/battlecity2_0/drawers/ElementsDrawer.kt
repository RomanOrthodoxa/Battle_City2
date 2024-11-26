package com.example.battlecity2_0.drawers

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.battlecity2_0.CELL_SIZE
import com.example.battlecity2_0.HORISONTAL_MAX_SIZE
import com.example.battlecity2_0.R
import com.example.battlecity2_0.VERTICAL_MAX_SIZE
import com.example.battlecity2_0.enums.Direction
import com.example.battlecity2_0.enums.Material
import com.example.battlecity2_0.models.Coordinate
import com.example.battlecity2_0.models.Element

class ElementsDrawer(val container:FrameLayout) {
    var currentMaterial = Material.EMPTY
    private val elementsOnContainer = mutableListOf<Element>()
    fun onTouchContainer(x: Float, y: Float) {
        val topMargin = y.toInt() - (y.toInt() % CELL_SIZE)
        val leftMargin = x.toInt() - (x.toInt() % CELL_SIZE)
        val coordinate = Coordinate(topMargin, leftMargin)
        if (currentMaterial == Material.EMPTY) {
            eraseView(coordinate)
        } else {
            drawOrReplaceView(coordinate)
        }

    }

    private fun drawOrReplaceView(coordinate: Coordinate) {
        val viewOnCoordinate = getElementsByCoordinates(coordinate)
        if (viewOnCoordinate == null) {
            drawView(coordinate)
            return
        }
        if (viewOnCoordinate.material != currentMaterial) {
            replaceView(coordinate)
        }
    }

    private fun replaceView(coordinate: Coordinate) {
        eraseView(coordinate)
        drawView(coordinate)
    }

    private fun getElementsByCoordinates(coordinate: Coordinate) =
        elementsOnContainer.firstOrNull { it.coordinate == coordinate }

    private fun eraseView(coordinate: Coordinate) {
        val elementOnCordinate = getElementsByCoordinates(coordinate)
        if (elementOnCordinate != null) {
            val erasingView = container.findViewById<View>(elementOnCordinate.viewId)
            container.removeView(erasingView)
            elementsOnContainer.remove(elementOnCordinate)
        }
    }

    fun drawView(coordinate: Coordinate) {
        val view = ImageView(container.context)
        val layoutParams = FrameLayout.LayoutParams(CELL_SIZE, CELL_SIZE)
        when (currentMaterial) {
            Material.EMPTY -> {

            }

            Material.BRICK -> view.setImageResource(R.drawable.topkirp)
            Material.CONCRETE -> view.setImageResource(R.drawable.stena)
            Material.GRASS -> view.setImageResource(R.drawable.travaa)
        }
        layoutParams.topMargin = coordinate.top
        layoutParams.leftMargin = coordinate.left
        val viewId = View.generateViewId()
        view.id = viewId
        view.layoutParams = layoutParams
        container.addView(view)
        elementsOnContainer.add(Element(viewId, currentMaterial, coordinate))
    }

     fun move(myTank: View, direction: Direction) {

        val layoutParams = myTank.layoutParams as FrameLayout.LayoutParams
         val currentCoordinate=Coordinate(layoutParams.topMargin,layoutParams.leftMargin)
        when (direction) {
            Direction.UP -> {
                myTank.rotation = 0f
                    (myTank.layoutParams as FrameLayout.LayoutParams).topMargin += -CELL_SIZE
            }

            Direction.DOWN -> {
                myTank.rotation = 180f
                    (myTank.layoutParams as FrameLayout.LayoutParams).topMargin += CELL_SIZE
            }

            Direction.LEFT -> {
                myTank.rotation = 90f
                    (myTank.layoutParams as FrameLayout.LayoutParams).topMargin += CELL_SIZE
            }

            Direction.RIGHT -> {
                myTank.rotation = 270f
                    (myTank.layoutParams as FrameLayout.LayoutParams).topMargin -= CELL_SIZE
            }

        }
         val nextCoordinate=Coordinate(layoutParams.topMargin,layoutParams.leftMargin)
         if (chekTankCanMoveThroughBorder(nextCoordinate,myTank)&&chekTankCanMoveThroughMaterial(nextCoordinate)){
             container.removeView(myTank)
             container.addView(myTank,0)
         }else{
             (myTank.layoutParams as FrameLayout.LayoutParams).topMargin=currentCoordinate.top
             (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin=currentCoordinate.left
         }
    }
    private fun getTankCoordinates(topleftCoordinate: Coordinate):List<Coordinate>{
        val coordinateList= mutableListOf<Coordinate>()
        coordinateList.add(topleftCoordinate)
        coordinateList.add(Coordinate(topleftCoordinate.top+ CELL_SIZE,topleftCoordinate.left))
        coordinateList.add(Coordinate(topleftCoordinate.top,topleftCoordinate.left+ CELL_SIZE))
        coordinateList.add(Coordinate(topleftCoordinate.top+ CELL_SIZE,topleftCoordinate.left+ CELL_SIZE))
        return coordinateList
    }
private fun chekTankCanMoveThroughMaterial(coordinate: Coordinate):Boolean{
getTankCoordinates(coordinate).forEach{
    val element=getElementsByCoordinates(it)
    if (element!=null&&!element.material.tankCanGoThrough){
        return false
    }
}
    return true
}
    private fun chekTankCanMoveThroughBorder(coordinate: Coordinate,myTank: View):Boolean{
        if (coordinate.top>=0&&coordinate.top+myTank.height< HORISONTAL_MAX_SIZE&&coordinate.left>=0&&coordinate.left+myTank.width<= VERTICAL_MAX_SIZE){
            return true
        }
        return false
    }



}
package com.example.battlecity2_0

import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_DPAD_DOWN
import android.view.KeyEvent.KEYCODE_DPAD_LEFT
import android.view.KeyEvent.KEYCODE_DPAD_RIGHT
import android.view.KeyEvent.KEYCODE_DPAD_UP
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.battlecity2_0.drawers.ElementsDrawer
import com.example.battlecity2_0.drawers.GridDrawer
import com.example.battlecity2_0.enums.Direction
import com.example.battlecity2_0.enums.Material
import com.example.battlecity2_0.models.Coordinate
import java.nio.file.Files.move

const val CELL_SIZE=45
const val VERTICAL_CELL_AMOUNT=28
const val HORISONTAL_CELL_AMOUNT=15
const val VERTICAL_MAX_SIZE= CELL_SIZE* VERTICAL_CELL_AMOUNT
const val HORISONTAL_MAX_SIZE= CELL_SIZE* HORISONTAL_CELL_AMOUNT
class MainActivity : AppCompatActivity() {
    private var editMode=false
    private val gridDrawer by lazy {
        val container=findViewById<FrameLayout>(R.id.main)
        GridDrawer(container)
    }
    private val elementsDrawer by lazy {
        val container=findViewById<FrameLayout>(R.id.main)
        ElementsDrawer(container)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val container=findViewById<FrameLayout>(R.id.main)
        container.layoutParams=FrameLayout.LayoutParams(VERTICAL_MAX_SIZE, HORISONTAL_MAX_SIZE)
        val editor_clear=findViewById<ImageView>(R.id.editor_clear)
        val editor_brick=findViewById<ImageView>(R.id.editor_brick)
        val editor_concrete=findViewById<ImageView>(R.id.editor_concerte)
        val editor_grass=findViewById<ImageView>(R.id.editor_grass)
        editor_clear.setOnClickListener{ elementsDrawer.currentMaterial=Material.EMPTY}
        editor_brick.setOnClickListener{ elementsDrawer.currentMaterial=Material.BRICK}
        editor_concrete.setOnClickListener{ elementsDrawer.currentMaterial=Material.CONCRETE}
        editor_grass.setOnClickListener{ elementsDrawer.currentMaterial=Material.GRASS}
        container.setOnTouchListener{_,event->
            elementsDrawer.onTouchContainer(event.x,event.y)
            return@setOnTouchListener true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_settings->{
                switchEditMode()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun switchEditMode(){
        val materials_container=findViewById<LinearLayout>(R.id.materials_container)
        if (editMode){
            gridDrawer.removeGrid()
            materials_container.visibility= GONE
        } else{
            gridDrawer.drawGrid()
            materials_container.visibility=VISIBLE
        }
        editMode=!editMode
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val myTank=findViewById<ImageView>(R.id.MyTank)
        when(keyCode){
            KEYCODE_DPAD_UP->elementsDrawer.move(myTank,Direction.UP)
            KEYCODE_DPAD_DOWN->elementsDrawer.move(myTank,Direction.DOWN)
            KEYCODE_DPAD_LEFT->elementsDrawer.move(myTank, Direction.LEFT)
            KEYCODE_DPAD_RIGHT->elementsDrawer.move(myTank, Direction.RIGHT)
        }
        return super.onKeyDown(keyCode, event)
    }

    }

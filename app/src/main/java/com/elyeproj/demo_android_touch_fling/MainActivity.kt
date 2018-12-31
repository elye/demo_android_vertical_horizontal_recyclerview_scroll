package com.elyeproj.demo_android_touch_fling

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.horizontal_view.view.*
import kotlinx.android.synthetic.main.vertical_cell.view.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view_list.layoutManager = LinearLayoutManager(this)
        view_list.adapter = MyAdapter(this)

        view_list.addOnItemTouchListener(object: RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                if (e.action == MotionEvent.ACTION_DOWN &&
                    rv.scrollState == RecyclerView.SCROLL_STATE_SETTLING) {
                    rv.stopScroll()
                }
                return false
            }
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })
    }
}

class MyAdapter(val context: Context) : RecyclerView.Adapter<MyViewHolder>() {
    val item = listOf("#FF0000", "LIST", "#00FF00", "LIST", "#0000FF", "LIST", "#FFFF00", "LIST", "#FF00FF", "LIST", "#00FFFF")

    companion object {
        const val TYPE_NORMAL = 0
        const val TYPE_LIST = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): MyViewHolder {
        return when (type) {
            TYPE_LIST ->
                ListViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.horizontal_view, parent, false), context
                )
            else ->
                MyViewHolder(context,
                    LayoutInflater.from(context)
                        .inflate(R.layout.vertical_cell, parent, false)
                )
        }

    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun getItemViewType(position: Int): Int {
        if (item[position] == "LIST") return TYPE_LIST
        return TYPE_NORMAL
    }

    override fun onBindViewHolder(view: MyViewHolder, position: Int) {
        view.populate(item[position])
    }
}

class SimpleAdapter(val context: Context) : RecyclerView.Adapter<MyViewHolder>() {
    val item = listOf("#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF", "#00FFFF")

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): MyViewHolder {
        return MyViewHolder(context,
            LayoutInflater.from(context)
                .inflate(R.layout.horizontal_cell, parent, false))
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(view: MyViewHolder, position: Int) {
        view.populate(item[position])
    }
}

open class MyViewHolder(private val context: Context, private val view: View) : RecyclerView.ViewHolder(view) {
    open fun populate(color: String) {
        view.container.setBackgroundColor(Color.parseColor(color))
        view.setOnClickListener {
            Log.d("TraceTouch", "MyViewHolder is clicked")
            Toast.makeText(context, "MyViewHolder is clicked", Toast.LENGTH_SHORT).show()
        }
    }
}

class ListViewHolder(private val view: View, val context: Context) : MyViewHolder(context, view) {
    override fun populate(color: String) {
        view.view_list_horizontal.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.view_list_horizontal.adapter = SimpleAdapter(context)
    }
}


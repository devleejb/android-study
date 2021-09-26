package com.example.ch11_jetpack

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devleejb.ch11_jetpack.R
import com.devleejb.ch11_jetpack.databinding.FragmentOneBinding
import com.devleejb.ch11_jetpack.databinding.FragmentTwoBinding
import com.devleejb.ch11_jetpack.databinding.ItemRecyclerviewBinding

class OneFragment : Fragment() {
    class MyViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

    class MyAdapter(val datas: MutableList<String>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return MyViewHolder(
                ItemRecyclerviewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val binding = (holder as MyViewHolder).binding

            binding.itemData.text = datas[position]
        }

        override fun getItemCount(): Int {
            return datas.size
        }
    }

    class MyDecoration(val context: Context) : RecyclerView.ItemDecoration() {
        override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            super.onDrawOver(c, parent, state)

            val width = parent.width
            val height = parent.height

            val dr: Drawable? = ResourcesCompat.getDrawable(context.resources, R.drawable.kbo, null)
            val drWidth = dr?.intrinsicWidth
            val drHeight = dr?.intrinsicHeight

            val left = width / 2 - drWidth?.div(2) as Int
            val top = height / 2 - drHeight?.div(2) as Int

            c.drawBitmap(
                BitmapFactory.decodeResource(context.resources, R.drawable.kbo),
                left.toFloat(),
                top.toFloat(),
                null
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOneBinding.inflate(inflater, container, false)

        val datas = mutableListOf<String>()

        for (i in 1..9) {
            datas.add("Item $i")
        }

        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager

        val adapter = MyAdapter(datas)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(MyDecoration(activity as Context))

        return binding.root
    }

}
package com.example.irpof.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.irpof.R
import com.example.irpof.databinding.PagesItemBinding

class PageAdapter(private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<PageAdapter.ViewHolder>() {
        private var items = mutableListOf<String>()
    private var expandedPosition = 0




    // Method to update the data in the adapter
    @SuppressLint("NotifyDataSetChanged")
    fun setData(pages: MutableList<String>) {
        items = pages
        Log.d("CustomerInfoAdapterParent", "Data set: $items")
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(PagesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
        val isExpanded = position == expandedPosition
        if (isExpanded) {
            holder.binding.selectedPageColor.visibility = View.VISIBLE
            holder.binding.pagesLl.background = ColorDrawable(Color.parseColor("#B9CAFB"))

        } else {
            holder.binding.selectedPageColor.visibility = View.INVISIBLE
            holder.binding.pagesLl.setBackgroundColor(
                ContextCompat.getColor(
                    holder.binding.root.context,
                    R.color.white
                )
            )
        }
        holder.binding.pagesLl.setOnClickListener {
            itemClickListener.onItemClick(items[position])
            expandedPosition = if (isExpanded) RecyclerView.NO_POSITION
            else position
            notifyDataSetChanged()

        }
    }

    class ViewHolder(
        val binding: PagesItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(page: String) {
            binding.pageTittle.text = page
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: String)
    }

}
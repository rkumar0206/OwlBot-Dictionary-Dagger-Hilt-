package com.rohitTheBest.owlbotdictionaryretrofit.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rohitTheBest.owlbotdictionaryretrofit.R
import com.rohitTheBest.owlbotdictionaryretrofit.data.model.Definition
import kotlinx.android.synthetic.main.main_activity_rv_layout.view.*
import kotlin.random.Random

class MainActivityRVAdapter :
    ListAdapter<Definition, MainActivityRVAdapter.RVViewHolder>(DiffUtilCallBack()) {

    inner class RVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Definition>() {

        override fun areItemsTheSame(oldItem: Definition, newItem: Definition): Boolean {

            return oldItem.definition == newItem.definition
        }

        override fun areContentsTheSame(oldItem: Definition, newItem: Definition): Boolean {
            return oldItem.definition == newItem.definition
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {

        return RVViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_activity_rv_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {

        holder.itemView.apply {

            getItem(position)?.let {

                val r = Random.nextInt(60, 255)
                val g = Random.nextInt(60, 255)
                val b = Random.nextInt(60, 255)

                cardView3.setCardBackgroundColor(Color.argb(100, r, g, b))

                searchResultTypeTV.text = it.type
                searchResultDefinationTV.text = it.definition
                searchResultExampleTV.text = it.example
            }
        }
    }
}
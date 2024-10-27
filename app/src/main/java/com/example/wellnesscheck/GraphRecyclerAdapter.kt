package com.example.wellnesscheck

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GraphRecyclerAdapter(private val graphs: List<GraphItem>) :
    RecyclerView.Adapter<GraphRecyclerAdapter.GraphViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GraphViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_graph_card, parent, false)
        return GraphViewHolder(view)
    }

    override fun onBindViewHolder(holder: GraphViewHolder, position: Int) {
        val graph = graphs[position]
        holder.bind(graph)
    }

    override fun getItemCount(): Int = graphs.size

    class GraphViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val graphTitle: TextView = itemView.findViewById(R.id.graphTitle)
        private val graphValue: TextView = itemView.findViewById(R.id.graphValue)
        private val graphChange: TextView = itemView.findViewById(R.id.graphChange)
        private val graphImage: ImageView = itemView.findViewById(R.id.graphImage)

        fun bind(graph: GraphItem) {
            graphTitle.text = graph.title
            graphValue.text = graph.value
            graphChange.text = graph.change
            graphImage.setImageResource(graph.imageResId)
        }
    }
}

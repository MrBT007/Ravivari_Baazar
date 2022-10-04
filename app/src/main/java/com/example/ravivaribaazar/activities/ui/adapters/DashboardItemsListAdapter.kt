package com.example.ravivaribaazar.activities.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ravivaribaazar.R
import com.example.ravivaribaazar.models.Product
import com.example.ravivaribaazar.utils.GlideLoader
import kotlinx.android.synthetic.main.item_dashboard_layout.view.*

open class DashboardItemsListAdapter(private val context: Context,private var list:ArrayList<Product>):RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_dashboard_layout,parent,false))
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if(holder is MyViewHolder)
        {
            GlideLoader(context).loadProductPicture(model.image,holder.itemView.iv_dashboard_item_image)
            holder.itemView.tv_dashboard_item_title.text = model.title
            holder.itemView.tv_dashboard_item_price.text = "₹${model.price}"
            holder.itemView.tv_dashboard_item_description.text = model.description
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
package com.utch.gastoscontrol.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utch.gastoscontrol.R
import com.utch.gastoscontrol.models.Expense
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class ExpenseAdapter(
    initial: List<Expense>,
    private val listener: OnItemInteraction
) : RecyclerView.Adapter<ExpenseAdapter.VH>() {

    interface OnItemInteraction { fun onClick(e: Expense); fun onDelete(e: Expense) }

    private var data: List<Expense> = initial
    private val mx = NumberFormat.getCurrencyInstance(Locale("es","MX"))
    private val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    fun setData(list: List<Expense>) { data = list; notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, pos: Int) {
        val e = data[pos]
        h.tvTitle.text = e.title
        h.tvAmount.text = mx.format(e.amount)
        h.tvCat.text = e.category
        h.tvDate.text = sdf.format(Date(e.dateMillis))
        h.itemView.setOnClickListener { listener.onClick(e) }
        h.btnDelete.setOnClickListener { listener.onDelete(e) }
    }

    override fun getItemCount() = data.size

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvTitle: TextView = v.findViewById(R.id.tvTitle)
        val tvAmount: TextView = v.findViewById(R.id.tvAmount)
        val tvCat: TextView = v.findViewById(R.id.tvCat)
        val tvDate: TextView = v.findViewById(R.id.tvDate)
        val btnDelete: ImageButton = v.findViewById(R.id.btnDelete)
    }
}

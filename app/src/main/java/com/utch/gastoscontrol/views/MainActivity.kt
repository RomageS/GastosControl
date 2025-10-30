package com.utch.gastoscontrol.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utch.gastoscontrol.R
import com.utch.gastoscontrol.controllers.DataController
import com.utch.gastoscontrol.models.Expense
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity(), ExpenseAdapter.OnItemInteraction {

    private lateinit var controller: DataController
    private lateinit var adapter: ExpenseAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTotal: TextView
    private lateinit var btnAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        controller = DataController(this)

        recyclerView = findViewById(R.id.recyclerView)
        tvTotal = findViewById(R.id.tvTotal)
        btnAdd = findViewById(R.id.btnAdd)

        adapter = ExpenseAdapter(emptyList(), this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnAdd.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java))
        }

        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        val expenses = controller.all()
        adapter.setData(expenses)

        val total = controller.total()
        val formatter = NumberFormat.getCurrencyInstance(Locale("es", "MX"))
        tvTotal.text = "Total: ${formatter.format(total)}"
    }

    override fun onClick(e: Expense) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("EXPENSE_ID", e.id)
        startActivity(intent)
    }

    override fun onDelete(e: Expense) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar gasto")
            .setMessage("¿Estás seguro de eliminar '${e.title}'?")
            .setPositiveButton("Sí") { _, _ ->
                controller.delete(e.id)
                loadData()
            }
            .setNegativeButton("No", null)
            .show()
    }
}

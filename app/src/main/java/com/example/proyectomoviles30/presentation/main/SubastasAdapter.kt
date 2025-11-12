package com.example.proyectomoviles30.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomoviles30.R

class SubastasAdapter(
    private val subastas: List<Subasta>,
    private val onItemClick: (Subasta) -> Unit
) : RecyclerView.Adapter<SubastasAdapter.SubastaViewHolder>() {

    class SubastaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen: ImageView = itemView.findViewById(R.id.imageViewProducto)
        val titulo: TextView = itemView.findViewById(R.id.textViewTituloProducto)
        val puja: TextView = itemView.findViewById(R.id.textViewPujaActual)
        val tiempo: TextView = itemView.findViewById(R.id.textViewTiempoRestante)
        val botonVer: TextView = itemView.findViewById(R.id.textViewPujar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubastaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subasta, parent, false)
        return SubastaViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubastaViewHolder, position: Int) {
        val subasta = subastas[position]

        holder.titulo.text = subasta.titulo
        holder.puja.text = String.format("$%,.2f MXN", subasta.pujaActual)
        holder.tiempo.text = subasta.tiempoRestante


        holder.botonVer.setOnClickListener {
            onItemClick(subasta)
        }
        holder.itemView.setOnClickListener {
            onItemClick(subasta)
        }
    }

    override fun getItemCount(): Int {
        return subastas.size
    }
}
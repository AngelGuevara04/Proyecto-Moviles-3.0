package com.example.proyectomoviles30.presentation.main

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomoviles30.R
import com.example.proyectomoviles30.domain.model.Subasta

class SubastasAdapter(
    private var subastas: List<Subasta>,
    private val onItemClick: (Subasta) -> Unit
) : RecyclerView.Adapter<SubastasAdapter.SubastaViewHolder>() {

    fun updateList(newList: List<Subasta>) {
        subastas = newList
        notifyDataSetChanged()
    }

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
        holder.tiempo.text = subasta.tiempoRestante // En el XML el label ya dice "Fecha Límite"

        if (subasta.imageUrl.isNotEmpty()) {
            try {
                holder.imagen.setImageURI(Uri.parse(subasta.imageUrl))
            } catch (e: Exception) {
                holder.imagen.setImageResource(android.R.drawable.ic_menu_gallery)
            }
        } else {
            holder.imagen.setImageResource(android.R.drawable.ic_menu_gallery)
        }

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
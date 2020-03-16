package com.example.englishstories

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(val context:Context, val notes:List<Note>, val onItemClick:(Int)->Unit): RecyclerView.Adapter<NotesAdapter.myViewHolder>(){

    inner class myViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val note_date = itemView.findViewById<TextView>(R.id.textViewNoteDate)
        val note_title = itemView.findViewById<TextView>(R.id.textViewNoteName)

        fun bindData(note:Note, context: Context){
            note_date.text = note.date
            note_title.text = note.title
            itemView.setBackgroundResource(if (note.done) R.color.colorDone else R.color.colorOngoing)

            itemView.setOnClickListener {
                onItemClick(note.id)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false)
        return myViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.count()
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.bindData(notes[position], context)
        AnimationHelper.animate(holder.itemView)
    }
}
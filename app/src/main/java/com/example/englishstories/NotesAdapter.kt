package com.example.englishstories

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(
    private val context: Context,
    private val notes: List<Note>,
    val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<NotesAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val noteDate: TextView = itemView.findViewById(R.id.textViewNoteDate)
        private val noteTitle: TextView = itemView.findViewById(R.id.textViewNoteName)
        private val noteDone: ImageView = itemView.findViewById(R.id.imageViewComplete)


        fun bindData(note: Note, context: Context) {
            noteDate.text = note.date
            noteTitle.text = note.title
            noteDone.setBackgroundResource(if (note.done) R.color.colorDone else R.color.colorOngoing)
            noteDone.setImageResource(if (note.done) R.drawable.ic_save_white_24dp else R.drawable.ic_clear_white_24dp)


            itemView.setOnClickListener {
                onItemClick(note.id)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(notes[position], context)
        AnimationHelper.animate(holder.itemView)
    }
}
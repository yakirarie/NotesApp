package com.example.englishstories

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    var db: SQLiteDatabase? = null
    var cursor: Cursor? = null
    var noteAdapter: NotesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        floatingActionButtonAddNote.setOnClickListener {
            val intent = Intent(this, AddAndEditNoteActivity::class.java)
            startActivity(intent)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.done_notes) {
            cursor = db!!.query(
                "NOTES",
                arrayOf("_id", "title", "description", "date", "done"),
                "done=?",
                arrayOf("1"),
                null,
                null,
                null
            )

            populateRecyclerView()
        }

        if (item.itemId == R.id.not_done_notes) {
            cursor = db!!.query(
                "NOTES",
                arrayOf("_id", "title", "description", "date", "done"),
                "done=?",
                arrayOf("0"),
                null,
                null,
                null
            )

            populateRecyclerView()

        }

        if (item.itemId == R.id.all_notes) {
            cursor = db!!.query(
                "NOTES",
                arrayOf("_id", "title", "description", "date", "done"),
                null,
                null,
                null,
                null,
                null
            )

            populateRecyclerView()

        }

        return super.onOptionsItemSelected(item)

    }

    private fun populateRecyclerView() {
        var listOfNotes = mutableListOf<Note>()
        while (cursor!!.moveToNext()) {
            val noteId = cursor!!.getInt(0)
            val noteTitle = cursor!!.getString(1)
            val noteDescription = cursor!!.getString(2)
            val noteDate = cursor!!.getString(3)
            val noteDone = cursor!!.getInt(4)

            val note = Note(noteId, noteDate, noteTitle, noteDescription, noteDone != 0)
            listOfNotes.add(note)

        }

        noteAdapter = NotesAdapter(this, listOfNotes) { noteId ->
            val intent = Intent(this, ViewNoteActivity::class.java)
            intent.putExtra("NOTE_ID", noteId)
            startActivity(intent)
        }
        val notesLayoutManager = GridLayoutManager(this, 2)

        recyclerViewNotes.adapter = noteAdapter
        recyclerViewNotes.layoutManager = notesLayoutManager
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter_notes_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        cursor!!.close()
        db!!.close()

    }

    override fun onStart() {
        super.onStart()

        val objToCreateDB = NoteSQLiteOpenHelper(this)
        db = objToCreateDB.readableDatabase
        cursor = db!!.query(
            "NOTES",
            arrayOf("_id", "title", "description", "date", "done"),
            null,
            null,
            null,
            null,
            null
        )
        populateRecyclerView()

    }
}

package com.example.englishstories

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_full_text.*
import kotlinx.android.synthetic.main.content_full_text.*

class ViewNoteActivity : AppCompatActivity() {

    var db: SQLiteDatabase? = null
    var cursor: Cursor? = null
    var noteId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_text)
        setSupportActionBar(toolbar)
        val notesDatabase = NoteSQLiteOpenHelper(this)
        noteId = intent.extras.get("NOTE_ID").toString()

        db = notesDatabase.readableDatabase

        cursor = db!!.query(
            "NOTES",
            arrayOf("title", "description", "date", "done"),
            "_id=?",
            arrayOf(noteId),
            null,
            null,
            null
        )

        if (cursor!!.moveToFirst()) {
            val taskStatus = if(cursor!!.getInt(3) != 0) "הושלם!" else "בטיפול..."
            title =cursor!!.getString(0)
            textViewStoryFullText.text ="עדכון אחרון - ${cursor!!.getString(2)}\n\nסטטוס - $taskStatus\n\n ${cursor!!.getString(1)}"

        }


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.delete_note) {

            AlertDialog.Builder(this)
                .setTitle("מחיקה")
                .setMessage("האם אתה בטוח שאתה רוצה למחוק את \"$title\"?")

                .setPositiveButton("כן") { dialogInterface, i ->
                    db!!.delete("NOTES", "_id=?", arrayOf(noteId))
                    Toast.makeText(this, "נמחק בהצלחה!", Toast.LENGTH_LONG).show()
                    finish()
                }
                .setNegativeButton("לא") { dialogInterface, i ->
                }
                .create()
                .show()


        }

        if (item.itemId == R.id.edit_note) {

            val intent = Intent(applicationContext, AddAndEditNoteActivity::class.java)
            intent.putExtra("NOTE_ID", noteId)
            startActivity(intent)
            finish()


        }

        return super.onOptionsItemSelected(item)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.view_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onDestroy() {
        super.onDestroy()
        db!!.close()
    }
}

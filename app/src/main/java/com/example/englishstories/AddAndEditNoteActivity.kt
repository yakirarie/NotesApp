package com.example.englishstories

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_note.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddAndEditNoteActivity : AppCompatActivity() {

    var dbWrite: SQLiteDatabase? = null
    var dbRead: SQLiteDatabase? = null
    var cursor: Cursor? = null
    var noteId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        val notesDataBase = NoteSQLiteOpenHelper(this)
        dbWrite = notesDataBase.writableDatabase
        dbRead = notesDataBase.readableDatabase

        noteId = intent?.extras?.get("NOTE_ID")?.toString()
        if (noteId != null) {
            cursor = dbRead!!.query(
                "NOTES",
                arrayOf("title", "description", "done"),
                "_id=?",
                arrayOf(noteId),
                null,
                null,
                null
            )

            if (cursor!!.moveToFirst()) {
                editTextNoteTitle.setText(cursor!!.getString(0))
                editTextNoteDescription.setText(cursor!!.getString(1))
                checkBoxTaskFinished.isChecked = cursor!!.getInt(2) != 0
            }


        }


    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.save_note) {

            val newNoteValues = ContentValues()
            newNoteValues.put(
                "TITLE",
                if (editTextNoteTitle.text.isEmpty()) "ללא כותרת" else editTextNoteTitle.text.toString()
            )
            newNoteValues.put(
                "DESCRIPTION",
                if (editTextNoteDescription.text.isEmpty()) "אין תיאור" else editTextNoteDescription.text.toString()
            )
            newNoteValues.put("DATE", getDate())
            newNoteValues.put("DONE", if(checkBoxTaskFinished.isChecked) 1 else 0)


            if (noteId != null) {

                dbWrite!!.update("NOTES", newNoteValues, "_id=?", arrayOf(noteId))
                Toast.makeText(this, "עודכן בהצלחה!", Toast.LENGTH_LONG).show()

            } else {


                dbWrite!!.insert("NOTES", null, newNoteValues)
                Toast.makeText(this, "נוסף בהצלחה!", Toast.LENGTH_LONG).show()
                editTextNoteTitle.setText("")
                editTextNoteDescription.setText("")
                checkBoxTaskFinished.isChecked = false
                editTextNoteTitle.requestFocus()



            }




        }
        return super.onOptionsItemSelected(item)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        dbWrite!!.close()
        dbRead!!.close()
    }

    fun getDate():String{
        val answer:String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            answer =  current.format(formatter)
        } else {
            var date = Date();
            val formatter = SimpleDateFormat("MMM dd yyyy")
            answer = formatter.format(date)
        }
        return answer
    }

}

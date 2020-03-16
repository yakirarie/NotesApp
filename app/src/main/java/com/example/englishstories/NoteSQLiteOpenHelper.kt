package com.example.englishstories


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteSQLiteOpenHelper(context: Context) : SQLiteOpenHelper(context, "NotesDB", null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL("create table notes(_id integer primary key autoincrement, title text, description text,date text ,done integer) ")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}
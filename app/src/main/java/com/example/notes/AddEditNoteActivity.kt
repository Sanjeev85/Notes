package com.example.notes

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {
    //on below line we are creating variables for our UI components.
    lateinit var noteTitleEdt: EditText
    lateinit var noteEdt: EditText
    lateinit var saveBtn: Button
    lateinit var cv1: CardView
    lateinit var cv2: CardView
    lateinit var cv3: CardView

    //on below line we are creating variable for viewmodal and and integer for our note id.
    lateinit var viewModal: NoteViewModal
    var noteID = -1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        //on below line we are initlaiing our view modal.
        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModal::class.java)
        //on below line we are initializing all our variables.
        noteTitleEdt = findViewById(R.id.idEdtNoteName)
        noteEdt = findViewById(R.id.idEdtNoteDesc)
        saveBtn = findViewById(R.id.idBtn)
        val rl = findViewById<RelativeLayout>(R.id.rl)
        cv1 = findViewById(R.id.cv1)
        cv2 = findViewById(R.id.cv2)
        cv3 = findViewById(R.id.cv3)
        cv1.setOnClickListener {
            noteTitleEdt.setBackgroundColor(Color.parseColor("#c3f09c"))
            noteEdt.setBackgroundColor(Color.parseColor("#c3f09c"))
            noteTitleEdt.setHintTextColor(Color.BLACK)
            rl.setBackgroundColor(Color.WHITE)
            noteEdt.setHintTextColor(Color.BLACK)
            noteEdt.setTextColor(Color.BLACK)
            noteTitleEdt.setTextColor(Color.BLACK)
        }
        cv2.setOnClickListener {
            rl.setBackgroundColor(Color.WHITE)
            noteEdt.setTextColor(Color.BLACK)
            noteTitleEdt.setTextColor(Color.BLACK)
            noteTitleEdt.setHintTextColor(Color.BLACK)
            noteEdt.setHintTextColor(Color.BLACK)
            noteTitleEdt.setBackgroundColor(Color.parseColor("#ffed37"))
            noteEdt.setBackgroundColor(Color.parseColor("#ffed37"))
        }
        cv3.setOnClickListener {
            rl.setBackgroundColor(Color.WHITE)
            noteEdt.setTextColor(Color.BLACK)
            noteTitleEdt.setTextColor(Color.BLACK)
            noteTitleEdt.setHintTextColor(Color.BLACK)
            noteEdt.setHintTextColor(Color.BLACK)
            noteTitleEdt.setBackgroundColor(Color.parseColor("#9faeff"))
            noteEdt.setBackgroundColor(Color.parseColor("#9faeff"))
        }


        //on below line we are getting data passsed via an intent.
        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            //on below line we are setting data to edit text.
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteId", -1)
            saveBtn.text = "Update Note"
            noteTitleEdt.setText(noteTitle)
            noteEdt.setText(noteDescription)
        } else {
            saveBtn.setText("Save Note")
        }

        //on below line we are adding click listner to our save button.
        saveBtn.setOnClickListener {
            //on below line we are getting title and desc from edit text.
            val noteTitle = noteTitleEdt.text.toString()
            val noteDescription = noteEdt.text.toString()
            //on below line we are checking the type and then saving or updating the data.
            if (noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    val updatedNote = Note(noteTitle, noteDescription, currentDateAndTime)
                    updatedNote.id = noteID
                    viewModal.updateNote(updatedNote)
                    Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
                }
            } else {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    //if the string is not empty we are calling a add note method to add data to our room database.
                    viewModal.addNote(Note(noteTitle, noteDescription, currentDateAndTime))
                    Toast.makeText(this, "$noteTitle Added", Toast.LENGTH_LONG).show()
                }
            }
            //opening the new activity on below line
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }
}
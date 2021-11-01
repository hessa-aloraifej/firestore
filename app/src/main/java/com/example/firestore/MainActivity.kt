package com.example.firestore

import android.app.Application
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private  val vm by lazy { ViewModelProvider(this).get(MyVM::class.java) }
    lateinit var saveBtn:Button
    lateinit var etNote:EditText
    lateinit var myRV:RecyclerView
    lateinit var rvAdapter:RVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saveBtn=findViewById(R.id.button)
        etNote=findViewById(R.id.etNote)
        myRV=findViewById(R.id.myRV)
        vm.getNote()
        vm.getNotes().observe(this, {
                list -> rvAdapter.update(list)


        })

        saveBtn.setOnClickListener {

           val noteText=etNote.text.toString()
           vm.save("",noteText)
            etNote.text.clear()

        }
       rvAdapter=RVAdapter(this)
        myRV.adapter =rvAdapter
        myRV.layoutManager = LinearLayoutManager(this)



    }
  //  fun updateRV( list:ArrayList<Note>){
    //        myRV.adapter=RVAdapter(this,list)
    //        myRV.layoutManager=LinearLayoutManager(this)
    //    }

    fun customAlert(NoteID:String){


        val dialogBuilder = AlertDialog.Builder(this)
        val input = EditText(this)
        dialogBuilder.setMessage("Edit Your Note")
            .setPositiveButton("Ok", DialogInterface.OnClickListener {
                    dialog, id ->
                vm.edit(NoteID , input.text.toString())
                // updateRV(vm.db.NoteDao().getAllNote())

            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id ->dialog.cancel()
            })

        val alert = dialogBuilder.create()

        alert.setTitle("Edit Note")
        alert.setView(input)
        alert.show()
    }

    fun confirmAlert(NoteID:String){


        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage("Are You Sure To Delete This Note?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id ->
                vm.deleteNote(NoteID)
                // updateRV(vm.db.NoteDao().getAllNote())
            })
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id ->dialog.cancel()
            })

        val alert = dialogBuilder.create()

        alert.setTitle("Confirmation")
        alert.show()
    }
}






package com.example.firestore

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

 class MyVM (application: Application): AndroidViewModel(application) {
     var TAG: String = "IAmMainActivity"
     val db = Firebase.firestore
     val list: MutableLiveData<List<Note>>
     val notes: ArrayList<Note> = arrayListOf()


     init {
         list = MutableLiveData()
     }


     fun getNotes(): MutableLiveData<List<Note>> {
         return list
     }

     fun save(id: String, note: String) {
         val note = hashMapOf(
             "notetext" to note,

             )

         db.collection("Note").add(note)
             .addOnSuccessListener { Log.w("MainActivity", "Saving Data Successfully.")

             }
             .addOnFailureListener { exception ->
                 Log.w(TAG, "Error Saving Data.", exception)
             }
       //getNote()

     }

     fun getNote() {
         db.collection("Note")
             .get()
             .addOnSuccessListener { result ->


                 for (document in result) {

                     Log.d(TAG, "${document.id} => ${document.data}")
                     document.data.map { (key, value)
                         ->
                         notes.add(Note(document.id, value as String))

                     }
                 }
                 list.postValue(notes)


             }
             .addOnFailureListener { exception ->
                 Log.w(TAG, "Error getting documents.", exception)
             }
     }

     fun deleteNote(noteID: String) {


         db.collection("Note")
             .get()
             .addOnSuccessListener { result ->
                 for (document in result) {
                     println("DB: ${document.id}")
                     println("LOCAL: $noteID")
                     if (document.id == noteID) {
                         db.collection("Note").document(noteID).delete()
                     }
                 }

             }
             .addOnFailureListener { exception ->
                 Log.w("MainActivity", "Error getting documents.", exception)
             }

     }
     fun edit(noteID: String, noteText: String){

             db.collection("Note")
                 .get()
                 .addOnSuccessListener { result ->
                     for (document in result) {

                         if(document.id == noteID){
                             db.collection("Note").document(noteID).update("notetext", noteText)
                         }
                     }
                     getNote()
                 }
                 .addOnFailureListener { exception ->
                     Log.w("MainActivity", "Error getting documents.", exception)
                 }
         }

 }
package com.example.shymko_hw_lesson15_130121_Text

import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

/*Створити програму яка містить список (RecyclerView),  поля введення імені та фамілії
і кнопку яка додає у список елемент з іменем та фамілією.
Можна використати домашнє завдання з лекції RecyclerView як основу.
///У списку ViewHolder має містити лише 1 TextView з іконкою (compound drawable).
///У списку у кожного елементу фамілія має бути підкресленою та виділена іншим кольором.
При введені імені та фамілії має бути валідація на мінімальну кількість символів,
на доступ для введення лише літер та 1-й символ UpperCase.
При не проходжені валіадціїї забороняти додавання нового елементу в список.*/

class MainActivity : AppCompatActivity() {
    companion object {
        val personList = mutableListOf<Person>()
        val adapter = PersonAdapter(personList)
        var firstNameIsOK: Boolean = false
        var secondNameIsOK: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvPerson.adapter = adapter
        rvPerson.layoutManager = LinearLayoutManager(this)
        setTextWatchers()
        btnAddNewPerson.setOnClickListener {
            makeTextSpanable()
        }
    }

    fun setTextWatchers() {
        etFirstName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var firtsName = etFirstName.text.toString()
                try {
                    if ((firtsName.toCharArray()[0].isUpperCase()) && (firtsName.length > 1)) {
                        firstNameIsOK = true
                    } else {
                        firstNameIsOK = false
                        btnAddNewPerson.isEnabled = false
                    }
                } catch (e: ArrayIndexOutOfBoundsException) {
                    btnAddNewPerson.isEnabled = false
                    Log.d("CustomLOG", "ArrayIndexOutOfBoundsException")
                }
                btnAddNewPerson.isEnabled = firstNameIsOK && secondNameIsOK
            }
        })
        etSecondName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var secondName = etSecondName.text.toString()
                try {
                    if ((secondName.toCharArray()[0].isUpperCase()) && (secondName.length > 1)) {   /// /*  && (secondNameLenght.length > 3*/   && secondName.toCharArray()[0].isUpperCase()
                        secondNameIsOK = true
                    } else {
                        secondNameIsOK = false
                        btnAddNewPerson.isEnabled = false
                    }
                } catch (e: ArrayIndexOutOfBoundsException) {
                    btnAddNewPerson.isEnabled = false
                    Log.d("CustomLOG", "ArrayIndexOutOfBoundsException")
                }
                btnAddNewPerson.isEnabled = firstNameIsOK && secondNameIsOK
            }
        })
    }

    fun makeTextSpanable() {
        var firtsName = etFirstName.text.toString()
        var secondName = etSecondName.text.toString()
        val fullName = "$firtsName  " + "$secondName"
        Log.d("fullName", fullName)
        val startIndex = fullName.length - secondName.length// e.g. only
        val endIndex = fullName.length // e.g. only
        val flag = 0  // 0: no flag
        val color: Int =
            Color.rgb(Random.nextInt(0, 256), Random.nextInt(0, 256), Random.nextInt(0, 256))
        val spansColor = ForegroundColorSpan(color)
        val spansUnderlineSpan = UnderlineSpan()
        val spannableFullName = SpannableString(fullName)
        spannableFullName.setSpan(spansColor, startIndex, endIndex, flag)
        spannableFullName.setSpan(spansUnderlineSpan, startIndex, endIndex, flag)
        val person = Person(spannableFullName)
        personList.add(person)
        adapter.notifyItemInserted(personList.size - 1)
    }
}


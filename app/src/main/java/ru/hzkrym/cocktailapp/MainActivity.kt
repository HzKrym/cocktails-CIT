package ru.hzkrym.cocktailapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.hzkrym.cocktailapp.list.presentation.ListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_container, ListFragment())
            .commit()
    }
}
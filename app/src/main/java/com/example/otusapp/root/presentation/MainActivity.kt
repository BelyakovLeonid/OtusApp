package com.example.otusapp.root.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.otusapp.R
import com.example.otusapp.recipes.presentation.RecipesListFragment

class MainActivity : AppCompatActivity(R.layout.a_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            openRecipesFragment()
        }
    }

    private fun openRecipesFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.vMainContainer, RecipesListFragment())
            .commit()
    }
}
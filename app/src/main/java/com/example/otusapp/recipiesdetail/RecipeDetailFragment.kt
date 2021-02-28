package com.example.otusapp.recipiesdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.otusapp.R

class RecipeDetailFragment : Fragment(R.layout.f_recipes_detail) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        const val ARG_RECIPE_ID = "arg_recipe_id"
    }
}
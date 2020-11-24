package com.jrk.mood4food.recipes.selection.controller

import com.jrk.mood4food.model.DataAccessLayer
import com.jrk.mood4food.recipes.selection.view.SelectionView

class SelectionController(private val model: DataAccessLayer) {
    private  lateinit var view: SelectionView
    fun bind(selectionView: SelectionView) {
        view = selectionView
    }
}
package com.jrk.mood4food.model.api

enum class Endpoint(val path: String) {

    RECIPES("/recipes"),
    INGREDIENTS("/ingredients"),
    MATERIALS("/materials")

}
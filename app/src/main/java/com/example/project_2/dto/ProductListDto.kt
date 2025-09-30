package com.example.project_2.dto

import java.io.Serializable

data class ProductListDto (
    val id:Int,
    val thumbnail:String,
    val title:String,
    val price:Double,
    var isLiked:Boolean=false
): Serializable
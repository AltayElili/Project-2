package com.example.project_2.mapper

import com.example.project_2.db.ProductEntity
import com.example.project_2.dto.ProductListDto
import com.example.project_2.model.Product

fun Product.toListDto():ProductListDto{
    return ProductListDto(
        id = this.id,
        thumbnail = this.thumbnail,
        title = this.title,
        price = this.price,
        isLiked = false
    )
}

fun ProductListDto.toEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        name = this.title,
        price = this.price.toString(),
        thumbnail = this.thumbnail
    )
}



package com.example.project_2.mapper

import com.example.project_2.db.ProductEntity
import com.example.project_2.dto.ProductListDto

fun ProductListDto.toEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        name = this.title,
        price = this.price.toString(),
        thumbnail = this.thumbnail
    )
}


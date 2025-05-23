package com.rest_api.spring.data.vo.v1

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.github.dozermapper.core.Mapping
import org.springframework.hateoas.RepresentationModel
import java.util.Date
import kotlin.coroutines.CoroutineContext

@JsonPropertyOrder("id", "author", "launch_date", "price", "title")
data class BookVO(
    @Mapping("id")
    @field:JsonProperty("id")
    var key: Long = 0,

    var author: String = "",

    @field:JsonProperty("launch_date")
    var launchDate: Date? = null,

    var price: Double = 0.0,
    var title: String = ""




): RepresentationModel<PersonVO>()
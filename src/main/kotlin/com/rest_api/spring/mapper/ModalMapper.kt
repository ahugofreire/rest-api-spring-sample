package com.rest_api.spring.mapper

import com.github.dozermapper.core.DozerBeanMapperBuilder
import com.github.dozermapper.core.Mapper
import org.modelmapper.ModelMapper

object ModalMapper {

    private val mapper: ModelMapper = ModelMapper()

    fun <O, D> parseObject(origin: O, destination: Class<D>?) :D {
        return mapper.map(origin, destination)
    }

    fun <O, D> parseListObject(origin: List<O>, destination: Class<D>?) :ArrayList<D> {
        val destinationObjects: ArrayList<D> = ArrayList()
        for (o in origin) {
            destinationObjects.add(mapper.map(o, destination))
        }
        return destinationObjects
    }


}
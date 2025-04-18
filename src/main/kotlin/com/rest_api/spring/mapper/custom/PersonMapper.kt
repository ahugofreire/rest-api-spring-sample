package com.rest_api.spring.mapper.custom

import com.rest_api.spring.data.vo.v2.PersonVO
import com.rest_api.spring.model.Person
import org.springframework.stereotype.Service
import java.util.*

@Service
class PersonMapper {

    fun mapEntityToVO(person: Person): PersonVO {
        val vo = PersonVO()
        vo.id = person.id
        vo.address = person.address
        vo.birthDay = Date()
        vo.firstName = person.firstName
        vo.lastName = person.lastName
        vo.gender = person.gender

        return vo
    }

    fun mapVOToEntity(person: PersonVO): Person {
        val entity = Person()
        entity.id = person.id
        entity.address = person.address
       // entity.birthDay = Date()
        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.gender = person.gender

        return entity
    }
}
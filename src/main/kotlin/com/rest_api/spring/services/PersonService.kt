package com.rest_api.spring.services

import com.rest_api.spring.controller.PersonController
import com.rest_api.spring.data.vo.v1.PersonVO
import com.rest_api.spring.exceptions.RequiredObjectIsNullException
import com.rest_api.spring.data.vo.v2.PersonVO as PersonV2
import com.rest_api.spring.exceptions.ResourceNotFoundException
import com.rest_api.spring.mapper.DozerMapper
import com.rest_api.spring.mapper.custom.PersonMapper
import com.rest_api.spring.model.Person
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.rest_api.spring.repository.PersonRepository
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo

import java.util.logging.Logger

@Service
class PersonService {
//    private  val counter : AtomicLong = AtomicLong()

    @Autowired
    private  lateinit var repository: PersonRepository

    @Autowired
    private lateinit var mapper: PersonMapper

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findById(id: Long): PersonVO {
        logger.info("Finding on Person with id $id")
        val entity =  repository.findById(id).orElseThrow { ResourceNotFoundException("No records found for this ID!") }
        val personVO = DozerMapper.parseObject(entity, PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)

        return personVO
    }

    fun findAll(): List<PersonVO> {
        logger.info("Finding all people!")
        val persons =  repository.findAll()
        val vos = DozerMapper.parseListObject(persons, PersonVO::class.java)
        for (person in vos) {
            val withSelfRel = linkTo(PersonController::class.java).slash(person.key).withSelfRel()
            person.add(withSelfRel)
        }

        return vos
//        val persons: MutableList<Person> = ArrayList()
//        for ( i in 0.. 7 ) {
//            val person = mockPerson(i)
//            persons.add(person)
//        }
    }

    fun create(person: PersonVO?): PersonVO {
        if (person == null) throw RequiredObjectIsNullException()
        logger.info("Creating one person with name ${person.firstName}!")
        val entity: Person  = DozerMapper.parseObject(person, Person::class.java)

        val personVO = DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)

        return personVO
    }

    fun createV2(person: PersonV2): PersonV2 {
        logger.info("Creating one person with name ${person.firstName}!")
        val entity: Person  = mapper.mapVOToEntity(person)

        return mapper.mapEntityToVO(repository.save(entity))
    }

    fun update(person: PersonVO): PersonVO {
        logger.info("Update one person with name ${person.firstName}!")
        val entity = repository.findById(person.key)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender

        val personVO = DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun delete(id: Long){
        logger.info("Deleting one person with id $id")
        val entity = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }
        repository.delete(entity)
    }
}
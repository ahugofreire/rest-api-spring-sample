package com.rest_api.spring.services

import com.rest_api.spring.data.vo.v1.PersonVO
import com.rest_api.spring.data.vo.v2.PersonVO as PersonV2
import com.rest_api.spring.exceptions.ResourceNotFoundException
import com.rest_api.spring.mapper.DozerMapper
import com.rest_api.spring.mapper.custom.PersonMapper
import com.rest_api.spring.model.Person
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.rest_api.spring.repository.PersonRepository
import java.util.concurrent.atomic.AtomicLong
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
        logger.info("Finding on Person")
        val entity =  repository.findById(id).orElseThrow { ResourceNotFoundException("No records found for this ID!") }
        return DozerMapper.parseObject(entity, PersonVO::class.java)
    }

    fun findAll(): List<PersonVO> {
        logger.info("Finding all people!")
        val persons =  repository.findAll()
        return DozerMapper.parseListObject(persons, PersonVO::class.java)
//        val persons: MutableList<Person> = ArrayList()
//        for ( i in 0.. 7 ) {
//            val person = mockPerson(i)
//            persons.add(person)
//        }
    }

    fun create(person: PersonVO): PersonVO {
        logger.info("Creating one person with name ${person.firstName}!")
        val entity: Person  = DozerMapper.parseObject(person, Person::class.java)

        return DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
    }

    fun createV2(person: PersonV2): PersonV2 {
        logger.info("Creating one person with name ${person.firstName}!")
        val entity: Person  = mapper.mapVOToEntity(person)

        return mapper.mapEntityToVO(repository.save(entity))
    }

    fun update(person: PersonVO): PersonVO {
        logger.info("Update one person with name ${person.firstName}!")
        val entity = repository.findById(person.id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender

        return DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)

    }

    fun delete(id: Long){
        logger.info("Deleting one person with id $id")
        val entity = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }
        repository.delete(entity)
    }
}
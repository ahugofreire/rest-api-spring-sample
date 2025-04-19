package com.rest_api.spring.controller

import com.rest_api.spring.data.vo.v1.PersonVO
import com.rest_api.spring.data.vo.v2.PersonVO as PersonV2
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import com.rest_api.spring.services.PersonService
import com.rest_api.spring.util.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping

@RestController
@RequestMapping("/person")
class PersonController {
    @Autowired
    private lateinit var service: PersonService

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun getPerson(@PathVariable(value = "id") id: Long): PersonVO {
        return service.findById(id)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun find():List<PersonVO> {
        return service.findAll()
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun create(@RequestBody person: PersonVO): PersonVO {
        return service.create(person)
    }

    @PostMapping(value = ["/v2"], consumes = [MediaType.APPLICATION_JSON,  MediaType.APPLICATION_XML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML])
    fun createV2(@RequestBody person: PersonV2): PersonV2 {
        return service.createV2(person)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML, MediaType.APPLICATION_YML ],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun update(@RequestBody person: PersonVO): PersonVO {
        return service.update(person)
    }

    @DeleteMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    fun delete(@PathVariable(value = "id") id: Long): ResponseEntity<*> {
        service.delete(id)
        return ResponseEntity.noContent().build<Any>()
    }
}
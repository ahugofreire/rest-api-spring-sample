package com.rest_api.spring.mockito.services

import com.rest_api.spring.exceptions.RequiredObjectIsNullException
import com.rest_api.spring.repository.PersonRepository
import com.rest_api.spring.services.PersonService
import com.rest_api.spring.unittests.mapper.mocks.MockPerson
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock

import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
 class PersonServiceTest {

  private lateinit var inputObject: MockPerson

  @InjectMocks
  private lateinit var service: PersonService

  @Mock
  private lateinit var repository: PersonRepository

@BeforeEach
 fun setUpMock() {
   inputObject = MockPerson()
   MockitoAnnotations.openMocks(this)
 }

@Test
 fun findById() {
   val parson = inputObject.mockEntity(1)
   parson.id = 1L
   `when`(repository.findById(1)).thenReturn(Optional.of(parson))

   val result = service.findById(1)

   assertNotNull(result)
   assertNotNull(result.key)
   assertNotNull(result.links)
   assertTrue(result.links.toString().contains("/person/1"))

   assertEquals("Address Test1",result.address)
   assertEquals("First Name Test1",result.firstName)
   assertEquals("Last Name Test1",result.lastName)
   assertEquals("Female",result.gender)
 }

@Test
  fun findAll() {
   val parsons = inputObject.mockEntityList()
   `when`(repository.findAll()).thenReturn(parsons)

   val result = service.findAll()

   assertNotNull(result)
   assertEquals(14, result.size)

   val personOne = result[1]
     assertNotNull(personOne)
     assertNotNull(personOne.key)
     assertNotNull(personOne.links)
     assertTrue(personOne.links.toString().contains("/person/1"))

     assertEquals("Address Test1",personOne.address)
     assertEquals("First Name Test1",personOne.firstName)
     assertEquals("Last Name Test1",personOne.lastName)
     assertEquals("Female",personOne.gender)

  }

@Test
   fun create() {
      val parson = inputObject.mockEntity(1)
      val persisted = parson.copy()
      persisted.id = 1


      `when`(repository.save(parson)).thenReturn(persisted)

      val vo = inputObject.mockVO(1)
      val result = service.create(vo)

      assertNotNull(result)
      assertNotNull(result.key)
      assertNotNull(result.links)
      assertTrue(result.links.toString().contains("/person/1"))

      assertEquals("Address Test1",result.address)
      assertEquals("First Name Test1",result.firstName)
      assertEquals("Last Name Test1",result.lastName)
   }

@Test
 fun update() {
     val parson = inputObject.mockEntity(1)
     val persisted = parson.copy()
     persisted.id = 1

     `when`(repository.findById(1)).thenReturn(Optional.of(parson))
     `when`(repository.save(parson)).thenReturn(persisted)

     val vo = inputObject.mockVO(1)
     val result = service.update(vo)

     assertNotNull(result)
     assertNotNull(result.key)
     assertNotNull(result.links)
     assertTrue(result.links.toString().contains("/person/1"))

     assertEquals("Address Test1",result.address)
     assertEquals("First Name Test1",result.firstName)
     assertEquals("Last Name Test1",result.lastName)

 }

@Test
 fun delete() {
    val parson = inputObject.mockEntity(1)
    `when`(repository.findById(1)).thenReturn(Optional.of(parson))

    service.delete(1)
 }

 @Test
 fun createWithNullPerson() {
  val exception: Exception = assertThrows(
   RequiredObjectIsNullException::class.java
  ) { service.create(null) }

  val expectedMessage = "It is not allowed to persist a null object!"
  val actualMessage = exception.message

  assertTrue(actualMessage!!.contains(expectedMessage))
 }
}


package com.rest_api.spring.unittests.mapper.mocks

import com.rest_api.spring.data.vo.v1.PersonVO
import com.rest_api.spring.mapper.DozerMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DozerConverterTest {

    var inputObject: MockPerson? = null

    @BeforeEach
    fun setUp() {
        inputObject = MockPerson()
    }

    @Test
    fun parseEntityToVOTest() {
        val output: PersonVO = DozerMapper.parseObject(inputObject!!.mockEntity(), PersonVO::class.java)
        assertEquals(0, output.key)
        assertEquals("First Name Test0", output.firstName)
        assertEquals("Last Name Test0", output.lastName)
        assertEquals("Address Test0", output.address)
        assertEquals("Male", output.gender)
    }

    @Test
    fun parseEntityToVOListTest() {
        val outputList: ArrayList<PersonVO> =
            DozerMapper.parseListObject(inputObject!!.mockEntityList(), PersonVO::class.java)

        val outputZero: PersonVO = outputList[0]
        assertEquals(0, outputZero.key)
        assertEquals("First Name Test0", outputZero.firstName)
        assertEquals("Last Name Test0", outputZero.lastName)
        assertEquals("Address Test0", outputZero.address)
        assertEquals("Male", outputZero.gender)

        val outputSeven: PersonVO = outputList[7]
        assertEquals(7.toLong(), outputSeven.key)
        assertEquals("First Name Test7", outputSeven.firstName)
        assertEquals("Last Name Test7", outputSeven.lastName)
        assertEquals("Address Test7", outputSeven.address)
        assertEquals("Female", outputSeven.gender)

    }
}
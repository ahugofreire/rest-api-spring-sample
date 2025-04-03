package services

import model.Person
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger

@Service
class PersonService {
    private  val counter : AtomicLong = AtomicLong()

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findById(id: Long): Person {
        logger.info("Finding on Person")

        val person = Person()
        person.id = counter.incrementAndGet()
        person.firstName = "Hugo"
        person.lastName = "Freire"
        person.address = "My address"
        person.gender = "male"

        return person
    }

    fun findAll(): List<Person> {
        logger.info("Finding all people!")

        val persons: MutableList<Person> = ArrayList()
        for ( i in 0.. 7 ) {
            val person = mockPerson(i)
            persons.add(person)
        }

        return persons
    }

    fun mockPerson(id: Int): Person {
        val person = Person()
        person.id = counter.incrementAndGet()
        person.firstName = "Person"
        person.lastName = "Name $id"
        person.address = "My address"
        person.gender = "male"

        return person
    }
}
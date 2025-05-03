package com.rest_api.spring.services

import com.rest_api.spring.controller.BookController
import com.rest_api.spring.data.vo.v1.BookVO
import com.rest_api.spring.exceptions.RequiredObjectIsNullException
import com.rest_api.spring.exceptions.ResourceNotFoundException
import com.rest_api.spring.mapper.DozerMapper
import com.rest_api.spring.model.Book
import com.rest_api.spring.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo

@Service
class BookService {

    @Autowired
    private lateinit var repository: BookRepository

    private val logger = Logger.getLogger(BookService::class.java.name)

    fun findAll(): List<BookVO> {
        val books = repository.findAll()
        val vos = DozerMapper.parseListObject(books, BookVO::class.java)
        for (book in vos) {
            val withSelfRel = linkTo(BookController::class.java).slash(book.key).withSelfRel()
            book.add(withSelfRel)
        }

        return vos
    }

    fun findById(id: Long): BookVO {
        logger.info("Finding one book with id: $id!")
        var book = repository.findById(id).orElseThrow{ ResourceNotFoundException("No records found for this id: $id!")}
        val bookVo: BookVO = DozerMapper.parseObject(book, BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVo.key).withSelfRel()
        bookVo.add(withSelfRel)
        return bookVo

    }

    fun create(book: BookVO?): BookVO {
        if (book == null) throw RequiredObjectIsNullException()

        val entity: Book = DozerMapper.parseObject(book, Book::class.java)
        val bookVO: BookVO = DozerMapper.parseObject(repository.save(entity),BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)

        return bookVO
    }

    fun update(book: BookVO?): BookVO {
        if (book == null) throw RequiredObjectIsNullException()
        val id: Long = book.key

        val entity = repository.findById(id).orElseThrow{ ResourceNotFoundException("No records found for this id: $id!")}

        entity.author = book.author
        entity.title = book.title
        entity.price = book.price
        entity.launchDate = book.launchDate

        val bookVO: BookVO = DozerMapper.parseObject(repository.save(entity), BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)

        return bookVO
    }

}
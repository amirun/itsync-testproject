package com.itsync.testproject.service;

import com.itsync.testproject.dto.BooksDTO;
import com.itsync.testproject.dto.FilterDTO;
import com.itsync.testproject.model.Book;
import com.itsync.testproject.model.TypeEnum;
import com.itsync.testproject.repo.BooksRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BooksServiceImpl implements BooksService{

    private final BooksRepository repository;
    private final EntityManager entityManager;

    private Logger logger = LoggerFactory.getLogger(BooksServiceImpl.class);

    @Override
    public Book createBook(BooksDTO dto) {
        logger.info("creating book: {}", dto);

        Book book = dtoMapper(dto);

        Book createdBook = repository.save(book);
        logger.info("new book created with id:{}", createdBook.getId());
        return createdBook;
    }

    @Override
    public Book updateBook(Integer bookId, BooksDTO dto) throws Exception {

        Book book = repository.findById(bookId)
                .orElseThrow(() ->new Exception("Unable to find book for provided id"));
        logger.info("updating book with id: {}", bookId);

        book.setName(dto.name());
        book.setAuthor(dto.author());
        book.setGenre(dto.genre());
        book.setVolumeCount(dto.volumeCount());
        book.setType(dto.type());
        Book updatedBook = repository.save(book);

        logger.info("book updated:{}", updatedBook.getId());

        return updatedBook;
    }

    @Override
    public boolean delete(Integer bookId) {
        try {
            repository.deleteById(bookId);
        } catch (Exception e) {
            logger.info("delete failefor book with id: {}", bookId);
            return false;
        }
        logger.info("deleted book with id: {}", bookId);
        return true;
    }

    @Override
    public List<Book> getBooks(FilterDTO dto) {
        logger.info("filtering books based on filters: {}", dto);
        List<Book> result = getFilteredList(dto);
        //List<Book> result = Arrays.asList(repository.findById(dto.getMinVolume()).orElseThrow(Exception::new));

        if(result.isEmpty()) {
            logger.error("No Books found matching your filters");
            return null;
        }

        logger.info("books count based on filters {} is {}", dto, result.size());
        return result;
    }

    List<Book> getFilteredList(FilterDTO filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);

        List<Predicate> predicateList = new ArrayList<>();
        if(filter.getMinVolume() != null && filter.getMinVolume() >= 0) {
            predicateList.add(criteriaBuilder.greaterThan(root.get("volumeCount"), filter.getMinVolume()));
        }
        if(filter.getAuthor() != null) {
            predicateList.add(criteriaBuilder.equal(root.get("author"), filter.getAuthor()));
        }
        if(filter.getGenre() != null) {
            predicateList.add(criteriaBuilder.equal(root.get("genre"), filter.getGenre()));
        }
        if(filter.getType() != null) {
            predicateList.add(criteriaBuilder.equal(root.get("type"), TypeEnum.valueOf(filter.getType())));
        }
        query.select(root).where(predicateList.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }

    private Book dtoMapper(BooksDTO dto) {
        return Book.builder()
                .name(dto.name())
                .author(dto.author())
                .genre(dto.genre())
                .volumeCount(dto.volumeCount())
                .createDate(Timestamp.valueOf(LocalDateTime.now()))
                .type(dto.type())
                .build();
    }
}

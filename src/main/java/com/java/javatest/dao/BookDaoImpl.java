package com.java.javatest.dao;



import com.java.javatest.model.Book;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by grh on 10/11/17.
 */

@Repository
public class BookDaoImpl implements BookDao {

    private static final Logger logger = LoggerFactory.getLogger(BookDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addBook(Book book) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(book);
        logger.info("Book saved. Book details: " + book);

    }

    @Override
    public void updateBook(Book book) {

        Session session = this.sessionFactory.getCurrentSession();
        session.update(book);
        logger.info("Book update. Book details: " + book);

    }

    @Override
    public void removeBook(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Book book = (Book) session.load(Book.class, new Integer(id));

        if (book != null) {
            session.delete(book);
        }
        logger.info("Book removed. Book details: " + book);
    }

    @Override
    public Book getBookById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Book book = (Book) session.load(Book.class, new Integer(id));
        logger.info("Book loaded. Book details: " + book);

        return book;
    }

    @Override
        @SuppressWarnings("unchecked")
    public List<Book> listBooks() {

        Session session = this.sessionFactory.getCurrentSession();

        List<Book> bookList = session.createQuery("from Book").list();

        for (Book book : bookList) {
            logger.info("Book list: " + book);
        }

        return bookList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Book getBookByTitle(String title) {
        Session session = this.sessionFactory.getCurrentSession();

        List<Book> bookList = session.createQuery("from Book").list();

        Book bookT = null;
      //  List<Book> listBooksTitle = new ArrayList<Book>();

        for (Book book : bookList) {
            if (book.getAuthor().equals(title)) ;
            bookT = book;
            logger.info("Book list add by title: " + book);
        }

        return bookT;
    }

}
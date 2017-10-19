package com.java.javatest.service;

import com.java.javatest.model.Book;

import java.util.List;

/**
 * Created by grh on 10/11/17.
 */
public interface BookService {

    public void addBook(Book book); //добавление книги

    public void updateBook(Book book); //редактирование - заменять книгу на новый выпуск книги

    public void removeBook(int id); //убирать (удалить) книгу

    public Book getBookById(int id); // поиска по уже существующим

    public List<Book> listBooks(); // вывод всех книг в нашейй таблице

    public Book getBookByTitle(String title); // поиск книги по уже существующим по названию

}
package com.java.javatest.controller;

import com.java.javatest.model.Book;
import com.java.javatest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BookController {

    private BookService bookService;




    @Autowired(required = true)
    @Qualifier(value = "bookService")
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }


    @RequestMapping(value="/books", method = RequestMethod.GET)
    public ModelAndView listOfBooks(@RequestParam(required = false) Integer page) {

        ModelAndView modelAndView = new ModelAndView("/books");// пейджинг

        modelAndView.addObject("book", new Book()); // пейджинг

        List<Book> books = bookService.listBooks();


        // пейджинг
        PagedListHolder<Book> pagedListHolder = new PagedListHolder<Book>(books);

        pagedListHolder.setPageSize(10);

        modelAndView.addObject("maxPages", pagedListHolder.getPageCount());

        if(page == null || page < 1 || page > pagedListHolder.getPageCount())

            page = 1;

        modelAndView.addObject("page", page);

        if(page > pagedListHolder.getPageCount()){

            pagedListHolder.setPage(page);
            modelAndView.addObject("listBooks", pagedListHolder.getPageList());
        }

        else if(page <= pagedListHolder.getPageCount()) {

            pagedListHolder.setPage(page-1);
            modelAndView.addObject("listBooks", pagedListHolder.getPageList());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/books/add", method = RequestMethod.POST)
    public String addBook(@ModelAttribute("book") Book book){


        if (book.getId() == 0) this.bookService.addBook(book);

        else this.bookService.updateBook(book);

        return "redirect:/books";
    }

      @RequestMapping("/remove/{id}")
    public String removeBook(@PathVariable("id") int id){
        this.bookService.removeBook(id);

        return "redirect:/books";
    }

    @RequestMapping("/edit/{id}")
    public String editBook(@PathVariable("id") int id, Model model){
        model.addAttribute("book", this.bookService.getBookById(id));
        model.addAttribute("listBooks", this.bookService.listBooks());

        return "bookedit";
    }

    @RequestMapping("/bookdate/{id}")
    public String bookData(@PathVariable("id") int id, Model model){
        model.addAttribute("book", this.bookService.getBookById(id));

        return "bookdate";
    }




    // черт ногу сломит


    @RequestMapping("/booksByID/")
    public String search(@RequestParam("id") int id, Model model) {
        model.addAttribute("book", this.bookService.getBookById(id));
        return "booksByID";
    }

/*

    @RequestMapping(value = "/booksByTitle/")
    public String searchTitle(@RequestParam("title") String title, Model model){
      //  model.addAttribute("book", new Book());
        model.addAttribute("listBooksTitle", this.bookService.getBookByTitle(title));
        return "booksByTitle";

    }
*/
}
package ru.epozin.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.epozin.otus.library.domain.Author;
import ru.epozin.otus.library.domain.Book;
import ru.epozin.otus.library.domain.Genre;
import ru.epozin.otus.library.service.BookService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {
    private final BookService bookService;

    @ShellMethod("get book from library")
    public String get(Long id) {
        Book book = bookService.getBookById(id);
        return book.toString();

    }

    @ShellMethod("update book")
    public String update(Book book) {
        bookService.updateBook(book);
        return book.getTitle() + " was updated";
    }

    @ShellMethod("get all books from library")
    public String getall() {
        List<Book> bookList = bookService.getAllBooks();
        return bookList.toString();
    }

    @ShellMethod("delete book from library")
    public String delete(Long id) {
        String title = bookService.getBookById(id).getTitle();
        bookService.deleteBook(id);
        return title + " was deleted";
    }

}

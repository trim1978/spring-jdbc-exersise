package ru.otus.trim.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.trim.dao.LibraryDao;
import ru.otus.trim.domain.Author;
import ru.otus.trim.domain.Book;
import ru.otus.trim.domain.Genre;

import java.util.List;

@ShellComponent
@Profile("shell")
@RequiredArgsConstructor
public class QuizCommandComponent {

    private final LibraryDao library;

//    @ShellMethod(value = "Start quiz", key = {"start", "run",})
//    @ShellMethodAvailability(value = "isQuizStartAvailable")
//    public String quizRun() {
//        QuizAction quizAction = eventsPublisher.runQuiz();
//        boolean complete = quizAction.isComplete() && quizAction.getRightAnsweredQuestionsCount() >= quizConfig.getEnough();
//        return complete ? "done" : "fault";
//    }

//    @ShellMethod(value = "Enter firstname and lastname of student", key = "enter")
//    public String studentRequest() {
//        student = studentRequest.requestStudent();
//        return "Student is ready";
//    }

//    @ShellMethod(value = "Add author", key = "add_author")
//    public String addAuthor(int id, String name) {
//        library.insertAuthor(new Author(id, name));
//        return "author was added";
//    }

    @ShellMethod(value = "Add author", key = "ins_author")
    public Author addAuthor(String name) {
        return library.insertAuthor(name);
    }

    @ShellMethod(value = "Add book", key = "ins_book")
    public Book addAuthor(String title, String author, String genre) {
        return library.insertBook(title, author, genre);
    }

    @ShellMethod(value = "Remove book", key = "remove_book")
    public String removeBook(long id) {
        boolean removed = library.deleteBookById(id);
        return "book was removed " + removed;
    }

    @ShellMethod(value = "Remove author", key = "remove_author")
    public String removeAuthor(int id) {
        boolean removed = library.deleteAuthorById(id);
        return "author was removed " + removed;
    }

    @ShellMethod(value = "Get all authors", key = "get_authors")
    public List<Author> getAuthors() {
        return library.getAllAuthors();
    }

    @ShellMethod(value = "Get all genres", key = "get_genres")
    public List<Genre> getGenres() {
        return library.getAllAGenres();
    }

    @ShellMethod(value = "Get all books", key = "get_books")
    public List<Book> getBooks() {
        return library.getAllBooks();
    }

}

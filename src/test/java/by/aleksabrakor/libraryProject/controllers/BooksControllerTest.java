package by.aleksabrakor.libraryProject.controllers;

import by.aleksabrakor.libraryProject.models.Book;
import by.aleksabrakor.libraryProject.models.Person;
import by.aleksabrakor.libraryProject.services.BooksService;
import by.aleksabrakor.libraryProject.services.PeopleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class BooksControllerTest {

    @Mock
    private PeopleService peopleService;

    @Mock
    private BooksService booksService;

    @Mock
    private Model model;
    @InjectMocks
    private BooksController booksController;


    @Test
    public void testFindById() {

        Person person = new Person(1,"testFio1", 1984,"test1@mail.ru","+375447796977");
        Person person2 = new Person(2,"testFio2", 1984, "test2@mail.ru","+375447796977");
        List<Person> people = new ArrayList<>(List.of(person, person2));

        int id = 1;
        Book book = new Book(id,"книга",1954);
        book.setOwner(person);
        person.setBooks(List.of(book));

        when(peopleService.findAll()).thenReturn(people);
        when(booksService.findById(id)).thenReturn(book);
        when(peopleService.findPersonByBooksId(id)).thenReturn(person);
        when(model.addAttribute("people", people)).thenReturn(model);
        when((model).addAttribute("book", book)).thenReturn(model);
        when((model).addAttribute("person", person)).thenReturn(model);

        String viewName = booksController.findById(id, model, new Person());


        verify(model).addAttribute("people", people);
        verify(model).addAttribute("book", book);
        verify(model).addAttribute("person", person);
        assertEquals("books/show", viewName);
    }



    @Test
    void newForCreate() {
    }

    @Test
    void create() {
    }

    @Test
    void edit() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void assignBook() {
    }

    @Test
    void releaseBook() {
    }

    @Test
    void searchPage() {
    }

    @Test
    void makSearch() {
    }
}
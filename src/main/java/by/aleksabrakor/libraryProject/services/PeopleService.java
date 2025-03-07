package by.aleksabrakor.libraryProject.services;

import by.aleksabrakor.libraryProject.models.Book;
import by.aleksabrakor.libraryProject.models.Person;
import by.aleksabrakor.libraryProject.repositories.PeopleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private static final int DAYS_TO_RETURN_BOOK = 30;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findById(int personId) {
        return peopleRepository.findById(personId).orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(Person personUpdate, int id) {
        personUpdate.setId(id);
        peopleRepository.save(personUpdate);

    }

    @Transactional
    public void deleteById(int id) {
        peopleRepository.deleteById(id);
    }

    //Для валидации уникальности email
    public Optional<Person> findByEmail(String email, int id) {
        return peopleRepository.findByEmailAndIdNot(email, id).stream().findAny();

    }

    public Person findPersonByBooksId(int id) {
        return peopleRepository.findPersonByBooksId(id);
    }

    public List<Book> findBookByOwner(int id) {
        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent()) {
            List<Book> booksByOwner = person.get().getBooks();
            checkForExpired(booksByOwner);
            return booksByOwner;
        }
        return Collections.emptyList();
    }

    private void checkForExpired(List<Book> booksByOwner) {
        booksByOwner.stream()
                .filter(book -> book.getTakenAt().plusDays(DAYS_TO_RETURN_BOOK).isBefore(LocalDateTime.now()))
                .forEach(book -> {
                    book.setExpired(true);
                    log.info("Книга '{}' помечена как просроченная.", book.getTitle());
                });
    }
}

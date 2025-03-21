package by.aleksabrakor.libraryProject.services;

import by.aleksabrakor.libraryProject.models.Book;
import by.aleksabrakor.libraryProject.models.Person;
import by.aleksabrakor.libraryProject.repositories.BooksRepository;
import by.aleksabrakor.libraryProject.specification.BooksSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public Page<Book> findWithPagination(int page, int itemsPerPage, boolean sortByTitle, boolean sortByAuthor, boolean sortByYear) {
        // Создаем спецификацию с сортировкой
        Specification<Book> spec = BooksSpecification.sortByParameters(sortByTitle, sortByAuthor, sortByYear);

        // Выполняем запрос с пагинацией, учитываем, что начало с 0 индекса
        return booksRepository.findAll(spec, PageRequest.of(page - 1, itemsPerPage));
    }


    public Book findById(int bookId) {
        return booksRepository.findById(bookId).orElse(null);
    }

    public List<Book> findByTitleStartingWith(String titleStartWith) {
        return booksRepository.findByTitleStartingWithIgnoreCase(titleStartWith);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(Book bookUpdate, int id) {
        Book bookToBeUpdate = booksRepository.findById(id).get();
        bookUpdate.setId(id);
        bookUpdate.setOwner(bookToBeUpdate.getOwner()); // чтобы не терялась связь при обновлении
        bookUpdate.setTakenAt(bookToBeUpdate.getTakenAt()); // чтобы не терялась связь при обновлении
        booksRepository.save(bookUpdate);
    }

    @Transactional
    public void deleteById(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void assignBook(Person person, int bookId) {
        booksRepository.findById(bookId).ifPresent(book -> {
            book.setOwner(person);
            book.setTakenAt(LocalDateTime.now());
        });
    }

    @Transactional
    public void releaseBook(int bookId) {
        booksRepository.findById(bookId).ifPresent(book -> {
            book.setOwner(null);
            book.setTakenAt(null);
        });
    }
}

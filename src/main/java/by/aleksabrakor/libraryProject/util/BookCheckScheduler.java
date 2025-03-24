package by.aleksabrakor.libraryProject.util;

import by.aleksabrakor.libraryProject.models.Book;
import by.aleksabrakor.libraryProject.models.Person;
import by.aleksabrakor.libraryProject.services.EmailNotificationService;
import by.aleksabrakor.libraryProject.services.PeopleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
public class BookCheckScheduler {
    private final PeopleService peopleService;
    private final EmailNotificationService emailNotificationService;

    @Autowired
    public BookCheckScheduler(PeopleService peopleService, EmailNotificationService emailNotificationService) {
        this.peopleService = peopleService;
        this.emailNotificationService = emailNotificationService;
    }

    //каждую минуту будет срабатывать, использовать для проверки работы (cron = "0 * * * * ?")

    // Метод для ежедневной проверки просроченных книг
    @Transactional
//    @Scheduled(cron = "0 0 9 * * ?")  // Запуск задачи каждый день в 09:00
    @Scheduled(cron = "0 0 9 * * MON")   //М. будет срабатывать каждый понедельник в 9.00
    public void checkForExpiredBooks() {
        log.info("Запуск проверки просроченных книг...");
        List<Person> people = peopleService.findAll();
        for (Person person : people) {

            List<Book> expiredBooks = peopleService.findBookByOwner(person.getId()).stream().filter(Book::isExpired).toList();

            if (!expiredBooks.isEmpty()) {
                emailNotificationService.sendExpirationNotification(person, expiredBooks);
            }
        }
        log.info("Проверка завершена.");
    }
}

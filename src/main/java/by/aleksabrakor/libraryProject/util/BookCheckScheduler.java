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


    // Запуск задачи каждый день в 00:00
    // Метод для ежедневной проверки просроченных книг
    @Transactional
    //каждую минуту будет срабатывать
    @Scheduled(cron = "0 * * * * ?")
    public void checkForExpiredBooks() {
        log.info("Запуск проверки просроченных книг...");
        List<Person> people = peopleService.findAll();
        for (Person person : people) {
            //Собрать список с просроченными книгами
            //достаем все книги пользователя, в этом методе они уже помечаются тру если просрочены и добавляются в список
            List<Book> expiredBooks = peopleService.findBookByOwner(person.getId()).stream().filter(Book::isExpired).toList();

            if (!expiredBooks.isEmpty()) {
                emailNotificationService.sendExpirationNotification(person, expiredBooks);
            }
        }
        log.info("Проверка завершена.");
    }
}

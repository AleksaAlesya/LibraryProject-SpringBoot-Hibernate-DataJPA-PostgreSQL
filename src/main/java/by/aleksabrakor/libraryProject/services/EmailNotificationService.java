package by.aleksabrakor.libraryProject.services;

import by.aleksabrakor.libraryProject.models.Book;
import by.aleksabrakor.libraryProject.models.Person;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class EmailNotificationService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailNotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // Метод для отправки уведомления о просроченных книгах
    public void sendExpirationNotification(Person person, List<Book> expiredBooks) {
        String to = person.getEmail();
        String subject = "Уведомление о просроченных книгах";
        String text = createEmailMessage(person.getFio(), expiredBooks);
        sendEmail(to, subject, text);
    }

    //М. отправки уведомлений
    private void sendEmail(String to, String subject, String text) {
        log.info("Отправка письма на: {}", to);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        try {
            javaMailSender.send(message);
            log.info("Письмо успешно отправлено.");
        } catch (MailAuthenticationException e) {
            log.error("Ошибка аутентификации: {}", e.getMessage());
        } catch (MailException e) {
            log.error("Ошибка отправки письма: {}", e.getMessage());
        }
    }


    // Метод для формирования текста письма
    private String createEmailMessage(String userName, List<Book> expiredBooks) {
        StringBuilder text = new StringBuilder();
        text.append("Уважаемый(ая) ").append(userName).append(",\n\n");
        text.append("У вас есть просроченные книги:\n");

        for (Book book : expiredBooks) {
            text.append("- ").append(book.getTitle()).append(" (взята: ")
                    .append(book.getTakenAt().toLocalDate()).append(")\n");
        }

        text.append("\nПожалуйста, верните их в библиотеку как можно скорее.\n");
        return text.toString();
    }

  /*  Уважаемый(ая) Иван Иванов,
    У вас есть просроченные книги:
    - Война и мир (взята: 2023-10-01)
    - Преступление и наказание (взята: 2023-10-05)

    Пожалуйста, верните их в библиотеку как можно скорее.*/
}

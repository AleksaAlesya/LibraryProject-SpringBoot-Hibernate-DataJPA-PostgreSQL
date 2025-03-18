# LibraryProject -SpringBoot - Hibernate-DataJPA- PostgreSQL
Проект LibraryProject -SpringMVC-Hibernate-DataJPA- PostgreSQL переписан на  SpringBoot
Изменения: application.properties, удалена папка config с конфигурационными классами MySpringMVCDispatcherInitializer и SpringConfig

PostgreSQL:
name - postgres,
password - postgres,
порт 5432,
jdbc:postgresql://localhost:5432/library_db

**Используемые технолоигии, фреймворки, библиотеки:**
сборка Maven Java 17, Spring Boot, lombok, AOP, планировщик Scheduled , Liquibase, Docker
подключены стартеры:
Spring Web, Spring Data JPA, Postgresql Driver , Thymeleaf, Validation, mail

**Главная страница**
**--http://localhost:8080**

Вывести список
--http://localhost:8080/people
--http://localhost:8080/books

Показать информацию по id
--http://localhost:8080/people/2
--http://localhost:8080/books/2

Создание нового
--http://localhost:8080/people/new
--http://localhost:8080/books/new

Редактирование
--http://localhost:8080/people/2/edit
--http://localhost:8080/books/2/edit

Поиск книги по названию
--http://localhost:8080/books/search

Поиск человека по ФИО
-- http://localhost:8080/people/search

### Проект Библиотека- веб-приложение
**Описание**
предназначенное для местной библиотеки, для перехода  на цифровой учет книг. 
Библиотекарь может иметь возможность регистрировать читателей, выдавать им книги и освобождать книги (после того, как читатель возвращает книгу обратно в библиотеку). Реализована -пагинация, сортировка, поиск по параметрам. В приложении можно видеть, у кого находится книга и подсвечивать красным, если срок возврата просрочен. Настроено авоматическая отправка уведомления на почту, с сообщением о просреченном возврате. В проекте используется [Liquibase](https://www.liquibase.org/) для управления изменениями в базе данных. Liquibase позволяет автоматически применять миграции и поддерживать схему базы данных в актуальном состоянии. Настроено разворачивание в докере БД и приложения.



**Структура файлов Liquibase**
Файлы изменений Liquibase находятся в папке `src/main/resources/db/changelog`.
- `db.changelog-master.xml` — главный файл, который ссылается на все остальные файлы изменений.
- `changes/` — папка с отдельными файлами изменений. Каждый файл соответствует определенной миграции.

Добавление новой миграции**

1. Создать новый файл в папке `changes/` с именем, соответствующим версии миграции (например, `03-add-new-column.xml`).
2. Добавить изменения в файл, используя синтаксис Liquibase.
3. Указать новый файл в `db.changelog-master.xml`:


**Сущности:**
Человек (поля: ФИО (UNIQUE), год рождения)
Книга (поля: название, автор, год)
Отношение между сущностями: Один ко Многим.
У человека может быть множество книг. Книга может принадлежать
только одному человеку.
В БД должно быть две таблицы - Person и Book. Для всех таблиц
настройте автоматическую генерацию id.
Для этого проекта создайте новую БД с названием project1.
-------------------------------------------------------------------------------------

**Функционал:**
1) Страницы добавления, изменения и удаления человека.
2) Страницы добавления, изменения и удаления книги
3) Страница со списком всех людей (люди кликабельные - при клике осуществляется
переход на страницу человека).
4) Страница со списком всех книг (книги кликабельные - при клике осуществляется
переход на страницу книги).
5) Страница человека, на которой показаны значения его полей и список книг, которые он
взял. Если человек не взял ни одной книги, вместо списка должен быть текст "Человек
пока не взял ни одной книги".
6) Страница книги, на которой показаны значения полей этой книги и имя человека,
который взял эту книгу. Если эта книга не была никем взята, должен быть текст "Эта
книга свободна".
7) На странице книги, если книга взята человеком, рядом с его именем должна быть кнопка
"Освободить книгу". Эта кнопка нажимается библиотекарем тогда, когда читатель
возвращает эту книгу обратно в библиотеку. После нажатия на эту кнопку книга снова
становится свободно и пропадает из списка книг человека.
8) На странице книги, если книга свободна, есть выпадающий список (<select>)
со всеми людьми и кнопка "Назначить книгу". Эта кнопка нажимается библиотекарем
тогда, когда читатель хочет забрать эту книгу домой. После нажатия на эту кнопку, книга
должна начать принадлежать выбранному человеку и должна появится в его списке
книг.
9) Все поля  валидируются - с помощью @Valid и Spring Validator, если это
требуется.
10) Пагинация для книг.
    Книг может быть много и они могут не помещаться на одной странице в браузере. 
    Чтобы решить эту проблему, метод контроллера умеет  выдавать не только все книги разом, но и разбивать выдачу на страницы.  
11) Сортировка книг по году 
    Метод контроллера умеет выдавать книги в отсортированном порядке.
12) Есть страница поиска книг, на нее попадаем, когда в строку "Найти книгу по названию" , на странице со списком книг
    Вводим в поле на странице начальные буквы (настроено игнорирование регистра) названия книги, получаем полное название книги и имя автора. 
   Также, если  книга сейчас находится у кого-то, получаем имя этого человека. Если такой книги не было
    найдено, то должно выдаваться сообщение о том, что "Книг не
    найдено".
13) Пагинация для людей.
        Метод контроллера умеет  выдавать не только всех людей, но и разбивать выдачу на страницы.
14) Сортировка людей по ФИО
    Метод контроллера умеет выдавать людей в отсортированном порядке.
15) Есть страница поиска людей, на нее попадаем, когда в строку "Найти человека по ФИО" , на странице со списком людей
    Вводим в поле на странице начальные буквы (настроено игнорирование регистра) ФИО, получаем список людей ФИО, которых начинается с введенного названия.
16) Работает функционал - автоматическая проверка на то, что человек просрочил возврат
книги. Подсвечиваем красным на странице, если просрочена


**Дополнительный функционал**, реализованный на бэк :

1. Пагинация для книг 
Метод index() в BooksController должен уметь принимать в адресной строке два ключа: page и books_per_page.
Первый ключ сообщает, какую страницу мы запрашиваем, а второй ключ сообщает, сколько книг должно быть на одной странице.
Нумерация страниц стартует с 0. Если в адресной строке не передаются эти ключи, то возвращаются как обычно все книги.
--Например (обычный запрос на http://localhost:8080/books):
выведет все книги
--Делаем запрос http://localhost:8080/books?page=1&books_per_page=3
Получаем:На каждую страницу приходится по 3 книги, мы просим вторую страницу

2. Сортировка для книг 
Метод index() в BooksController умеет принимать в адресной строке ключ sort_by_year.
Если он имеет значение true, то выдача должна быть отсортирована по году.
Если в адресной строке не передается этот ключ, то книги возвращаются в
обычном порядке.
Например:
---(обычный запрос на http://localhost:8080/books):
Получаем: неотсортированный список

---Делаем запрос http://localhost:8080/books?sort_by_year=true
Получаем: (Книги отсортированы по году)

//возможные варианты запроса:
//http://localhost:8080/books
//http://localhost:8080/books?sort_by_year=true
//http://localhost:8080/books?page=0&books_per_page=3
//http://localhost:8080/books?page=1&books_per_page=3&sort_by_year=true

 
3. Пагинация для людей
   Метод index() в PeopleController умеет принимать в адресной строке два ключа: page и people_per_page.
   Первый ключ сообщает, какую страницу мы запрашиваем, а второй ключ сообщает, сколько человек должно быть на одной странице.
   Нумерация страниц стартует с 1. Если в адресной строке не передаются эти ключи, то возвращаются как обычно все люди.
   --Например (обычный запрос на http://localhost:8080/people):
   выведет все книги
   --Делаем запрос http://localhost:8080/books?page=2&books_per_page=3
   Получаем:На каждую страницу приходится по 3 человека, мы просим вторую страницу

4. Сортировка для  людей (по ФИО)
   Метод index() в PeopleController умеет принимать в адресной строке ключ sort_by_fio.
   Если он имеет значение true, то выдача должна быть отсортирована по Фио.
   Если в адресной строке не передается этот ключ, то книги возвращаются в
   обычном порядке.
   Например:(обычный запрос на http://localhost:8080/people):
   Получаем: неотсортированный список
   Делаем запрос http://localhost:8080/books?sort_by_fio=true
   Получаем: (Людей отсортированы по ФИО)

 //возможные варианты запроса:

  //http://localhost:8080/people
  http://localhost:8080/people?sort_by_fio=true
  http://localhost:8080/people?page=1&people_per_page=2
  http://localhost:8080/people?sort_by_fio=true&page=1&people_per_page=2

5. Добавлен функционал запланированной  проверки и отправки уведомлений на почту пользователю, со списком просроченных книг. (в почте гугл библиотекаря  обязательно включить двухфакторную защиту, тогда можно б. получить дополнительный пароль для приложения)
 Сообщение  будет приходить в таком виде

 Уважаемый(ая) Иван Иванов,
     У вас есть не возвращенные в 30 дневный срок книги:
     - Война и мир (взята: 2023-10-01)
     - Преступление и наказание (взята: 2023-10-05)

Пожалуйста, верните их в библиотеку как можно скорее.

6. В проекте интегрирован механизм Liquibase для управления изменениями в базе данных. Это позволяет:
Автоматически применять изменения при запуске приложения.
Легко добавлять новые миграции.
Поддерживать базу данных в актуальном состоянии.



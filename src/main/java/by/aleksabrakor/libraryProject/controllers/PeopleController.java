package by.aleksabrakor.libraryProject.controllers;


import by.aleksabrakor.libraryProject.models.Person;
import by.aleksabrakor.libraryProject.services.PeopleService;
import by.aleksabrakor.libraryProject.util.PersonValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController implements ControllerI<Person> {
    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService,
                            PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

//    @GetMapping
//    public String index(Model model,
//                        @RequestParam(value = "sort_by_fio", required = false) boolean sortByFio,
//                        @RequestParam(value = "sort_by_yearOfBirth", required = false) boolean sortByYearOfBirth,
//                        @RequestParam(value = "page", required = false) Integer page,
//                        @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage) {
//
//        if (page == null || peoplePerPage == null) {
//            model.addAttribute("people", peopleService.findAll(sortByFio, sortByYearOfBirth));
//        } else {
//            if (page < 1) {
//                page = 1;
//                //что бы не могли указать отрицательное значение
//            }
//            int zeroBasedPage = page - 1;
//            model.addAttribute("people", peopleService.findWithPagination(zeroBasedPage, peoplePerPage, sortByFio, sortByYearOfBirth));
//        }
//        return "people/index";
//    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "sort_by_fio", required = false) boolean sortByFio,
                        @RequestParam(value = "sort_by_yearOfBirth", required = false) boolean sortByYearOfBirth,
                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                        @RequestParam(value = "people_per_page", required = false, defaultValue = "30") int peoplePerPage) {

        // Защита от некорректных значений
        if (page < 1) {
            page = 1;
        }
        if (peoplePerPage < 1) {
            peoplePerPage = 10;
        }

        // Преобразуем страницу в нулевой индекс
        int zeroBasedPage = page - 1;

        // Получаем данные с пагинацией
        Page<Person> peoplePage = peopleService.findWithPagination(zeroBasedPage, peoplePerPage, sortByFio, sortByYearOfBirth);

        // Вычисляем диапазон страниц для пагинации
        int totalPages = peoplePage.getTotalPages();
        int currentPage = page;
        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(currentPage + 2, totalPages);
        StringBuilder baseUrl =formingBaseUrl(sortByFio,sortByYearOfBirth,peoplePerPage);

        // Передаем данные в модель
        model.addAttribute("people", peoplePage.getContent()); // Список людей на текущей странице
        model.addAttribute("currentPage", currentPage); // Текущая страница (начинается с 1)
        model.addAttribute("totalPages", totalPages); // Общее количество страниц
        model.addAttribute("startPage", startPage); // Начальная страница для отображения
        model.addAttribute("endPage", endPage); // Конечная страница для отображения
        model.addAttribute("baseUrl",baseUrl); // Базовый URL с параметрами сортировки

        return "people/index";
    }

    @GetMapping("/{id}")
    public String findById(@NotNull @PathVariable("id") int id,
                           Model model) {

        model.addAttribute("person", peopleService.findById(id));
        model.addAttribute("books", peopleService.findBookByOwner(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newForCreate(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        //валидация на уникальность
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        // в модель уже запишется созданный person c полями
        peopleService.save(person);
        return ("redirect:/people");
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model,
                       @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findById(id));
        return ("people/edit");
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        //валидация на уникальность
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/edit";
        peopleService.update(person, id);
        return ("redirect:/people");
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.deleteById(id);
        return ("redirect:/people");
    }

    @GetMapping("/search")
    public String searchPage() {
        return "people/search";
    }

    @PostMapping("/search")
    public String makSearch(Model model,
                            @RequestParam(value = "fio_start_with", required = false) String fioStartWith) {
        model.addAttribute("people", peopleService.findByFioStartingWith(fioStartWith));
        List<Person> list = peopleService.findByFioStartingWith(fioStartWith);
        System.out.println(list);
        return "people/search";
    }

    private StringBuilder formingBaseUrl(boolean sortByFio, boolean sortByYearOfBirth, int peoplePerPage){
        StringBuilder baseUrl = new StringBuilder("/people?");
        if (sortByFio ) {
            baseUrl.append("sort_by_fio=").append(sortByFio).append("&");
        }
        if (sortByYearOfBirth) {
            baseUrl.append("sort_by_yearOfBirth=").append(sortByYearOfBirth).append("&");
        }
        baseUrl.append("people_per_page=").append(peoplePerPage);
        return baseUrl;
    }

    //Пара ключ-значение добавится в модел каждого метода этого контроллера
    @ModelAttribute("message")
    public String message() {
        return "Message for all";
    }
}

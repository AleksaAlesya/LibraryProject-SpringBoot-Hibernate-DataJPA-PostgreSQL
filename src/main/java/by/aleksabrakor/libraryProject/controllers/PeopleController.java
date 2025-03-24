package by.aleksabrakor.libraryProject.controllers;


import by.aleksabrakor.libraryProject.models.Person;
import by.aleksabrakor.libraryProject.pagination.PaginationData;
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

import static by.aleksabrakor.libraryProject.pagination.PaginationData.createPaginationData;
import static by.aleksabrakor.libraryProject.util.UrlUtils.formingBaseUrlForPeople;

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
            peoplePerPage = 30;
        }

        //Сортируем и Получаем данные с пагинацией
        Page<Person> peoplePage = peopleService.findWithPagination(page, peoplePerPage, sortByFio, sortByYearOfBirth);

        // Формируем базовый URL
        String baseUrl = formingBaseUrlForPeople(sortByFio, sortByYearOfBirth, peoplePerPage);

        // Вычисляем диапазон страниц для пагинации
        PaginationData<Person> personPaginationData = createPaginationData(peoplePage, page, baseUrl);

        model.addAttribute("paginationData", personPaginationData);
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

    //Пара ключ-значение добавится в модел каждого метода этого контроллера
    @ModelAttribute("message")
    public String message() {
        return "Message for all";
    }
}

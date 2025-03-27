package by.aleksabrakor.libraryProject.specification;

import by.aleksabrakor.libraryProject.models.Book;
import by.aleksabrakor.libraryProject.models.Person;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PeopleSpecification {

    public static Specification<Person> sortByParameter(boolean sortByFio, boolean sortByYearOfBirth) {
        return (root, query, criteriaBuilder) -> {
            // Создаем список для хранения порядка сортировки
            List<Order> orders = new ArrayList<>();
            if (sortByFio) {
                orders.add(criteriaBuilder.asc(root.get("fio")));
            }
            if (sortByYearOfBirth) {
                orders.add(criteriaBuilder.asc(root.get("yearOfBirth")));
            }
            // Применяем сортировку к запросу, если есть хотя бы одно условие
            if (!orders.isEmpty()) {
                query.orderBy(orders);
            }
            // Возвращаем null, так как это спецификация для сортировки, а не для фильтрации
            return null;
        };
    }

    //фильтрация по части в названия
    public static Specification<Person> fioLike(String fioPart) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("fio")), // Приводим поле к нижнему регистру
                        String.format("%%%s%%", fioPart.toLowerCase()) // Приводим значение поиска к нижнему регистру
                );
    }
}
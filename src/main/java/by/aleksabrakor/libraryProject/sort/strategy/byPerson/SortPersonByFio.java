package by.aleksabrakor.libraryProject.sort.strategy.byPerson;

import by.aleksabrakor.libraryProject.models.Person;
import by.aleksabrakor.libraryProject.sort.strategy.SortStrategy;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;

import java.util.Collections;
import java.util.List;

public class SortPersonByFio implements SortStrategy<Person> {
    @Override
    public List<Order> apply(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return Collections.singletonList(criteriaBuilder.asc(root.get("fio")));
    }
}
package by.aleksabrakor.libraryProject.sort.strategy;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;

import java.util.List;

public interface SortStrategy<T> {
    List<Order> apply(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);
}
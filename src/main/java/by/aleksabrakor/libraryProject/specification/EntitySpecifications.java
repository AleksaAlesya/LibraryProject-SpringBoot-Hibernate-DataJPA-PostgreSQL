package by.aleksabrakor.libraryProject.specification;

import by.aleksabrakor.libraryProject.sort.strategy.SortStrategy;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class EntitySpecifications {
    public static <T> Specification<T> withSorting(SortStrategy<T> sortStrategy) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (sortStrategy != null) {
                query.orderBy(sortStrategy.apply(root, query, criteriaBuilder));
            }
            return null; // Нет фильтрации, только сортировка
        };
    }
}
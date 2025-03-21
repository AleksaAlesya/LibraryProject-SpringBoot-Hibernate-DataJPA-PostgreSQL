package by.aleksabrakor.libraryProject.specification;

import by.aleksabrakor.libraryProject.models.Book;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BooksSpecification {

    public static Specification<Book> sortByParameters(boolean  sortByTitle, boolean sortByAuthor, boolean sortByYear) {
        return (root, query, criteriaBuilder) -> {
            // Создаем список для хранения порядка сортировки
            List<Order> orders = new ArrayList<>();
            if (sortByTitle) {
                orders.add(criteriaBuilder.asc(root.get("title")));
            }
            if (sortByAuthor) {
                orders.add(criteriaBuilder.asc(root.get("author")));
            }
            if (sortByYear) {
                orders.add(criteriaBuilder.asc(root.get("year")));
            }
            // Применяем сортировку к запросу, если есть хотя бы одно условие
            if (!orders.isEmpty()) {
                query.orderBy(orders);
            }
            // Возвращаем null, так как это спецификация для сортировки, а не для фильтрации
            return null;
        };
    }
}
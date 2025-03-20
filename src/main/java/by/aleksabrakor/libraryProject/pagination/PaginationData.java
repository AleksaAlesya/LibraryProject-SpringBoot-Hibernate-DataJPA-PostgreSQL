package by.aleksabrakor.libraryProject.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
@Data
@AllArgsConstructor
public class PaginationData<T>{
    private List<T> items; // Список элементов на текущей странице
    private int currentPage; // Текущая страница
    private int totalPages; // Общее количество страниц
    private int startPage; // Начальная страница для отображения
    private int endPage; // Конечная страница для отображения
    private String baseUrl; // Базовый URL с параметрами сортировки

    public static <T> PaginationData<T> createPaginationData(Page<T> page, int currentPage, String baseUrl) {
        int totalPages = page.getTotalPages();
        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(currentPage + 2, totalPages);

        return new PaginationData<>(
                page.getContent(), // Список элементов на текущей странице
                currentPage, // Текущая страница
                totalPages, // Общее количество страниц
                startPage, // Начальная страница для отображения
                endPage, // Конечная страница для отображения
                baseUrl // Базовый URL с параметрами сортировки
        );
    }
}

package by.aleksabrakor.libraryProject.sort;

import by.aleksabrakor.libraryProject.sort.strategy.byPerson.SortPersonByFio;
import by.aleksabrakor.libraryProject.sort.strategy.byPerson.SortPersonByFioAndYearOfBirth;
import by.aleksabrakor.libraryProject.sort.strategy.byPerson.SortPersonByYearOfBirth;
import by.aleksabrakor.libraryProject.sort.strategy.SortStrategy;

public class SortStrategyFactory {
    public static <T> SortStrategy<T> createPersonSortStrategy(boolean sortByFio, boolean sortByYearOfBirth) {
        if (sortByFio && sortByYearOfBirth) {
            return (SortStrategy<T>) new SortPersonByFioAndYearOfBirth();
        } else if (sortByFio) {
            return (SortStrategy<T>) new SortPersonByFio();
        } else if (sortByYearOfBirth) {
            return (SortStrategy<T>) new SortPersonByYearOfBirth();
        } else {
            return null; // Нет сортировки
        }
    }
}
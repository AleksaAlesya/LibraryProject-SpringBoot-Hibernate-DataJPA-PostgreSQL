package by.aleksabrakor.libraryProject.util;

public class UrlUtils {

    public static String formingBaseUrlForPeople(boolean sortByFio, boolean sortByYearOfBirth, int peoplePerPage){
        StringBuilder baseUrl = new StringBuilder("/people?");
        if (sortByFio ) {
            baseUrl.append("sort_by_fio=").append(sortByFio).append("&");
        }
        if (sortByYearOfBirth) {
            baseUrl.append("sort_by_yearOfBirth=").append(sortByYearOfBirth).append("&");
        }
        baseUrl.append("people_per_page=").append(peoplePerPage);
        return baseUrl.toString();
    }

    public static String formingBaseUrlForBooks( boolean sortByTitle, boolean sortByAuthor, boolean sortByYear, int booksPerPage){
        StringBuilder baseUrl = new StringBuilder("/books?");
        if (sortByTitle ) {
            baseUrl.append("sort_by_title=").append(sortByTitle).append("&");
        }
        if (sortByAuthor) {
            baseUrl.append("sort_by_author=").append(sortByAuthor).append("&");
        }
        if (sortByYear) {
            baseUrl.append("sort_by_year=").append(sortByYear).append("&");
        }
        baseUrl.append("books_per_page=").append(booksPerPage);
        return baseUrl.toString();
    }
}

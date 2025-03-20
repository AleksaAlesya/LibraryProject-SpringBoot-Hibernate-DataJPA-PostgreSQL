package by.aleksabrakor.libraryProject.util;

public class UrlUtils {

    public static String formingBaseUrl(boolean sortByFio, boolean sortByYearOfBirth, int peoplePerPage){
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
}

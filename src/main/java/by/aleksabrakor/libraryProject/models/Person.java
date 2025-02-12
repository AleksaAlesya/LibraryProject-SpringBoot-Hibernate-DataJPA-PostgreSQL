package by.aleksabrakor.libraryProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="person")
@Data
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @NotEmpty(message = "ФИО не должно быть пустым")
    @Size(min = 2, max = 100, message = "Поле не должно быть меньше 2 и больше 30 символов")
//    @Pattern(regexp = "[A-Z, А-Я]\\w+, [A-Z, А-Я]\\w+, [A-Z, А-Я]\\w+", message = " Фио должно быть в формате: Иванов Иван Иванович")
    @Column(name = "fio")
    private String fio;

    @NotNull(message = "Год рождения не доллжен быть пустым")
    @Min(value = 1900, message = "Год рождения не должен быть больше 1900")
    @Max(value = 2100, message = "WOW!!!")
    @Column(name = "yearofbirth")
    private int yearOfBirth;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;


    public Person(int id, String fio, int yearOfBirth) {
        this.id = id;
        this.fio = fio;
        this.yearOfBirth = yearOfBirth;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", books=" + books +
                '}';
    }
}

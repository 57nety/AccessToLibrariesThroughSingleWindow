package ru.alexenko.diplom.database.entity;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Класс сущности "Избранная книга".
 * Является таблицей БД, связанной с user_account связью ManyToOne.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "favorites")
public class Favorites implements Serializable {

    @Id
    @SerializedName("id")
    @SequenceGenerator(
            name = "favorites_sequence",
            sequenceName = "favorites_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "favorites_sequence"
    )
    private Long id;

    @SerializedName("book_id")
    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @SerializedName("library")
    @Column(name = "library", nullable = false)
    private String library;

    @SerializedName("book_title")
    @Column(name = "book_title", nullable = false)
    private String bookTitle;

    @SerializedName("book_link")
    @Column(name = "book_link", nullable = false)
    private String bookLink;

    @SerializedName("date")
    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Метод реализует представление объекта в формате строки JSON
     * @return строка в формате JSON
     */
    @Override
    public String toString() {
        return "\n{" +
                "\"id\":" + id + "," +
                "\"book_id\":" + bookId + "," +
                "\"book_title\":" + "\"" + bookTitle + "\"," +
                "\"book_link\":" + "\"" + bookLink + "\"" +
                '}';
    }

    public String getBookTitle() {
        return addEscapeCharacters(bookTitle);
    }

    /**
     * Метод расставляет символы '\' для корректного парсинга строки в объект.
     * @param jsonBook входная строка
     * @return строка с расставленными символами '\'
     */
    private String addEscapeCharacters(String jsonBook) {
        return jsonBook.chars()
                .mapToObj(ch -> {
                    if (ch == '\"') {
                        return "\\\"";
                    } else if (ch == '\\') {
                        return "";
                    } else {
                        return Character.toString((char) ch);
                    }
                })
                .collect(Collectors.joining());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorites favorites = (Favorites) o;
        return Objects.equals(bookId, favorites.bookId) && Objects.equals(library, favorites.library) && Objects.equals(bookTitle, favorites.bookTitle) && Objects.equals(bookLink, favorites.bookLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, library, bookTitle, bookLink);
    }
}

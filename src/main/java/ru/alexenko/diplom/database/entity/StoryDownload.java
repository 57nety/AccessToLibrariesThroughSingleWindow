package ru.alexenko.diplom.database.entity;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Класс сущности "История скачивания".
 * Является таблицей БД, связанной с user_account связью ManyToOne.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "story_download")
public class StoryDownload implements Serializable {

    @Id
    @SequenceGenerator(
            name = "story_download_sequence",
            sequenceName = "story_download_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "story_download_sequence"
    )
    @SerializedName("id")
    private Long id;

    @SerializedName("book_id")
    @Column(name = "book_id", nullable = false)
    private Long bookId;

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
     * Метод реализует представление времени в формате HH:mm dd.MM.yy.
     * @return время в формате HH:mm dd.MM.yy
     */
    public String getDate() {
        return new SimpleDateFormat("HH:mm dd.MM.yy").format(this.date);
    }

    /**
     * Метод реализует представление объекта в формате строки JSON
     * @return строка в формате JSON
     */
    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id + "," +
                "\"book_id\":" + bookId + "," +
                "\"book_title\":" + "\"" + bookTitle + "\"," +
                "\"book_link\":" + "\"" + bookLink + "\"" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryDownload that = (StoryDownload) o;
        return Objects.equals(id, that.id) && Objects.equals(bookId, that.bookId) && Objects.equals(bookLink, that.bookLink) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId, bookLink, date);
    }
}

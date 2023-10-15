package ru.alexenko.diplom.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Класс сущности "История поиска".
 * Является таблицей БД, связанной с user_account связью ManyToOne.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "story_search")
public class StorySearch implements Serializable {

    @Id
    @SequenceGenerator(
            name = "story_search_sequence",
            sequenceName = "story_search_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "story_search_sequence"
    )
    private Long id;

    @Column(name = "search_object", nullable = false)
    private String searchObject;

    @Column(name = "request", nullable = false)
    private String request;

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

    List
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StorySearch that = (StorySearch) o;
        return Objects.equals(id, that.id) && Objects.equals(searchObject, that.searchObject) && Objects.equals(request, that.request) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, searchObject, request, date);
    }

    @Override
    public String toString() {
        return "StorySearch{" +
                "id=" + id +
                ", searchObject='" + searchObject + '\'' +
                ", request='" + request + '\'' +
                ", date=" + date +
                '}';
    }
}


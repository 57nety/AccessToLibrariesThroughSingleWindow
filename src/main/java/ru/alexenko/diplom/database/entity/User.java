package ru.alexenko.diplom.database.entity;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

/**
 * Класс сущности User.
 * Является родительской таблицей в БД.
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_account")
public class User implements Serializable {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @SerializedName("id")
    private Long id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<StorySearch> storySearches;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<StoryDownload> storyDownloads;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Favorites> favorites;

    /**
     * Метод делает новую запись в истории поиска пользователя.
     * @param storySearch информация о запросе пользователя
     */
    public void addStorySearch(StorySearch storySearch) {
        this.storySearches.add(storySearch);
    }

    /**
     * Метод делает новую запись о скачанной книге.
     * @param storyDownload информация о скачанной книге
     */
    public void addStoryDownload(StoryDownload storyDownload) {
        this.storyDownloads.add(storyDownload);
    }

    /**
     * Метод добавляет книгу в список избранных книг пользователя
     * @param favorites информация об избранной книге
     */
    public void addFavorites(Favorites favorites) {
        this.favorites.add(favorites);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", \n storySearches=" + storySearches +
                ", \n storyDownloads=" + storyDownloads +
                ", \n favorites=" + favorites +
                '}';
    }
}

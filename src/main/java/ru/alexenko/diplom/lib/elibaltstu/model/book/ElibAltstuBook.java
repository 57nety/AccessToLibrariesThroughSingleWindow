package ru.alexenko.diplom.lib.elibaltstu.model.book;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Модель книги из библиотеки АлтГТУ
 */
@Getter
@Setter
public class ElibAltstuBook {

    @SerializedName("m_id")
    private Long id;

    @SerializedName("title")
    private String title;

    @SerializedName("link")
    private String url;

    @SerializedName("author_fio")
    private String authorFio;

    @SerializedName("mat_year")
    private Integer matYear;

    /**
     * Строковое представление объекта в формате JSON
     * @return строка в формате JSON
     */
    @Override
    public String toString() {
        return "{" +
                "\"m_id\":" + id + "," +
                "\"title\":" + "\"" + getTitle() + "\"," +
                "\"link\":" + "\"" + getUrl() + "\"," +
                "\"author_fio\":" + "\"" + getAuthorFio() + "\"," +
                "\"mat_year\":" + matYear +
                '}';
    }

    public String getTitle() {
        return addEscapeCharacters(title);
    }

    public String getUrl() {
        return addEscapeCharacters(url);
    }

    public String getAuthorFio() {
        return addEscapeCharacters(authorFio);
    }

    /**
     * Метод реализует представление объекта в формате строки JSON
     * @return строка в формате JSON
     */
    private String addEscapeCharacters(String jsonBook) {
        return Arrays.stream(jsonBook.split(""))
                .map(c -> {
                    if (c.equals("\"")) {
                        return "\\" + c; // Добавить обратную косую черту перед кавычкой
                    } else if (c.equals("\\")) {
                        return "\\" + c; // Добавить обратную косую черту перед обратной косой чертой
                    } else {
                        return c;
                    }
                })
                .collect(Collectors.joining());
    }
}

package ru.alexenko.diplom.lib.iprsmart.model.book;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * Модель книги из библиотеки IPR SMART полученной через запрос на получение книги.
 */
@Getter
@Setter
public class IprSmartBook {

    @SerializedName("id")
    private Long id;

    @SerializedName("title")
    private String title;

    @SerializedName("title_additional")
    private String titleAdditional;

    @SerializedName("pubhouse")
    private String pubhouse;

    @SerializedName("authors")
    private String authors;

    @SerializedName("liability")
    private String liability;

    @SerializedName("pubyear")
    private Integer pubyear;

    @SerializedName("description")
    private String description;

    @SerializedName("keywords")
    private String keywords;

    @SerializedName("pubtype")
    private String pubtype;

    @SerializedName("addit_subscribe")
    private Integer additSubscribe;

    @SerializedName("url")
    private String url;

    @SerializedName("image")
    private String image;

    /**
     * Делает представление строки в формате JSON.
     * @return строка в формате JSON
     */
    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id + "," +
                "\"title\":" + "\"" + title + "\"," +
                "\"title_additional\":" + "\"" + titleAdditional + "\"," +
                "\"pubhouse\":" + "\"" + pubhouse + "\"," +
                "\"authors\":" + "\"" + authors + "\"," +
                "\"liability\":" + "\"" + liability + "\"," +
                "\"pubyear\":" + pubyear + "," +
                "\"description\":" + "\"" + description + "\"," +
                "\"keywords\":" + "\"" + keywords + "\"," +
                "\"pubtype\":" + "\"" + pubtype + "\"," +
                "\"addit_subscribe\":" + additSubscribe + "," +
                "\"url\":" + "\"" + url + "\"," +
                "\"image\":" + "\"" + image + "\"" +
                '}';
    }
}

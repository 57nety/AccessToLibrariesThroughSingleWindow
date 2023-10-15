package ru.alexenko.diplom.lib.iprsmart.model.book;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * Модель книги из библиотеки IPR SMART полученной через список книг в дисциплине.
 */
@Getter
@Setter
public class IprSmartBookInDiscipline {

    @SerializedName("id")
    private Integer id;

    @SerializedName("discipline_id")
    private Integer disciplineId;

    @SerializedName("publication_id")
    private Integer publicationId;

    @SerializedName("type")
    private Integer type;

    @SerializedName("library_card")
    private String libraryCard;
}

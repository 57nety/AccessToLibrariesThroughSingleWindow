package ru.alexenko.diplom.lib.iprsmart.model.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import ru.alexenko.diplom.lib.iprsmart.model.book.IprSmartBookInDiscipline;

import java.util.List;

/**
 * Модель ответа на запрос на получение списка книг в дисциплине.
 */
@Getter
@Setter
public class IprSmartBooksInDisciplineResponse {

    @SerializedName("success")
    private Boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("total")
    private Integer total;

    @SerializedName("status")
    private Integer status;

    @SerializedName("data")
    private List<IprSmartBookInDiscipline> books;
}

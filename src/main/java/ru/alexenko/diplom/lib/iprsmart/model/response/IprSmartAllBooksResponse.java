package ru.alexenko.diplom.lib.iprsmart.model.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import ru.alexenko.diplom.lib.iprsmart.model.book.IprSmartBook;

import java.util.List;

/**
 * Модель ответа на запрос получения списка всех книг.
 */
@Getter
@Setter
public class IprSmartAllBooksResponse {

    @SerializedName("success")
    private Boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("total")
    private Integer total;

    @SerializedName("status")
    private Integer status;

    @SerializedName("data")
    private List<IprSmartBook> books;
}

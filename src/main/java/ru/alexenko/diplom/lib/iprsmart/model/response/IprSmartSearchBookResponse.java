package ru.alexenko.diplom.lib.iprsmart.model.response;


import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import ru.alexenko.diplom.lib.iprsmart.model.book.IprSmartBook;

import java.util.Map;

/**
 * Модель ответа на запрос на поиск книги.
 */
@Getter
@Setter
public class IprSmartSearchBookResponse {

    @SerializedName("success")
    private Boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private Integer status;

    @SerializedName("data")
    private IprSmartBook data;

    @SerializedName("replacement_data")
    private String replacementData;

    @SerializedName("content")
    private Map<Integer, String> content;
}

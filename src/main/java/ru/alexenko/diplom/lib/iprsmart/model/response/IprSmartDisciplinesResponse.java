package ru.alexenko.diplom.lib.iprsmart.model.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import ru.alexenko.diplom.lib.iprsmart.model.discipline.IprSmartDiscipline;

import java.util.List;

/**
 * Модель ответа на запрос на получение списка дисциплин.
 */
@Getter
@Setter
public class IprSmartDisciplinesResponse {

    @SerializedName("success")
    private Boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("total")
    private String total;

    @SerializedName("status")
    private Integer status;

    @SerializedName("data")
    private List<IprSmartDiscipline> disciplines;
}

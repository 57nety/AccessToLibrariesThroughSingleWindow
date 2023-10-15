package ru.alexenko.diplom.lib.elibaltstu.model.discipline;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * Модель дисциплины из библиотеки АлтГТУ.
 */
@Getter
@Setter
public class ElibAltstuDiscipline {

    @SerializedName("disc_id")
    private Integer id;

    @SerializedName("discipline")
    private String name;

    /**
     * Строковое представление объекта ElibAltstuDiscipline в формате JSON.
     * @return строковое представление в формате JSON
     */
    @Override
    public String toString() {
        return "{" +
                "\"disc_id\":" + id + "," +
                "\"discipline\":" + "\"" + name + "\"" +
                '}';
    }
}

package ru.alexenko.diplom.lib.iprsmart.model.discipline;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * Модель дисциплины из библиотеки IPR SMART.
 */
@Getter
@Setter
public class IprSmartDiscipline {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("full_compliance")
    private String fullCompliance;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id + "," +
                "\"name\":" + "\"" + name + "\"," +
                "\"fullCompliance\":" + "\"" + fullCompliance + "\"" +
                '}';
    }
}

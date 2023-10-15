package ru.alexenko.diplom.web.service;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexenko.diplom.lib.elibaltstu.service.ElibAltstuApiService;
import ru.alexenko.diplom.lib.elibaltstu.model.discipline.ElibAltstuDiscipline;
import ru.alexenko.diplom.lib.iprsmart.service.IprSmartApiService;
import ru.alexenko.diplom.lib.iprsmart.model.discipline.IprSmartDiscipline;

@Service
@RequiredArgsConstructor
@Getter
public class DisciplineService {

    private final Gson gson;

    private final ElibAltstuApiService elibAltstuApiService;

    private final IprSmartApiService iprSmartApiService;

    public ElibAltstuDiscipline fromJsonToElibAltstuDiscipline(String jsonDiscipline) {
        try {
            return gson.fromJson(jsonDiscipline, ElibAltstuDiscipline.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось отобразить JSON в ElibAltstuDiscipline");
            return null;
        }
    }

    public IprSmartDiscipline fromJsonToIprSmartDiscipline(String jsonDiscipline) {
        try {
            return gson.fromJson(jsonDiscipline, IprSmartDiscipline.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось отобразить JSON в IprSmartDiscipline");
            return null;
        }
    }
}

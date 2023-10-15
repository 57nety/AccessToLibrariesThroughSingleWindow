package ru.alexenko.diplom.web.service;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexenko.diplom.database.entity.Favorites;
import ru.alexenko.diplom.database.entity.StoryDownload;
import ru.alexenko.diplom.lib.elibaltstu.service.ElibAltstuApiService;
import ru.alexenko.diplom.lib.elibaltstu.model.book.ElibAltstuBook;
import ru.alexenko.diplom.lib.iprsmart.service.IprSmartApiService;
import ru.alexenko.diplom.lib.iprsmart.model.book.IprSmartBook;

@Service
@RequiredArgsConstructor
@Getter
public class BookService {

    private final Gson gson;

    private final ElibAltstuApiService elibAltstuApiService;

    private final IprSmartApiService iprSmartApiService;

    public ElibAltstuBook fromJsonToElibAltstuBook(String jsonBook) {
        try {
            return gson.fromJson(jsonBook, ElibAltstuBook.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось отобразить JSON в ElibAltstuBook");
            return null;
        }
    }

    public Favorites fromJsonToElibAltstuFavoriteBook(String jsonBook) {
        try {
            return gson.fromJson(jsonBook, Favorites.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось отобразить JSON в ElibAltstuFavoriteBook");
            return null;
        }
    }

    public IprSmartBook fromJsonToIprSmartBook(String jsonBook) {
        try {
            return gson.fromJson(jsonBook, IprSmartBook.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось отобразить JSON в IprSmartBook");
            return null;
        }
    }

    public StoryDownload fromJsonToElibAltstuDownloadBook(String jsonBook) {
        try {
            return gson.fromJson(jsonBook, StoryDownload.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось отобразить JSON в ElibAltstuDownloadBook");
            return null;
        }
    }
}

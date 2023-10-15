package ru.alexenko.diplom.lib.elibaltstu.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.alexenko.diplom.lib.elibaltstu.model.book.ElibAltstuBook;
import ru.alexenko.diplom.lib.elibaltstu.model.discipline.ElibAltstuDiscipline;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Сервис реализует взаимодействия с API ЭБС АлтГТУ.
 */
@Service
@RequiredArgsConstructor
public class ElibAltstuApiService {

    /**
     * Базовый url к которому отправляются запросы.
     */
    @Value("${api.elib_altstu.url}")
    private String apiUrl;

    /**
     * uid - user id, используется для идентификации пользователя
     */
    private String uid;

    /**
     * token, используется для идентификации пользователя
     */
    private String token;

    private final WebClient webClient;

    private final Gson gson;

    /**
     * Метод отправляет запрос на идентификацию пользователя в системе по введённым им логину и паролю.
     * @param login логин
     * @param password пароль
     * @return true - пользователь авторизирован, false - пользователь не авторизирован
     */
    public Boolean authenticateUser(String login, String password) {
        try {
            String endpoint = apiUrl + "?request=auth&login=" + login + "&password=" + password;
            String response = requestToApi(endpoint);
            if (response == null){
                return Boolean.FALSE;
            }
            JSONArray jsonArray = new JSONArray(response);
            uid = jsonArray.getString(0);
            token = jsonArray.getString(1);
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось аутентифицировать пользователя в системе ЭБС АлтГТУ");
            return Boolean.FALSE;
        }
    }

    /**
     * Выход пользователя из аккаунта, отправляется запрос на удаление uid и token.
     * @return true - пользователь вышел из аккаунта, false - пользователь не вышел из аккаунта
     */
    public Boolean logoutFromProfile() {
        String endpoint = apiUrl + "?request=logoff&uid=" + uid + "&token=" + token;
        String response = requestToApi(endpoint);
        return response == null;
    }


    /**
     * Получить список всех книг по запросу, если запрос пуст, то получается список всех книг из библиотеки.
     * @param query запрос на поиск
     * @return список книг по запросу
     */
    public List<ElibAltstuBook> getAllBooks(String query) {
        try {
            String endpoint = apiUrl + "?request=search&uid=" + uid + "&token=" + token + "&query=" + query;
            String response = requestToApi(endpoint);
            List<ElibAltstuBook> books = gson.fromJson(response, new TypeToken<List<ElibAltstuBook>>() {
            }.getType());
            return books.stream().sorted(Comparator.comparing(ElibAltstuBook::getTitle)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось получить список всех книг из ЭБС АлтГТУ");
            return null;
        }
    }

    /**
     * Получить первые 10 книг из списка всех книг в библиотеки
     * @return первые 10 книг из всего списка
     */
    public List<ElibAltstuBook> getPreviewBooks() {
        try {
            return getAllBooks("").stream().limit(10).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось получить preview всех книг из ЭБС АлтГТУ");
            return null;
        }
    }

    /**
     * Поиск книги по конкретному запросу
     * @param query запрос на поиск книги
     * @return список найденных по запросу книг
     */
    public List<ElibAltstuBook> searchBooks(String query) {
        List<ElibAltstuBook> foundBooks = getAllBooks(query);
        if (foundBooks == null) {
            System.out.println("\nОШИБКА! Не удалось получить список книг по запросу из ЭБС АлтГТУ");
        }
        return foundBooks;
    }

    /**
     * Получить всю информацию о конкретной книге
     * @param bookId id книги
     * @param title название книги
     * @return вяс информация о книге
     */
    public ElibAltstuBook getBook(Long bookId, String title) {
        try {
            String endpoint = apiUrl + "?request=search&uid=" + uid + "&token=" + token + "&query=" + title;
            String response = requestToApi(endpoint);
            List<ElibAltstuBook> books = gson.fromJson(response, new TypeToken<List<ElibAltstuBook>>() {
            }.getType());
            return books.stream().filter(book -> book.getId().equals(bookId)).findFirst().orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось получить конкретную книгу из ЭБС АлтГТУ");
            return null;
        }
    }

    /**
     * Скачать книгу
     * @param bookId id книги
     * @param response, в него помещается книга, которая будет скачана браузером
     */
    public void downloadBook(Long bookId, HttpServletResponse response) {
        try {
            String endpoint = apiUrl + "?request=getmat&uid=" + uid + "&token=" + token + "&mat_id=" + bookId.intValue();

            URL fileUrl = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) fileUrl.openConnection();

            String fileLocation = connection.getHeaderField("location");

            response.setHeader("Content-Disposition", "attachment; filename=\"filename.pdf\"");
            response.setHeader("Content-Type", "application/pdf");

            response.setHeader("Location", fileLocation);

            // Отправляю ответ с пустым телом
            response.setContentLength(0);
            response.setStatus(HttpServletResponse.SC_FOUND); // 302 Redirect

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось скачать книгу из ЭБС АлтГТУ");
        }
    }

    /**
     * Получить список всех дисциплин из библиотеки
     * @param query запрос на поиск
     * @return список дисциплин соответствующих запросу
     */
    public List<ElibAltstuDiscipline> getAllDisciplines(String query) {
        try {
            String endpoint = apiUrl + "?request=disc&uid=" + uid + "&token=" + token + "&query=" + query;
            String response = requestToApi(endpoint);
            List<ElibAltstuDiscipline> books = gson.fromJson(response, new TypeToken<List<ElibAltstuDiscipline>>() {
            }.getType());
            if (books == null) {
                return new ArrayList<>();
            }
            return books.stream().sorted(Comparator.comparing(ElibAltstuDiscipline::getName)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось получить список всех дисциплин из ЭБС АлтГТУ");
            return null;
        }
    }

    /**
     * Получить первые 10 дисциплин из всего списка
     * @param query пустой параметр
     * @return первые 10 книг из всего списка
     */
    public List<ElibAltstuDiscipline> getPreviewDisciplines(String query) {
        try {
            return getAllDisciplines("").stream().limit(10).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось получить preview всех дисциплин из ЭБС АлтГТУ");
            return null;
        }
    }

    /**
     * Получить дисциплину по запросу
     * @param query запрос на поиск дисциплины
     * @return список всех дисциплин по запросу
     */
    public List<ElibAltstuDiscipline> searchDisciplines(String query) {
        List<ElibAltstuDiscipline> foundDisciplines = getAllDisciplines(query);
        if (foundDisciplines == null) {
            System.out.println("\nОШИБКА! Не удалось получить список дисциплин по запросу из ЭБС АлтГТУ");
        }
        return foundDisciplines;
    }

    /**
     * Получить список всех книг в конкретной дисциплине
     * @param disciplineId id дисциплины
     * @return список всех книг в дисциплине
     */
    public List<ElibAltstuBook> getBooksInDiscipline(Integer disciplineId) {
        try {
            String endpoint = apiUrl + "?request=discmat&uid=" + uid + "&token=" + token + "&disc_id=" + disciplineId;
            String response = requestToApi(endpoint);
            List<ElibAltstuBook> books = gson.fromJson(response, new TypeToken<List<ElibAltstuBook>>() {
            }.getType());
            return books.stream().sorted(Comparator.comparing(ElibAltstuBook::getTitle)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось получить спиок книг в дисциплине из ЭБС АлтГТУ");
            return null;
        }
    }

    /**
     * Метод реализует запрос к api в зависимости от endpoint.
     * @param endpoint к которому будет осуществлён запрос
     * @return ответ от API
     */
    private String requestToApi(String endpoint) {
        try {
            return webClient.get()
                    .uri(endpoint)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось выполнить ЗАПРОС к API ЭБС АлтГТУ");
            return null;
        }
    }
}

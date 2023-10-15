package ru.alexenko.diplom.lib.iprsmart.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.alexenko.diplom.lib.iprsmart.jwt.JwtProvider;
import ru.alexenko.diplom.lib.iprsmart.model.book.IprSmartBook;
import ru.alexenko.diplom.lib.iprsmart.model.book.IprSmartBookInDiscipline;
import ru.alexenko.diplom.lib.iprsmart.model.discipline.IprSmartDiscipline;
import ru.alexenko.diplom.lib.iprsmart.model.response.IprSmartAllBooksResponse;
import ru.alexenko.diplom.lib.iprsmart.model.response.IprSmartBooksInDisciplineResponse;
import ru.alexenko.diplom.lib.iprsmart.model.response.IprSmartDisciplinesResponse;
import ru.alexenko.diplom.lib.iprsmart.model.response.IprSmartSearchBookResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис реализующий запросы к API ЭБС IPR SMART.
 */
@RequiredArgsConstructor
@Service
public class IprSmartApiService {

    @Value("${api.ipr_smart.url}")
    private String apiUrl;

    @Value("${api.ipr_smart.x_apikey}")
    private String xApiKey;

    @Value("${api.ipr_smart.client_id}")
    private Integer clientId;

    private final WebClient webClient;

    private final Gson gson;

    private final JwtProvider jwtProvider;

    /**
     * Получение списка всех книг.
     * @return список всех книг
     */
    public List<IprSmartBook> getAllBooks() {
        try {
            String endpoint = apiUrl + "/resources/books/?client_id=" + clientId;
            String response = request(endpoint);
            IprSmartAllBooksResponse iprSmartAllBooksResponse = gson.fromJson(response, new TypeToken<IprSmartAllBooksResponse>() {}.getType());
            List<IprSmartBook> books = iprSmartAllBooksResponse.getBooks();
            return books.stream().sorted(Comparator.comparing(IprSmartBook::getTitle)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось получить список всех книг из IPR SMART");
            return null;
        }
    }

    /**
     * Получить первые 10 книг из всего списка книг.
     * @return первые 10 книг из всего списка
     */
    public List<IprSmartBook> getPreviewBooks() {
        try {
            return getAllBooks().stream().limit(10).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось получить preview всех книг из IPR SMART");
            return null;
        }
    }

    /**
     * Поиск книг по введённому запросу.
     * @param query поисковый запрос
     * @return список книг по поисковому запросу
     */
    public List<IprSmartBook> searchBooks(String query) {
        try {
            String subStr = query.toLowerCase();
            return getAllBooks().stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(subStr) || book.getKeywords().contains(subStr))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось получить список книг по запросу из IPR SMART");
            return null;
        }
    }

    /**
     * Получить всю информацию о книге.
     * @param bookId id книги
     * @return вся инфрормация о книге
     */
    public IprSmartBook getBook(Long bookId) {
        try {
            String endpoint = apiUrl + "/resources/books/get/" + bookId + "?client_id=" + clientId;
            String response = request(endpoint);
            IprSmartSearchBookResponse iprSmartSearchBookResponse = gson.fromJson(response, new TypeToken<IprSmartSearchBookResponse>() {}.getType());
            return iprSmartSearchBookResponse.getData();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось получить конкретную книгу из IPR SMART");
            return null;
        }
    }

    /**
     * Поиск дисциплины по запросу.
     * @param query поисковый запрос
     * @return список дисциплин по запросу
     */
    public List<IprSmartDiscipline> searchDisciplines(String query) {
        try {
            String endpoint = apiUrl + "/bookselling/disciplines/get?client_id=" + clientId + "&discipline=" + query;
            String response = request(endpoint);
            IprSmartDisciplinesResponse iprSmartAllBooksResponse = gson.fromJson(response, new TypeToken<IprSmartDisciplinesResponse>() {}.getType());
            List<IprSmartDiscipline> disciplines = iprSmartAllBooksResponse.getDisciplines();
            if (disciplines == null) {
                return new ArrayList<>();
            }
            return disciplines.stream().sorted(Comparator.comparing(IprSmartDiscipline::getName)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось получить список дисциплин по запросу из IPR SMART");
            return null;
        }
    }

    /**
     * Список книг в конкретной дисциплине.
     * @param disciplineId id дисциплине
     * @return список книг в дисциплине
     */
    public List<IprSmartBookInDiscipline> getBooksInDiscipline(Integer disciplineId) {
        try {
            String endpoint = apiUrl + "/bookselling/" + disciplineId + "/get?client_id=" + clientId;
            String response = request(endpoint);
            IprSmartBooksInDisciplineResponse booksInDisciplineResponse = gson.fromJson(response, new TypeToken<IprSmartBooksInDisciplineResponse>() {}.getType());
            return booksInDisciplineResponse.getBooks();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось получить спиок книг в дисциплине из IPR SMART");
            return null;
        }
    }

    /**
     * Метод реализует запросы к API по сформированному endpoint.
     * @param endpoint сформированный
     * @return ответ в виде строки
     */
    public String request(String endpoint) {
        try {
            String jwtToken = jwtProvider.generateToken(clientId);
            String authorizationHeader = "Bearer " + jwtToken;
            return webClient.get()
                    .uri(endpoint)
                    .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                    .header("X-APIKey", xApiKey)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nОШИБКА! Не удалось выполнить запрос к API IPR SMART");
            return null;
        }
    }
}

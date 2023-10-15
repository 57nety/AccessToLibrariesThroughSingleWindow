package ru.alexenko.diplom.database.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexenko.diplom.database.entity.Favorites;
import ru.alexenko.diplom.database.entity.StoryDownload;
import ru.alexenko.diplom.database.entity.StorySearch;
import ru.alexenko.diplom.database.entity.User;
import ru.alexenko.diplom.database.repository.UserRepository;

import java.util.Date;
import java.util.Set;

/**
 * Класс UserService реализует связь сущности User с БД.
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final FavoritesService favoritesService;

    private final StorySearchService storySearchService;

    private final StoryDownloadService storyDownloadService;

    /**
     * Метод реализующий сохранение пользователя в БД.
     * @param user пользователь
     * @return пользователь, если сохранение успешно, иначе null
     */
    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ОШИБКА! Не удалось сохранить пользователя в БД");
            return null;
        }
    }

    /**
     * Поиск пользователя в БД по заданному логину.
     * @param login заданный логин
     * @return пользователь с заданным логином
     */
    public User findByLogin(String login) {
        try {
            User user = userRepository.findByLogin(login);
            if (user == null){
                return user;
            }
            user.setFavorites(getFavorites(user));
            user.setStorySearches(getStorySearch(user));
            user.setStoryDownloads(getStoryDownload(user));
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ОШИБКА! Не удалось найти пользователя по логину в БД.");
            return null;
        }
    }

    /**
     * Сохранение в БД поискового запроса текущего в сессии пользователя.
     * @param session сессия
     * @param searchObject предмет поиска пользователя
     * @param request поисковый запрос
     */
    public void saveStorySearch(HttpSession session, String searchObject, String request) {
        User user = (User) session.getAttribute("user");
        StorySearch storySearch = new StorySearch(null, searchObject, request, new Date(), user);
        user.addStorySearch(storySearch);
        try {
            storySearchService.save(storySearch);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ОШИБКА! Не удалось сохранить запрос в историю поиска в БД.");
        }
    }

    /**
     * Сохранение в БД скачанной книги текущего в сессии пользователя.
     * @param session сессия
     * @param bookId id скачанной книги
     * @param title название скачанной книги
     * @param bookLink ссылка на скачанную книгу
     */
    public void saveStoryDownload(HttpSession session, Long bookId, String title, String bookLink) {
        User user = (User) session.getAttribute("user");
        StoryDownload storyDownload = new StoryDownload(null, bookId, title, bookLink, new Date(), user);
        user.addStoryDownload(storyDownload);
        try {
            storyDownloadService.save(storyDownload);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ОШИБКА! Не удалось сохранить книгу в раздел скачанных книг в БД.");
        }
    }

    /**
     * Сохранение в БД избранной книги текущего в сессии пользователя
     * @param session сессия
     * @param bookId id книги
     * @param bookLink ссылка на книгу
     * @param title название книги
     * @param library библиотека, в которой эта книга находится
     * @return ответ, успешное или не успешное сохранение книги
     */
    public Boolean saveFavorites(HttpSession session, Long bookId, String bookLink, String title, String library) {
        User user = (User) session.getAttribute("user");
        Favorites favorites = new Favorites(null, bookId, library, title, bookLink, new Date(), user);
        user.addFavorites(favorites);
        session.setAttribute("user", user);
        try {
            favoritesService.saveFavorites(favorites);
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ОШИБКА! Не удалось сохранить книгу в раздел избранных книг в БД.");
            return Boolean.FALSE;
        }
    }

    /**
     * Удаление из БД избранной книги текущего в сессии пользователя
     * @param session сессия
     * @param bookId id книги
     * @param bookTitle название книги
     * @param bookLink ссылка на книгу
     * @param library библиотека, в которой эта книга находится
     * @return ответ, успешное или не успешное сохранение книги
     */
    public Boolean deleteFavorites(HttpSession session, Long bookId, String bookTitle, String bookLink, String library) {
        User user = (User) session.getAttribute("user");
        Set<Favorites> favoritesBooks = user.getFavorites();
        Favorites favoriteBook = favoritesBooks.stream().filter(
                book -> book.getBookId().equals(bookId)
                && book.getBookTitle().equals(bookTitle)
                && book.getBookLink().equals(bookLink)
                && book.getLibrary().equals(library)
        ).findFirst().orElse(null);
        System.out.println(favoriteBook);
        System.out.println(favoritesBooks.remove(favoriteBook));
        user.setFavorites(favoritesBooks);
        session.setAttribute("user", user);
        try {
            favoritesService.deleteFavorites(favoriteBook);
            return Boolean.FALSE;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ОШИБКА! Не удалось удалить книгу из раздела избранных книг из БД.");
            return Boolean.TRUE;
        }
    }

    /**
     * Получить все избранные книги для данного пользователя.
     * @param user пользователь
     * @return множество Set избранных книг
     */
    public Set<Favorites> getFavorites(User user) {
        try {
            return favoritesService.findByUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ОШИБКА! Не удалось получить список книг из раздела \"Избранное\".");
            return null;
        }
    }

    /**
     * Получить все поисковые запросы для данного пользователя.
     * @param user пользователь
     * @return множество Set поисковых запросов
     */
    public Set<StorySearch> getStorySearch(User user) {
        try {
            return storySearchService.findByUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ОШИБКА! Не удалось получить список запросов из раздела \"История поиска\".");
            return null;
        }
    }

    /**
     * Получить все скачанные книги для данного пользователя.
     * @param user пользователь
     * @return множество Set скачанных книг
     */
    public Set<StoryDownload> getStoryDownload(User user) {
        try {
            return storyDownloadService.findByUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ОШИБКА! Не удалось получить список книг из раздела \"Скачанные книги\".");
            return null;
        }
    }


}

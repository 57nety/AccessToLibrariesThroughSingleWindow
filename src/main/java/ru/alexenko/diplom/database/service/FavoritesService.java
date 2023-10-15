package ru.alexenko.diplom.database.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexenko.diplom.database.entity.Favorites;
import ru.alexenko.diplom.database.entity.User;
import ru.alexenko.diplom.database.repository.FavoritesRepository;

import java.util.Set;

/**
 * Класс FavoritesService реализует связь сущности Favorites с БД.
 */
@RequiredArgsConstructor
@Service
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;


    /**
     * Получить все избранные книги по заданному пользователю
     * @param user заданный пользователь
     * @return множество Set избранных книг
     */
    public Set<Favorites> findByUser(User user) {
        return favoritesRepository.findByUser(user);
    }

    /**
     * Метод реализует сохранение избранной книги в БД.
     * @param favorites избранная книга
     * @return книга, которую сохранили, иначе null
     */
    public Favorites saveFavorites(Favorites favorites) {
        return favoritesRepository.save(favorites);
    }

    /**
     * Удалить определённую избранную книгу из БД.
     * @param favorites книга, которую надо удалить
     */
    public void deleteFavorites(Favorites favorites) {
        favoritesRepository.delete(favorites);
    }
}

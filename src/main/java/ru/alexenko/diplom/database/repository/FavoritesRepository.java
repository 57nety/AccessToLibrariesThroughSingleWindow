package ru.alexenko.diplom.database.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.alexenko.diplom.database.entity.Favorites;
import ru.alexenko.diplom.database.entity.User;

import java.util.Set;

/**
 * Репозиторий, связывающий сущность Favorites с БД.
 */
@Repository
public interface FavoritesRepository extends CrudRepository<Favorites, Long> {

    /**
     * Получить все избранные книги по заданному пользователю
     * @param user заданный пользователь
     * @return множество Set избранных книг
     */
    Set<Favorites> findByUser(User user);
}

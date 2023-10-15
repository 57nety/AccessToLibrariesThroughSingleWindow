package ru.alexenko.diplom.database.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.alexenko.diplom.database.entity.StorySearch;
import ru.alexenko.diplom.database.entity.User;

import java.util.Set;

/**
 * Репозиторий, связывающий сущность StorySearch с БД.
 */
@Repository
public interface StorySearchRepository extends CrudRepository<StorySearch, Long> {

    /**
     * Получить всю историю поиска пользователю
     * @param user заданный пользователь
     * @return множество Set запросов пользователя
     */
    Set<StorySearch> findByUser(User user);
}

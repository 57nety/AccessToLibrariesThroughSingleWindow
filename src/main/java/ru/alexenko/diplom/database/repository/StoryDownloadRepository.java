package ru.alexenko.diplom.database.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.alexenko.diplom.database.entity.StoryDownload;
import ru.alexenko.diplom.database.entity.User;

import java.util.Set;

/**
 * Репозиторий, связывающий сущность StoryDownload с БД.
 */
@Repository
public interface StoryDownloadRepository extends CrudRepository<StoryDownload, Long> {

    /**
     * Получить все скачанные книги по заданному пользователю
     * @param user заданный пользователь
     * @return множество Set скачанных книг
     */
    Set<StoryDownload> findByUser(User user);
}

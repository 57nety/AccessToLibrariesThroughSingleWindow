package ru.alexenko.diplom.database.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexenko.diplom.database.entity.StoryDownload;
import ru.alexenko.diplom.database.entity.User;
import ru.alexenko.diplom.database.repository.StoryDownloadRepository;

import java.util.Set;

/**
 * Класс StoryDownloadService реализует связь сущности StoryDownload с БД.
 */
@RequiredArgsConstructor
@Service
public class StoryDownloadService {

    private final StoryDownloadRepository storyDownloadRepository;

    /**
     * Получить все скачанные книги по заданному пользователю
     * @param user заданный пользователь
     * @return множество Set скачанных книг
     */
    public Set<StoryDownload> findByUser(User user) {
        return storyDownloadRepository.findByUser(user);
    }

    /**
     * Метод реализует сохранение скачанной книги в БД.
     * @param storyDownload скачанная книге
     * @return скачанную книгу, если сохранилось в БД, иначе null
     */
    public StoryDownload save(StoryDownload storyDownload) {
        return storyDownloadRepository.save(storyDownload);
    }
}

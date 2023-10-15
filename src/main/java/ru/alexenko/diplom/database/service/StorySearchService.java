package ru.alexenko.diplom.database.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexenko.diplom.database.entity.StorySearch;
import ru.alexenko.diplom.database.entity.User;
import ru.alexenko.diplom.database.repository.StorySearchRepository;

import java.util.Set;

/**
 * Класс StorySearchService реализует связь сущности StorySearch с БД.
 */
@RequiredArgsConstructor
@Service
public class StorySearchService {

    private final StorySearchRepository storySearchRepository;

    /**
     * Получить все запросы из поиска пользователю
     * @param user заданный пользователь
     * @return множество Set поисковых запросов пользователя
     */
    public Set<StorySearch> findByUser(User user) {
        return storySearchRepository.findByUser(user);
    }

    /**
     * Сохранение поискового запроса в БД.
     * @param storySearch поисковый запрос
     * @return поисковый запрос, если сохранение успешно, иначе null
     */
    public StorySearch save(StorySearch storySearch) {
        return storySearchRepository.save(storySearch);
    }

}

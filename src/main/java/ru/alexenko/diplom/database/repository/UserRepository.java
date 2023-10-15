package ru.alexenko.diplom.database.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.alexenko.diplom.database.entity.User;

/**
 * Репозиторий, связывающий сущность User с БД.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     *  Поиск пользователя в БД по заданному логину.
     * @param login заданный логин
     * @return пользователь с заданным логином
     */
    User findByLogin(String login);
}

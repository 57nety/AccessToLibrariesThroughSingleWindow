package ru.alexenko.diplom.web.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexenko.diplom.database.entity.User;
import ru.alexenko.diplom.database.service.UserService;
import ru.alexenko.diplom.lib.elibaltstu.service.ElibAltstuApiService;

import java.util.HashSet;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserService userService;

    private final ElibAltstuApiService elibAltstuApiService;

    public User authenticateUser(String login, String password) {
        if (elibAltstuApiService.authenticateUser(login, password) == Boolean.TRUE) {
            User user = userService.findByLogin(login);
            if (user != null) {
                return user;
            }
            user = new User(null, login, new HashSet<>(), new HashSet<>(), new HashSet<>());
            userService.saveUser(user);
            return user;
        }
        return null;
    }

    public Boolean logoutFromProfile(HttpSession session) {
        if (elibAltstuApiService.logoutFromProfile()) {
            session.setAttribute("user", null);
            return true;
        }
        return false;
    }
}

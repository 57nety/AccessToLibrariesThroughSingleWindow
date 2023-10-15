package ru.alexenko.diplom.web.secutiry;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ru.alexenko.diplom.database.entity.User;

/**
 * Класс CustomInterceptor используется для перехвата запроса.
 */
@Service
public class CustomInterceptor implements HandlerInterceptor {

    /**
     * Данный метод реализует доступ пользователя к endpoint приложения.
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return true - можно перейти к endpoint, false - нельзя перейти к endpoint
     * @throws Exception пробрасываемое исключение
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            if (requestURI.equals(request.getContextPath() + "/login")) {
                return true;
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
        } else if (requestURI.equals(request.getContextPath() + "/login")) {
            response.sendRedirect(request.getContextPath() + "/books/preview");
            return false;
        }
        return true;
    }
}
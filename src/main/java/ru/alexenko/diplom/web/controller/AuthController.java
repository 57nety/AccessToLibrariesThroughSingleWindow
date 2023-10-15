package ru.alexenko.diplom.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alexenko.diplom.database.entity.User;
import ru.alexenko.diplom.web.service.AuthService;

/**
 * Контроллер отвечающий за авторизацию пользователя в приложении.
 */
@RequiredArgsConstructor
@Controller
class AuthController {

    private final AuthService authService;

    /**
     * Принимает запросы на форму входа.
     * @return строковое представление
     */
    @GetMapping("/login")
    public String loginForm() {
        return "/login/loginForm";
    }

    @PostMapping("/login")
    public String authenticateUser(@RequestParam String login, @RequestParam String password, Model model, HttpSession session) {
        User user = authService.authenticateUser(login, password);

        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/books/preview";
        } else {
            model.addAttribute("error", "Неверные учетные данные");
            return "/login/loginForm";
        }
    }

    @GetMapping("/logout")
    public String logoutUser(HttpServletRequest request, HttpSession session) {

        if (authService.logoutFromProfile(session)) {
            return "redirect:/login";
        }

        String referer = request.getHeader("referer");

        if (referer != null && !referer.isEmpty()) {
            return "redirect:" + referer;
        }

        return "/errors/error";
    }
}

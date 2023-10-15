package ru.alexenko.diplom.web.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alexenko.diplom.database.entity.Favorites;
import ru.alexenko.diplom.database.entity.StoryDownload;
import ru.alexenko.diplom.database.entity.StorySearch;
import ru.alexenko.diplom.database.entity.User;
import ru.alexenko.diplom.database.service.UserService;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/profile")
@Controller
public class ProfileController {

    private final UserService userService;

    @GetMapping
    public String profileForm(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");
        model.addAttribute("login", user.getLogin());

        return "/profile/profileForm";
    }

    @GetMapping("/storysearch")
    public String storySearchForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("login", user.getLogin());
        Set<StorySearch> storySearch = userService.getStorySearch(user).stream()
                .sorted(Comparator.comparing(StorySearch::getDate).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        model.addAttribute("storySearch", storySearch);

        return "/profile/storySearchForm";
    }

    @GetMapping("/storydownload")
    public String storyDownloadForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("login", user.getLogin());
        Set<StoryDownload> storyDownloads = userService.getStoryDownload(user).stream()
                .sorted(Comparator.comparing(StoryDownload::getDate).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        model.addAttribute("storyDownload", storyDownloads);

        return "/profile/storyDownloadForm";
    }

    @GetMapping("/favorites")
    public String favoritesForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("login", user.getLogin());
        Set<Favorites> favorites = userService.getFavorites(user).stream()
                .sorted(Comparator.comparing(Favorites::getDate).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        model.addAttribute("favorites", favorites);

        return "/profile/favoritesForm";
    }
}

package ru.alexenko.diplom.web.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alexenko.diplom.database.service.UserService;
import ru.alexenko.diplom.lib.elibaltstu.model.book.ElibAltstuBook;
import ru.alexenko.diplom.lib.elibaltstu.model.discipline.ElibAltstuDiscipline;
import ru.alexenko.diplom.lib.iprsmart.model.book.IprSmartBookInDiscipline;
import ru.alexenko.diplom.lib.iprsmart.model.discipline.IprSmartDiscipline;
import ru.alexenko.diplom.web.service.DisciplineService;

import java.util.List;

@Controller
@RequestMapping("/disciplines")
@RequiredArgsConstructor
public class DisciplineController {

    private final DisciplineService disciplineService;

    private final UserService userService;

    @GetMapping("/preview")
    public String previewDisciplinesForm(Model model) {
        List<ElibAltstuDiscipline> elibAltstuDisciplines = disciplineService.getElibAltstuApiService().getPreviewDisciplines("");
        if (elibAltstuDisciplines == null) {
            return "errors/error";
        }
        model.addAttribute("elibAltstuDisciplines", elibAltstuDisciplines);
        model.addAttribute("iprSmartDisciplines", null);

        return "disciplines/preview/previewDisciplinesForm";
    }

    @GetMapping("/all")
    public String allDisciplinesForm(Model model) {
        List<ElibAltstuDiscipline> elibAltstuAllDisciplines = disciplineService.getElibAltstuApiService().getAllDisciplines("");
        if (elibAltstuAllDisciplines == null) {
            return "errors/error";
        }
        model.addAttribute("elibAltstuDisciplines", elibAltstuAllDisciplines);
        model.addAttribute("iprSmartDisciplines", null);

        return "disciplines/all/allDisciplinesForm";
    }

    @GetMapping("/search")
    public String searchDisciplinesForm(@RequestParam("query") String query, Model model, HttpSession session) {
        userService.saveStorySearch(session, "Дисциплины", query);
        model.addAttribute("lastQuery", query);
        List<ElibAltstuDiscipline> elibAltstuFoundDisciplines = disciplineService.getElibAltstuApiService().searchDisciplines(query);
        List<IprSmartDiscipline> iprSmartFoundDisciplines = disciplineService.getIprSmartApiService().searchDisciplines(query);
        if (elibAltstuFoundDisciplines == null || iprSmartFoundDisciplines == null) {
            return "errors/error";
        }
        model.addAttribute("elibAltstuDisciplines", elibAltstuFoundDisciplines);
        model.addAttribute("iprSmartDisciplines", iprSmartFoundDisciplines);

        return "disciplines/search/searchDiscipline";
    }

    @GetMapping("/altstu/books")
    public String altstuAllBooksInDiscipline(@RequestParam("discipline") String jsonDiscipline, Model model) {
        ElibAltstuDiscipline discipline = disciplineService.fromJsonToElibAltstuDiscipline(jsonDiscipline);
        if (discipline == null) {
            return "errors/error";
        }
        model.addAttribute("disciplineName", discipline.getName());
        List<ElibAltstuBook> elibAltstuBooksInDiscipline = disciplineService.getElibAltstuApiService().getBooksInDiscipline(discipline.getId());
        if (elibAltstuBooksInDiscipline == null) {
            return "errors/error";
        }
        model.addAttribute("books", elibAltstuBooksInDiscipline);

        return "disciplines/booksindiscipline/altstuAllBooksInDiscipline";
    }

    @GetMapping("/iprsmart/books")
    public String iprSmartAllBooksInDiscipline(@RequestParam("discipline") String jsonDiscipline, Model model) {
        IprSmartDiscipline discipline = disciplineService.fromJsonToIprSmartDiscipline(jsonDiscipline);
        if (discipline == null) {
            return "errors/error";
        }
        model.addAttribute("disciplineName", discipline.getName());
        List<IprSmartBookInDiscipline> iprSmartBooksInDiscipline = disciplineService.getIprSmartApiService().getBooksInDiscipline(discipline.getId());
        if (iprSmartBooksInDiscipline == null) {
            return "errors/error";
        }
        model.addAttribute("books", iprSmartBooksInDiscipline);

        return "disciplines/booksindiscipline/iprSmartAllBooksInDiscipline";
    }
}

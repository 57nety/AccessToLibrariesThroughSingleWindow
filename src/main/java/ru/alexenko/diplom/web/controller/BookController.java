package ru.alexenko.diplom.web.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alexenko.diplom.database.entity.Favorites;
import ru.alexenko.diplom.database.entity.StoryDownload;
import ru.alexenko.diplom.database.entity.User;
import ru.alexenko.diplom.database.service.UserService;
import ru.alexenko.diplom.lib.elibaltstu.model.book.ElibAltstuBook;
import ru.alexenko.diplom.lib.iprsmart.model.book.IprSmartBook;
import ru.alexenko.diplom.web.service.BookService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final UserService userService;

    private static final String ELIB_ALTSTU = "ЭБС АлтГТУ";
    private static final String IPR_SMART = "IPR SMART";

    @GetMapping("/preview")
    public String previewBooksForm(Model model) {
        List<ElibAltstuBook> elibAltstuBooksPreview = bookService.getElibAltstuApiService().getPreviewBooks();
        List<IprSmartBook> iprSmartBooksPreview = bookService.getIprSmartApiService().getPreviewBooks();
        if (elibAltstuBooksPreview == null || iprSmartBooksPreview == null) {
            return "errors/error";
        }
        model.addAttribute("elibAltstuBooks", elibAltstuBooksPreview);
        model.addAttribute("iprSmartBooks", iprSmartBooksPreview);

        return "books/preview/previewBooksForm";
    }

    @GetMapping("/all")
    public String allBooksForm(Model model) {
        List<ElibAltstuBook> elibAltstuBooks = bookService.getElibAltstuApiService().getAllBooks("");
        List<IprSmartBook> iprSmartBooks = bookService.getIprSmartApiService().getAllBooks();
        if (elibAltstuBooks == null || iprSmartBooks == null) {
            return "errors/error";
        }
        model.addAttribute("elibAltstuBooks", elibAltstuBooks);
        model.addAttribute("iprSmartBooks", iprSmartBooks);

        return "books/all/allBooksForm";
    }

    @GetMapping("/search")
    public String searchBooksForm(@RequestParam("query") String query, Model model, HttpSession session) {
        userService.saveStorySearch(session, "Книги", query);
        model.addAttribute("lastQuery", query);
        List<ElibAltstuBook> elibAltstuFoundBooks = bookService.getElibAltstuApiService().searchBooks(query);
        List<IprSmartBook> iprSmartFoundBooks = bookService.getIprSmartApiService().searchBooks(query);
        if (elibAltstuFoundBooks == null || iprSmartFoundBooks == null) {
            return "errors/error";
        }
        model.addAttribute("elibAltstuBooks", elibAltstuFoundBooks);
        model.addAttribute("iprSmartBooks", iprSmartFoundBooks);

        return "books/search/searchBooksForm";
    }

    @GetMapping("/altstu/aboutbook")
    public String altstuAboutBookForm(@RequestParam("book") String jsonBook, Model model, HttpSession session) {
        ElibAltstuBook book = bookService.fromJsonToElibAltstuBook(jsonBook);
        if (book == null) {
            return "errors/error";
        }
        User user = (User) session.getAttribute("user");
        model.addAttribute("book", book);

        return "books/about/elibAltstuAboutBook";
    }

    @GetMapping("/iprsmart/aboutbook")
    public String iprSmartAboutBookForm(@RequestParam("book") String jsonBook, Model model, HttpSession session) {
        IprSmartBook book = bookService.fromJsonToIprSmartBook(jsonBook);
        if (book == null) {
            return "errors/error";
        }
        User user = (User) session.getAttribute("user");
        Boolean isFavorite = user.getFavorites().stream().anyMatch(favorite -> Objects.equals(favorite.getBookId(), book.getId()));
        model.addAttribute("isFavorite", isFavorite);
        model.addAttribute("book", book);

        return "books/about/iprSmartAboutBook";
    }

    @GetMapping("/altstu/aboutfavoritebook")
    public String altstuAboutFavoriteBookForm(@RequestParam("favoriteBook") String jsonBook, Model model) {
        Favorites favoriteBook = bookService.fromJsonToElibAltstuFavoriteBook(jsonBook);
        ElibAltstuBook book = bookService.getElibAltstuApiService().getBook(favoriteBook.getBookId(), favoriteBook.getBookTitle());
        if (book == null) {
            return "errors/error";
        }
        model.addAttribute("isFavorite", true);
        model.addAttribute("book", book);

        return "books/about/elibAltstuAboutBook";
    }

    @GetMapping("/altstu/aboutdownloadbook")
    public String altstuAboutDownloadBookForm(@RequestParam("downloadBook") String jsonBook, Model model) {
        StoryDownload downloadBook = bookService.fromJsonToElibAltstuDownloadBook(jsonBook);
        ElibAltstuBook book = bookService.getElibAltstuApiService().getBook(downloadBook.getBookId(), downloadBook.getBookTitle());
        if (book == null) {
            return "errors/error";
        }
        model.addAttribute("isFavorite", true);
        model.addAttribute("book", book);

        return "books/about/elibAltstuAboutBook";
    }

    @GetMapping("/iprsmart/aboutfavoritebook")
    public String iprSmartAboutFavoriteBookForm(@RequestParam("favoriteBook") String jsonBook, Model model) {
        Favorites favoriteBook = bookService.fromJsonToElibAltstuFavoriteBook(jsonBook);
        if (favoriteBook == null) {
            return "errors/error";
        }
        IprSmartBook book = bookService.getIprSmartApiService().getBook(favoriteBook.getBookId());
        model.addAttribute("isFavorite", true);
        model.addAttribute("book", book);

        return "books/about/iprSmartAboutBook";
    }

    @GetMapping("/download")
    public void downloadPDF(@RequestParam("book") String jsonBook, HttpSession session, HttpServletResponse response) throws IOException {
        ElibAltstuBook book = bookService.fromJsonToElibAltstuBook(jsonBook);
        if (book == null) {
            return;
        }
        userService.saveStoryDownload(session, book.getId(), book.getTitle(), book.getUrl());
        bookService.getElibAltstuApiService().downloadBook(book.getId(),response);
    }

    @GetMapping("/altstu/addfavoritebook")
    public String elibAltstuAddFavoriteBookForm(@RequestParam("book") String jsonBook, HttpSession session, Model model) {
        ElibAltstuBook book = bookService.fromJsonToElibAltstuBook(jsonBook);
        if (book == null) {
            return "errors/error";
        }
        Boolean isFavorite = userService.saveFavorites(session, book.getId(), book.getUrl(), book.getTitle(), ELIB_ALTSTU);
        model.addAttribute("isFavorite", isFavorite);
        model.addAttribute("book", book);

        return "books/about/elibAltstuAboutBook";
    }

    @GetMapping("/iprsmart/addfavoritebook")
    public String iprSmartAddFavoriteBookForm(@RequestParam("book") String jsonBook, HttpSession session, Model model) {
        IprSmartBook book = bookService.fromJsonToIprSmartBook(jsonBook);
        if (book == null) {
            return "errors/error";
        }
        Boolean isFavorite = userService.saveFavorites(session, book.getId(), book.getUrl(), book.getTitle(), IPR_SMART);
        model.addAttribute("isFavorite", isFavorite);
        model.addAttribute("book", book);

        return "books/about/iprSmartAboutBook";
    }

    @GetMapping("/altstu/deletefavoritebook")
    public String elibAltstuDeleteFavoriteBookForm(@RequestParam("book") String jsonBook, HttpSession session, Model model) {
        ElibAltstuBook book = bookService.fromJsonToElibAltstuBook(jsonBook);
        if (book == null) {
            return "errors/error";
        }
        Boolean isFavorite = userService.deleteFavorites(session, book.getId(), book.getTitle(), book.getUrl(), ELIB_ALTSTU);
        model.addAttribute("isFavorite", isFavorite);
        model.addAttribute("book", book);

        return "books/about/elibAltstuAboutBook";
    }

    @GetMapping("/iprsmart/deletefavoritebook")
    public String iprSmartDeleteFavoriteBookForm(@RequestParam("book") String jsonBook, HttpSession session, Model model) {
        IprSmartBook book = bookService.fromJsonToIprSmartBook(jsonBook);
        if (book == null) {
            return "errors/error";
        }
        Boolean isFavorite = userService.deleteFavorites(session, book.getId(), book.getTitle(), book.getUrl(), IPR_SMART);
        model.addAttribute("isFavorite", isFavorite);
        model.addAttribute("book", book);

        return "books/about/iprSmartAboutBook";
    }
}

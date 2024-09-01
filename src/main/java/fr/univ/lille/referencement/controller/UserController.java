package fr.univ.lille.referencement.controller;
import java.util.List;
import fr.univ.lille.referencement.service.ArticlesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import fr.univ.lille.referencement.dao.UserDaoJPAImpl;
import fr.univ.lille.referencement.model.Article;
import fr.univ.lille.referencement.model.User;
import fr.univ.lille.referencement.service.ChiffrementService;
import fr.univ.lille.referencement.utils.Constants;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * The controller for the user pages.
 */
@Controller
@RequestMapping(path = "/")
class UserController {

    private static final String USERNAME = "username";

    /**
     * The user DAO implementation.
     */
    @Autowired
    private UserDaoJPAImpl userDao;

    /**
     * The encryption service.
     */
    @Autowired
    private ChiffrementService chiffrementService;

    /**
     * the article service.
     */
    @Autowired
    private ArticlesServices articlesServices;

    /**
     * The connection page.
     * @return the view name.
     */
    @GetMapping(path = "/")
    public String home() {
        return "login";
    }

    /**
     * The home page.
     * @param model the model.
     * @param session the session.
     * @return the view name.
     * @throws Exception if an error occurs.
     */
    @GetMapping(path = "/home")
    public String userInfo(Model model, HttpSession session, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10")int pagination, @RequestParam(value = "category", defaultValue = "") String category, @RequestParam(value = "productName", defaultValue = "") String prodName, @RequestParam(value = "productReference", defaultValue = "") String prodRef) {
        String username = (String) session.getAttribute(Constants.USERNAME);
        if (username == null) {
            return "redirect:/";
        }

        if(category.equals("")){
            category = null;
        } else if(prodName.equals("")){
            prodName = null;
        } else if(prodRef.equals("")){
            prodRef = null;
        }

        List<Article> allArticles = articlesServices.getAllNotPerishedArticles(category, prodName, prodRef);

        model.addAttribute("allArticles", articlesServices.getPagedArticles(page, pagination, allArticles));
        model.addAttribute("list", articlesServices.getPages(pagination, allArticles.size()));
        model.addAttribute("categories", articlesServices.getAllCategoriesNames());
        return "home";
    }

    /**
     * The sign-out page.
     * @param session the session.
     * @return the view name.
     */
    @GetMapping(path = "/signOut")
    public String signOut(HttpSession session) {
        session.removeAttribute(USERNAME);
        return "redirect:/";
    }

    /**
     * The sign-in page.
     * @param model the model.
     * @param session the session.
     * @param response the response.
     * @param username the username.
     * @param password the password.
     * @return the view name.
     */
    @PostMapping(path = "/login")
    public String processSignIn(Model model, HttpSession session, HttpServletResponse response, String username, String password) {
        User user = null;
        try {
            user = userDao.getUserByEmailAndPassword(username, chiffrementService.chiffrer(password));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            model.addAttribute("errorMessage", "User or Password is incorrect");
            return "error";
        }
        session.setAttribute(USERNAME, user.getEmail());
        session.setAttribute("user",user);
        return "redirect:/home";
    }
}
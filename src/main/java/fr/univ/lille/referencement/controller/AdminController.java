package fr.univ.lille.referencement.controller;

import java.io.IOException;
import java.util.List;
import java.io.File;

import fr.univ.lille.referencement.dao.*;
import fr.univ.lille.referencement.service.ArticlesServices;
import fr.univ.lille.referencement.utils.Constants;
import fr.univ.lille.referencement.utils.ExceptionArticle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import fr.univ.lille.referencement.model.Article;
import fr.univ.lille.referencement.model.Lot;
import fr.univ.lille.referencement.model.Perishable;
import fr.univ.lille.referencement.model.Imperishable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

/**
 * The controller for the user pages.
 */
@Controller
@RequestMapping(path = "/")
public class AdminController {
    /**
     * The default picture for the articles.
     */
    private static final String DEFAULT_PICTURE = "default.png";

    /**
     * The article DAO.
     */
    @Autowired
    private ArticleDao articleDao;

    /**
     * the imperishable article DAO implementation.
     */
    @Autowired
    private ImperishableDaoImpl iDao;

    /**
     * the perishable article DAO implementation.
     */
    @Autowired
    private PerishableDaoImpl pDao;

    /**
     * the lot DAO.
     */
    @Autowired
    LotDao lotDao;

    /**
     * the article service.
     */
    @Autowired
    private ArticlesServices articlesServices;

    /**
     * The upload directory.
     */
    @Value(value = "${upload.dir}")
    String uploadDir;

    /**
     * Metod get for the home page for the manager or employee.
     * @param session the session.
     * @return the view name.
     */
    @GetMapping(path = "/adminpanel/**")
    public String adminpanel(HttpSession session) {
        String username = (String) session.getAttribute(Constants.USERNAME);
        if (username.equals("employee") || username.equals(Constants.ADMIN)) {
            return "administration";
        } else {
            return Constants.REDIRECT_HOME;
        }
    }
    /**
     * Method get the product page for the manager.
     * @param model the model.
     * @return the view name.
     */
    @GetMapping(path = "/adminpanel/produitmanager/**")
    public String produitmanager(Model model,HttpSession session, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10")int pagination, @RequestParam(value = "category", defaultValue = "") String category, @RequestParam(value = "productName", defaultValue = "") String prodName, @RequestParam(value = "productReference", defaultValue = "") String prodRef) {
        String username = (String) session.getAttribute(Constants.USERNAME);
        if(category.equals("")){
            category = null;
        } else if(prodName.equals("")){
            prodName = null;
        } else if(prodRef.equals("")){
            prodRef = null;
        }
        if (username.equals(Constants.ADMIN)) {
            List<Article> articles = articlesServices.getAllArticles(category, prodName, prodRef);

            model.addAttribute("allArticles", articlesServices.getPagedArticles(page, pagination, articles));
            model.addAttribute("list", articlesServices.getPages(pagination, articles.size()));
            model.addAttribute("categories", articlesServices.getAllCategoriesNames());
            return "produitmanager";
        } else {
            return "redirect:/adminpanel";
        }
    }

    /**
     * Method get the stock page for the manager.
     * @param model the model.
     * @return the view name.
     */
    @GetMapping(path = "/adminpanel/stockmanager/**")
    public String stockmanager(Model model,HttpSession session, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10")int pagination, @RequestParam(value = "category", defaultValue = "") String category, @RequestParam(value = "productName", defaultValue = "") String prodName, @RequestParam(value = "productReference", defaultValue = "") String prodRef) {
        String username = (String) session.getAttribute(Constants.USERNAME);
        if(category.equals("")){
            category = null;
        } else if(prodName.equals("")){
            prodName = null;
        } else if(prodRef.equals("")){
            prodRef = null;
        }
        if (username.equals(Constants.ADMIN)) {
            List<Article> articles = articlesServices.getAllArticles(category, prodName, prodRef);

            model.addAttribute("allArticles", articlesServices.getPagedArticles(page, pagination, articles));
            model.addAttribute("list", articlesServices.getPages(pagination, articles.size()));
            model.addAttribute("categories", articlesServices.getAllCategoriesNames());
            return "stockmanager";
        } else {
            return "redirect:/adminpanel";
        }
    }

    /**
     * Method get to modify a lot.
     * @param id the id of the lot.
     * @param model the model.
     * @param session the session.
     * @return the view name.
     */
    @GetMapping(path = "/adminpanel/stockmanager/lot/{id}")
    public String modifLot(@PathVariable("id") String id, Model model, HttpSession session ) {
        String username = (String) session.getAttribute(Constants.USERNAME);
        if (username.equals(Constants.ADMIN)) {
            Lot lot = lotDao.getLot(Integer.parseInt(id));
            Perishable perishable = lot.getPerishable();
            model.addAttribute(Constants.ARTICLE, perishable);
            model.addAttribute("lot", lot);
            return "detailLot";
        }
        else {
            return Constants.REDIRECT_HOME;
        }
    }

    /**
     * Method post to modify a lot.
     * @param id the id of the lot.
     * @param quantity the quantity of the lot.
     * @param date the date of the lot.
     * @param model the model.
     * @param idArticle the id of the article.
     * @param session the session.
     * @return the view name.
     */
    @PostMapping(path = "/adminpanel/stockmanager/lot/{id}")
    public String modifLotPost(@PathVariable("id") int id, @RequestParam("quantity") int quantity, @RequestParam("date") String date, Model model, @RequestParam("idArticle") String idArticle,HttpSession session) {
        String username = (String) session.getAttribute(Constants.USERNAME);
        if (username.equals(Constants.ADMIN)) {
            lotDao.updateLot(id, quantity, Lot.getFormattedDate(date));
            Lot lot = lotDao.getLot(id);
            model.addAttribute("lot", lot);
            return Constants.REDIRECT_STOCK + idArticle + Constants.PERISHABLE;
        }
        else {
            return Constants.REDIRECT_HOME;
        }
    }

    /**
     * Method post to delete an article.
     * @param id the id of the lot.
     * @return the view name.
     */
    @PostMapping(path = "/adminpanel/stockmanager/lot/{id}/delete")
    public String deleteLot(@PathVariable("id") int id) {
        Lot lot = lotDao.deleteLot(id);
        return Constants.REDIRECT_STOCK + lot.getPerishable().getId() + Constants.PERISHABLE;
    }

    /**
     * Method get to add a perishable article.
     * @param session the session.
     * @return the view name.
     */
    @GetMapping(path = "/adminpanel/produitmanager/addproduitp")
    public String addproduitP(HttpSession session) {
        String username = (String) session.getAttribute(Constants.USERNAME);
        if (username.equals(Constants.ADMIN)) {
            return "ajoutPerishable";
        } else {
            return Constants.REDIRECT_HOME;
        }
    }

    /**
     * Method post to add an article.
     * @param request the request.
     * @param file the file.
     * @param name the name of the article.
     * @param quantity the quantity of the article.
     * @param price the price of the article.
     * @param session the session.
     * @return the view name.
     * @throws IOException if an error occurs.
     */
    @PostMapping(path = "/adminpanel/produitmanager/addproduit")
    public String add(HttpServletRequest request, @RequestParam("file") MultipartFile file,
                      @RequestParam("name") String name, @RequestParam("quantity") int quantity,
                      @RequestParam("price") double price, @RequestParam(value = "description", defaultValue = "") String description, HttpSession session) throws IOException {
        String username = (String) session.getAttribute(Constants.USERNAME);
        if (username.equals(Constants.ADMIN)) {
            String orgName = addalltypeproduit(request, file);
            Imperishable i = new Imperishable(name, price, Constants.UPLOADS + orgName, quantity);
            if (!description.isEmpty())
                i.setDescription(description);
            iDao.addImperishable(i);
            return Constants.REDIRECT_PRODUITMANAGER;
        } else {
            return Constants.REDIRECT_HOME;
        }
    }

    /**
     * Add all article types.
     * @param request the request.
     * @param file the file.
     * @return the name of the file.
     * @throws IOException if an error occurs.
     */
    private String addalltypeproduit(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        String uploadsDir = Constants.UPLOADS;
        String realPathtoUploads = request.getServletContext().getRealPath(uploadsDir);
        String orgName;

        if (file.isEmpty()) {
            orgName = DEFAULT_PICTURE;

        } else {
            orgName = file.getOriginalFilename();
            String filePath = realPathtoUploads + orgName;
            File dest = new File(filePath);
            file.transferTo(dest);
        }
        return orgName;
    }

    /**
     * Methode get to add a lot.
     * @param model the model.
     * @param id the id of the article.
     * @param session the session.
     * @return the view name.
     */
    @GetMapping(path = "/adminpanel/stockmanager/addlot/{id}")
    public String addlot(Model model, @PathVariable("id") String id,HttpSession session) {
        String username = (String) session.getAttribute(Constants.USERNAME);
        if (username.equals(Constants.ADMIN)) {
            try {
                Perishable p = pDao.getPerishable(Integer.parseInt(id));
                model.addAttribute(Constants.ARTICLE, p);
            } catch (Exception e) {
                return "redirect:/error";
            }
            return "addStock";
        } else {
            return Constants.REDIRECT_HOME;
        }
    }

    /**
     * Method post to add a lot.
     * @param model the model.
     * @param id the id of the article.
     * @param quantity the quantity of the article.
     * @param date the date of the article.
     * @param session the session.
     * @return the view name.
     */
    @PostMapping(path = "/adminpanel/stockmanager/addlot/{id}")
    public String addlotPost(Model model, @PathVariable("id") String id, @RequestParam("quantity") int quantity, @RequestParam("date") String date,HttpSession session) {
        String username = (String) session.getAttribute(Constants.USERNAME);
        if (username.equals(Constants.ADMIN)) {
            Perishable p;
            try {
                p = pDao.getPerishable(Integer.parseInt(id));
                model.addAttribute(Constants.ARTICLE, p);
            } catch (Exception e) {
                return "redirect:/error";
            }
            Lot l = new Lot(quantity, date);
            pDao.addLot(p, l);
            return Constants.REDIRECT_STOCK + id + Constants.PERISHABLE;
        } else {
            return Constants.REDIRECT_HOME;
        }
    }

    /**
     * Method post to add a perishable article.
     * @param request the request.
     * @param file the file.
     * @param name the name of the article.
     * @param quantity the quantity of the article.
     * @param price the price of the article.
     * @param date the date of the article.
     * @param session the session.
     * @return the view name.
     * @throws ExceptionArticle if an error occurs.
     * @throws IOException if an error occurs.
     */
    @PostMapping(path = "/adminpanel/produitmanager/addproduitPerishable")
    public String add(HttpServletRequest request, @RequestParam("file") MultipartFile file,
                      @RequestParam("name") String name, @RequestParam("quantity") int quantity,
                      @RequestParam("price") double price, @RequestParam("date") String date, @RequestParam(value = "description", defaultValue = "") String description, HttpSession session)
            throws ExceptionArticle, IOException {
        String username = (String) session.getAttribute(Constants.USERNAME);
        if (username.equals(Constants.ADMIN)) {
            String orgName = addalltypeproduit(request, file);
            Lot l = new Lot(quantity, date);
            Perishable p = new Perishable(name, price, Constants.UPLOADS + orgName);
            if (!description.isEmpty())
                p.setDescription(description);
            pDao.addPerishable(p, l);
            return Constants.REDIRECT_PRODUITMANAGER;
        } else {
            return Constants.REDIRECT_HOME;
        }
    }

    /**
     * Method get to modify an article.
     * @param id the id of the article.
     * @param model the model.
     * @param session the session.
     * @return the view name.
     * @throws ExceptionArticle if an error occurs.
     */
    @GetMapping(path = "/adminpanel/produitmanager/article/{id}")
    public String article(@PathVariable("id") int id, Model model, HttpSession session) throws ExceptionArticle {
        String username = (String) session.getAttribute(Constants.USERNAME);
        if (username.equals(Constants.ADMIN)) {
            Article a = articleDao.getArticle(id);
            model.addAttribute(Constants.ARTICLE, a);
            return "modification";
        } else {
            return Constants.REDIRECT_HOME;
        }
    }

    /**
     * Method post to modify an article.
     * @param id the id of the article.
     * @param name the name of the article.
     * @param price the price of the article.
     * @param model the model.
     * @param session the session.
     * @return the view name.
     */
    @PostMapping(path = "/adminpanel/produitmanager/article/{id}")
    public String updateArticle(@PathVariable("id") int id, @RequestParam("name") String name,
                                @RequestParam("price") double price, @RequestParam(value = "description", defaultValue = "") String description, Model model,HttpSession session) {
        String username = (String) session.getAttribute(Constants.USERNAME);
        if (username.equals(Constants.ADMIN)) {
            Article u = articleDao.updateArticle(id, name, price, description);
            model.addAttribute(Constants.ARTICLE, u);
            return Constants.REDIRECT_PRODUITMANAGER;
        } else {
            return Constants.REDIRECT_HOME;
        }
    }

    /**
     * Method get to add an article.
     * @param session the session.
     * @return the view name.
     */
    @GetMapping(path = "/adminpanel/produitmanager/addproduit")
    public String addproduit(HttpSession session) {
        String username = (String) session.getAttribute(Constants.USERNAME);
        if (username.equals(Constants.ADMIN)) {
            return "ajout";
        } else {
            return Constants.REDIRECT_HOME;
        }
    }

    /**
     * Method get to modify the stock of an article.
     * @param id the id of the article.
     * @param model the model.
     * @param session the session.
     * @return the view name.
     * @throws ExceptionArticle if an error occurs.
     */
    @GetMapping(path = "/adminpanel/stockmanager/stock/{id}")
    public String modifStock(@PathVariable("id") int id, Model model, HttpSession session) throws ExceptionArticle {
        String username = (String) session.getAttribute(Constants.USERNAME);
        Article article = articleDao.getArticle(id);

        if (username.equals(Constants.ADMIN)) {
            if (Boolean.FALSE.equals(article.isPerishable())) {
                Imperishable i = (Imperishable) article;
                model.addAttribute(Constants.ARTICLE, i);
                return "modifStock";
            } else {

                Perishable p = (Perishable) article;
                List<Lot> l = p.getLot();
                model.addAttribute("allLot", l);
                model.addAttribute("idArticle", id);
                return "listLot";
            }
        } else {
            return Constants.REDIRECT_HOME;
        }
    }

    /**
     * Method post to modify the stock of an article.
     * @param id the id of the article.
     * @param quantity the quantity of the article.
     * @param perishable the perishable of the article.
     * @param session the session.
     * @return the view name.
     * @throws ExceptionArticle if an error occurs.
     */
    @PostMapping(path = "/adminpanel/stockmanager/stock/{id}")
    public String modifStockImperishable(@PathVariable("id") int id, @RequestParam("quantity") int quantity, @RequestParam("perishable") Boolean perishable,HttpSession session) throws ExceptionArticle {
        String username = (String) session.getAttribute(Constants.USERNAME);
        if (username.equals(Constants.ADMIN)) {
            if (Boolean.FALSE.equals(perishable)) {
                iDao.updateImperishableStock(id, quantity);
                return "redirect:/adminpanel/stockmanager";
            } else {
                return Constants.REDIRECT_HOME;
            }
        } else {
            return Constants.REDIRECT_HOME;
        }
    }
}

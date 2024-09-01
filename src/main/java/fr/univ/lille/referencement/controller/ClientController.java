package fr.univ.lille.referencement.controller;

import fr.univ.lille.referencement.dao.*;
import fr.univ.lille.referencement.model.Article;
import fr.univ.lille.referencement.model.Order;
import fr.univ.lille.referencement.model.OrderLine;
import fr.univ.lille.referencement.model.User;
import fr.univ.lille.referencement.utils.Constants;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import fr.univ.lille.referencement.dao.OrderDao;
import fr.univ.lille.referencement.dao.UserDaoJPAImpl;

@Controller
@RequestMapping(path = "/")
public class ClientController {
    /**
     * The article DAO.
     */
    @Autowired
    private ArticleDao articleDao;

    /**
     * The order DAO implementation.
     */
    @Autowired
    OrderDaoImpl orderDaoImpl;

    /**
     * The order line DAO.
     */
    @Autowired
    OrderLineDao lineDao;

    /**
     * The user DAO implementation.
     */
    @Autowired
    private UserDaoJPAImpl userDao;

    @Autowired
    OrderDao orderDao;

    /**
     * Method get to buy an article.
     * @param id the article id.
     * @param model the model.
     * @param session the session.
     * @return the view.
     */
    @GetMapping(path = "/client/buyArticle/{id}")
    public String buyArticle(@PathVariable("id") int id, Model model, HttpSession session) {
        String username = (String) session.getAttribute(Constants.USERNAME);
        if (username.equals(Constants.CLIENT)) {
            Article a = articleDao.getArticle(id);
            model.addAttribute(Constants.ARTICLE, a);
            return "buyArticle";
        } else {
            return Constants.REDIRECT_HOME;
        }
    }

    /**
     * Method post to buy an article.
     * @param session the session.
     * @param quantity the quantity.
     * @param id the article id.
     * @param model the model.
     * @return the view.
     */
    @PostMapping(path = "/client/buyArticle/{id}")
    public String articleIn(HttpSession session, @RequestParam("quantity") int quantity, @PathVariable("id") int id,
                            Model model) {
        String username = (String) session.getAttribute(Constants.USERNAME);
        if (username.equals(Constants.CLIENT)) {
            User u = (User) session.getAttribute("user");
            u = userDao.getUserById(u.getId());

            Order o = u.getNotBuyedCommand();
            if(o == null){
                return Constants.REDIRECT_HOME;
            }
            OrderLine l = new OrderLine(articleDao.getArticle(id));
            l.setQuantity(quantity);
            orderDaoImpl.addOrderLine(o.getId(), l);
            return Constants.REDIRECT_HOME;
        } else {
            return Constants.REDIRECT_HOME;
        }
    }

    /**
     * Method get to see the cart.
     * @param session the session.
     * @param model the model.
     * @return the view.
     */
    @GetMapping(path = "/client/panier")
    public String getPanier(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username.equals("client")) {
        User u = ((User) session.getAttribute("user"));
        u = userDao.getUserById(  u.getId());
        Order order = u.getNotBuyedCommand();
        model.addAttribute("idOrder", order.getId());
        model.addAttribute("lineCommands", order.getLines());
        return "panier";
    } else {
        return Constants.REDIRECT_HOME;
    }
    }

    /**
     * Method post for the cart.
     * @param id the article id.
     * @param session the session.
     * @param model the model.
     * @return the view.
     */
    @PostMapping(path = "/client/panier/{id}")
    public String postPanier(@PathVariable("id")int id, HttpSession session, Model model) {
        String username = (String) session.getAttribute(Constants.USERNAME);
        if (username.equals("client")) {
            if(Boolean.TRUE.equals(orderDao.validate(id))){
                User u = (User) session.getAttribute("user");
                userDao.addOrder(u.getId(), new Order(""));
                return Constants.REDIRECT_HOME;
            }
            return "redirect:/client/panier";
    }else {
        return Constants.REDIRECT_HOME;
    }}
}

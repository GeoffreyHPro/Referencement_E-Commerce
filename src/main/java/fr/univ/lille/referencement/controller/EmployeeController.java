package fr.univ.lille.referencement.controller;

import fr.univ.lille.referencement.dao.*;
import fr.univ.lille.referencement.model.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

    private static final String USERNAME = "username";

    private static final Object EMPLOYEE = "employee";

    private static final String REDIRECT_HOME = "redirect:/home";

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

    @Autowired
    ArticleDao articleDao;

    

    /**
     * The order page.
     * @param model the model.
     * @param session the session.
     * @return the view name.
     */
    @GetMapping(path = "/adminpanel/ordermanager/order/**")
    public String getOrders(Model model, HttpSession session) {
        String username = (String) session.getAttribute(USERNAME);
        if (username.equals(EMPLOYEE)) {
        List<Order> orders = orderDaoImpl.getAllOrders();
        model.addAttribute("orders", orders);
        return "ListOrders";
        } else if (username.equals("admin")) {
            return "redirect:/adminpanel";
        } else {
            return REDIRECT_HOME;
        }
    }

    /**
     * The page of details for an order.
     * @param id the id of the order.
     * @param model the model.
     * @return the view name.
     */
    @GetMapping(path = "/adminpanel/ordermanager/order/{id}")
    public String getOrders(@PathVariable("id") int id, Model model, HttpSession session) {
        String username = (String) session.getAttribute(USERNAME);
        if (username.equals(EMPLOYEE)) {
            Order order = orderDaoImpl.getOrder(id);
            List<OrderLine> l = order.getLines();
            model.addAttribute("idOrder", id);
            model.addAttribute("lines", l);
            return "detailCommande";
        } else {
            return REDIRECT_HOME;
        }
    }

    @PostMapping(path = "/adminpanel/ordermanager/order/{id}")
    public String updateOrder(@PathVariable("id") int id, @RequestParam("idOrder") int idOrder, Model model, HttpSession session) {
        String username = (String) session.getAttribute(USERNAME);
        if (username.equals(EMPLOYEE)) {
            OrderLine orderline = lineDao.getOrderLine(id);
            if (!orderline.getArticle().isPerishable()) {
                orderline.getArticle().add(orderline.getQuantity());
                lineDao.updateOrderLine(id, 0);
                return "redirect:/adminpanel/ordermanager/order/" + idOrder;
            } else if (orderline.getArticle().isPerishable()) {
                lineDao.updateOrderLine(id, 0);
                return "redirect:/adminpanel/ordermanager/order/" + idOrder;
            }
        } else {
            return REDIRECT_HOME;
        }
        return REDIRECT_HOME;
    }
}
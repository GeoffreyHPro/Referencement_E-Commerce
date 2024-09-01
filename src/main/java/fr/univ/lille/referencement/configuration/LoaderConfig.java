package fr.univ.lille.referencement.configuration;

import fr.univ.lille.referencement.dao.*;
import fr.univ.lille.referencement.model.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.univ.lille.referencement.service.ChiffrementService;
import jakarta.annotation.PostConstruct;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoaderConfig {

    static final String ELECTROMENAGER = "Electromenager";
    static final String LIVRE = "Livre";
    static final String ALIMENTAIRE = "Alimentaire";
    static final String BOULANGERIE = "Boulangerie";
    static final String FRUIT = "Fruit";
    static final String PRODUIT_ENTRETIEN = "Produit d'entretien";

    @Autowired
    private UserDaoJPAImpl userDao;

    @Autowired
    private ImperishableDao iDao;

    @Autowired
    private PerishableDao pDao;

    @Autowired
    private ChiffrementService chiffrementService;

    @Autowired
    private CategoryDao cDao;

    public LoaderConfig(UserDaoJPAImpl userDao) {
        this.userDao = userDao;
    }

    @PostConstruct @Transactional
    public void init() throws Exception {
        

        userDao.addUser(new User("client", chiffrementService.chiffrer("client"), Role.CLIENT));
        userDao.addUser(new User("admin", chiffrementService.chiffrer("admin"), Role.ADMIN));
        userDao.addUser(new User("employee", chiffrementService.chiffrer("employee"), Role.EMPLOYEE));
        
        Map<String, Category> categories = new HashMap<>();
        categories.put(ELECTROMENAGER, new Category(ELECTROMENAGER, new ArrayList<>()));
        categories.put("Informatique", new Category("Informatique", new ArrayList<>()));
        categories.put(LIVRE, new Category(LIVRE, new ArrayList<>()));
        categories.put("Vetement", new Category("Vetement", new ArrayList<>()));
        categories.put("Sport", new Category("Sport", new ArrayList<>()));
        categories.put(ALIMENTAIRE, new Category(ALIMENTAIRE, new ArrayList<>()));
        categories.put(BOULANGERIE, new Category(BOULANGERIE, new ArrayList<>()));
        categories.put(FRUIT, new Category(FRUIT, new ArrayList<>()));
        categories.put("Legume", new Category("Legume", new ArrayList<>()));
        categories.put("Viande", new Category("Viande", new ArrayList<>()));
        categories.put("Poisson", new Category("Poisson", new ArrayList<>()));
        categories.put("Produit laitier", new Category("Produit laitier", new ArrayList<>()));
        categories.put("Boisson", new Category("Boisson", new ArrayList<>()));
        categories.put("Jouet", new Category("Jouet", new ArrayList<>()));
        categories.put(PRODUIT_ENTRETIEN, new Category(PRODUIT_ENTRETIEN, new ArrayList<>()));
        categories.put("Produit de beauté", new Category("Produit de beauté", new ArrayList<>()));
        categories.put("Autre", new Category("Autre", new ArrayList<>()));

        for (Map.Entry<String, Category> entry : categories.entrySet()) {
            cDao.addCategory(entry.getValue());
        }

        Imperishable i1 = new Imperishable("naruto", 7.9, "/uploads/naruto.jpg", 50) ;
        i1.getCategories().add(categories.get(LIVRE));
        iDao.addImperishable(i1);

        Imperishable i2 = new Imperishable("aspirateur", 50.99, "/uploads/aspirateur.jpg", 2) ;
        i2.getCategories().add(categories.get(ELECTROMENAGER));
        i2.getCategories().add(categories.get(PRODUIT_ENTRETIEN));
        iDao.addImperishable(i2);

        Imperishable i3 = new Imperishable("monster", 7.8, "/uploads/tenma.jpg", 20) ;
        i3.getCategories().add(categories.get(LIVRE));
        iDao.addImperishable(i3);


        Perishable p1 = new Perishable("confiture", 4.50, "/uploads/confiture_fraise.jpg");
        p1.getCategories().add(categories.get(ALIMENTAIRE));
        p1.getCategories().add(categories.get(FRUIT));
        Lot l1 = new Lot(5, "2024-02-05");
        pDao.addPerishable(p1,l1);


        Perishable p2 = new Perishable("pain au chocolat", 2.3, "/uploads/pain_choco.jpg");
        p2.getCategories().add(categories.get(ALIMENTAIRE));
        p2.getCategories().add(categories.get(BOULANGERIE));
        Lot l2 = new Lot(150, "2024-01-01");
        pDao.addPerishable(p2,l2);

        Perishable p3 = new Perishable("pomme", 0.5, "/uploads/pomme.jpg");
        p3.getCategories().add(categories.get(ALIMENTAIRE));
        p3.getCategories().add(categories.get(FRUIT));
        Lot l3 = new Lot(50, "2024-01-15");
        pDao.addPerishable(p3,l3);

        importfile("src/main/webapp/uploads/article.csv", categories, iDao, pDao);
    }
    public void importfile(String file, Map<String, Category> categories, ImperishableDao dada, PerishableDao dado) {
        String csvFile = file;
        String line;
        String csvSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                String type = data[0];
                String name = data[1];
                double price = Double.parseDouble(data[2]);
                int quantity = Integer.parseInt(data[3]);
                String categorie = data[4];
                String expirationdate = data[5];
                String[] multiplecategories = categorie.split(";");
                if (type.equals("Imperishable")){
                    Imperishable article = new Imperishable(name, price, "/uploads/default.png",quantity);
                    for (String category : multiplecategories) {
                        article.getCategories().add(categories.get(category));
                    }
                    dada.addImperishable(article);
                } else if (type.equals("Perishable")) {
                    Perishable article = new Perishable(name, price, "/uploads/default.png");
                    for (String category : multiplecategories) {
                        article.getCategories().add(categories.get(category));
                    }
                    Lot lot = new Lot(quantity, expirationdate);
                    dado.addPerishable(article,lot);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

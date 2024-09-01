package fr.univ.lille.referencement.service;

import fr.univ.lille.referencement.dao.ArticleDaoImpl;
import fr.univ.lille.referencement.dao.CategoryDaoImpl;
import fr.univ.lille.referencement.dao.ImperishableDaoImpl;
import fr.univ.lille.referencement.dao.LotDao;
import fr.univ.lille.referencement.model.Article;
import fr.univ.lille.referencement.model.Category;
import fr.univ.lille.referencement.model.Lot;
import fr.univ.lille.referencement.utils.ExceptionArticle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Service to manage articles.
 */
@Service
public class ArticlesServices {

    /**
     * The imperishable DAO.
     */
    @Autowired
    private ImperishableDaoImpl imperishableDao;

    /**
     * The category DAO.
     */
    @Autowired
    private CategoryDaoImpl categoryDao;

    /**
     * The article DAO.
     */
    @Autowired
    private ArticleDaoImpl articleDao;

    /**
     * The lot DAO.
     */
    @Autowired
    private LotDao lotDao;

    /**
     * To filter articles by category.
     * @param allArticle the list of articles to filter.
     * @param category the category to filter.
     */
    private void filterByCategory(Set<Article> allArticle, String category) {
        if (category != null) {
            allArticle.removeIf(article -> !article.getCategoriesNames().contains(category));
        }
    }

    /**
     * To filter articles by name.
     * @param allArticle the list of articles to filter.
     * @param prodName the name to filter.
     */
    private void filterByName(Set<Article> allArticle, String prodName) {
        if (prodName != null) {
            allArticle.removeIf(article -> !article.getName().contains(prodName));
        }
    }

    /**
     * To filter articles by reference.
     * @param allArticle the list of articles to filter.
     * @param prodRef the reference to filter.
     */
    private void filterByReference(Set<Article> allArticle, String prodRef) {
        if (prodRef != null) {
            allArticle.removeIf(article -> !String.valueOf(article.getId()).contains(prodRef));
        }
    }

    /**
     * To get all articles not perished.
     * @return a JSON object with all articles not perished.
     * @throws ExceptionArticle if an error occurs.
     */
    public List<Article> getAllNotPerishedArticles(String category, String prodName, String prodRef) throws ExceptionArticle{
        Set<Article> allArticle = new HashSet<>();
        List<Lot> allLotPerishable = lotDao.getLotBeforeDate(LocalDateTime.now().plusDays(5));

        allLotPerishable.removeIf(lot -> lot.getQuantity() == 0);

        for (Lot lot : allLotPerishable) {
            allArticle.add(lot.getPerishable());
        }

        allArticle.addAll(imperishableDao.getAllImperishablesWithStock());

        return filterAndSort(category, prodName, prodRef, allArticle);
    }

    /**
     * To filter and sort articles.
     * @param category the category to filter.
     * @param prodName the name to filter.
     * @param prodRef the reference to filter.
     * @param allArticle the list of articles to filter.
     * @return a JSON object with all articles filtered and sorted.
     */
    private List<Article> filterAndSort(String category, String prodName, String prodRef, Set<Article> allArticle) {
        filterByCategory(allArticle, category);
        filterByName(allArticle, prodName);
        filterByReference(allArticle, prodRef);

        List<Article> sortedArticles = new ArrayList<>(allArticle);
        sortedArticles.sort(Comparator.comparingInt(Article::getId));
        return sortedArticles;
    }

    /**
     * To get all articles.
     * @param category the category to filter.
     * @param prodName the name to filter.
     * @param prodRef the reference to filter.
     * @return a JSON object with all articles.
     */
    public List<Article> getAllArticles(String category, String prodName, String prodRef) {
        List<Article> articles = articleDao.getAllArticles();

        Set<Article> allArticle = new HashSet<>(articles);

        return filterAndSort(category, prodName, prodRef, allArticle);
    }

    /**
     * To get all categories names.
     * @return a List of all categories names.
     */
    public List<String> getAllCategoriesNames() {
        return categoryDao.getAllCategories().stream().map(Category::getName).toList();
    }

    /**
     * To get articles by page.
     * @param page the page to get.
     * @param size the size of the page.
     * @param articles the list of articles.
     * @return a JSON object with the articles of the page.
     */
    public List<Article> getPagedArticles(int page, int size, List<Article> articles) {
        int start = (page - 1) * size;
        int end = Math.min(start + size, articles.size());
        if (start > end) {
            return new ArrayList<>();
        }
        return articles.subList(start, end);
    }

    /**
     * To get the pages.
     * @param size the size of the page.
     * @param articlesSize the size of the list of articles.
     * @return a JSON object with the pages.
     */
    public List<Integer> getPages(int size, int articlesSize) {
        int nbPages = articlesSize/size ;
        if (articlesSize % size != 0) {
            nbPages++;
        }

        // creer une liste de nbPages elements avec un for de 1 a nbPages
        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= nbPages; i++) {
            pages.add(i);
        }
        return pages;
    }

}

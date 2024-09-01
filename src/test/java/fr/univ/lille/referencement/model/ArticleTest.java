package fr.univ.lille.referencement.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ArticleTest {

    static Article article;
    static Category category;

    @Before
    public void init() {
        article = new Article("Example", 10.0, "example.jpg") {
            @Override
            public Boolean isPerishable() {
                return false;
            }

            @Override
            public void delete(int q) {}

            @Override
            public void add(int q) {}

            @Override
            public int getQuantity() {
                return 0;
            }

            @Override
            public int isPerishableInt() {
                return 0;
            }
        };

        category = new Category("Electronics", null);
    }

    @Test
    public void ArticleConstructorTest() {
        assertEquals("Example", article.getName());
        assertEquals(10.0, article.getPrice(), 0.0);
        assertEquals("example.jpg", article.getImg());
        assertEquals(0, article.getCategories().size());
    }

    @Test
    public void AddCategoryToArticleTest() {
        assertEquals(0, article.getCategories().size());
        article.getCategories().add(category);
        assertEquals(1, article.getCategories().size());
        assertEquals("Electronics", article.getCategories().get(0).getName());
    }

    @Test
    public void ArticleGetCategoriesNamesTest() {
        article.getCategories().add(category);
        assertEquals(1, article.getCategories().size());
        assertEquals("Electronics", article.getCategoriesNames().get(0));
    }

    @Test
    public void ArticleIsPerishableTest() {
        assertFalse(article.isPerishable());
    }
    
}

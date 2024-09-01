package fr.univ.lille.referencement.utils;

/**
 * ExceptionArticle is the exception that is thrown when there is an error with an article.
 */
public class ExceptionArticle  extends RuntimeException {

    public ExceptionArticle(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
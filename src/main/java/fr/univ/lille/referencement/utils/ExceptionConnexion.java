package fr.univ.lille.referencement.utils;

/**
 * ExceptionConnexion is the exception that is thrown when there is an error with the connexion.
 */
public class ExceptionConnexion  extends RuntimeException {

    public ExceptionConnexion(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}

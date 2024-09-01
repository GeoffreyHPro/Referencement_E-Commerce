package fr.univ.lille.referencement.dao;

import fr.univ.lille.referencement.model.Lot;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This interface is used to manage the lots in the database.
 */
public interface LotDao {

    /**
     * This method is used to get a lot from the database.
     * @param id The id of the lot.
     * @return The lot.
     */
    Lot getLot(int id);

    /**
     * This method is used to get all the lots that will expire before a date.
     * @param date The date before which the lots will expire.
     * @return The list of all the lots that wil expire before the date.
     */
        List<Lot> getLotBeforeDate(LocalDateTime date);

    /**
     * This method is used to add a lot in the database.
     * @param lot The lot to add.
     */
    void addLot(Lot lot);

    /**
     * This method is used to delete a lot from the database.
     * @param id The id of the lot to delete.
     * @return The deleted lot.
     */
    Lot deleteLot(int id);

    /**
     * This method is used to update a lot in the database.
     * @param id The id of the lot to update.
     * @param quantity The quantity of the lot to update.
     * @param date The date of the lot to update.
     */
    void updateLot(int id, int quantity, LocalDateTime date);

}

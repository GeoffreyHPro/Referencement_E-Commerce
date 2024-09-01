package fr.univ.lille.referencement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Lot is the class that represent the lots in the store. It contains articles that are Perishables.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@Table(name = "lots")
public class Lot {

    /**
     * The id of the lot.
     */
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    /**
     * The quantity of the lot.
     */
    private int quantity;

    /**
     * The expiration date of the lot.
     */
    private LocalDateTime expirationDate;

    /**
     * The Perishable articles of the lot.
     */
    @ManyToOne(cascade = CascadeType.DETACH)
    Perishable perishable;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME ;

    public Lot(){}

    /**
     * Constructor.
     * @param quantity the quantity of the lot.
     * @param expirationDate the expiration date of the lot in the format "yyyy-MM-dd".
     */
    public Lot(int quantity, String expirationDate) throws DateTimeParseException {
        this.quantity = quantity;
        this.expirationDate = getFormattedDate(expirationDate);
    }

    /**
     * To format the date.
     * @param date the date to format.
     * @return the formatted date.
     */
    public static LocalDateTime getFormattedDate(String date) {
    	return LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(date)).atStartOfDay();
    }

    /**
     * Get the expiration date of the lot in the format "yyyy-MM-dd".
     */
    public String getExpirationDateFormatted(){
        String value = FORMATTER.format(expirationDate);
        return value.substring(0, value.indexOf('T'));
    }

    /**
     * To know if the lot has perished.
     * @return 2 if the lot has perished, 1 if the lot is about to perish in 5 days, 0 otherwise.
     */
    public int isPerishableInt() {
        if (getExpirationDate().isBefore(LocalDate.now().atStartOfDay())){
            return 2;
        } else if (getExpirationDate().isBefore(LocalDate.now().plusDays(5).atStartOfDay())){
            return 1;
        }
        return 0;
    }
}

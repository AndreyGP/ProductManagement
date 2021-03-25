package labs.pm.data;

import java.math.BigDecimal;

/**
 * <p>ProductManagement Created by Home Work Studio AndrHey [andreigp]</p>
 * <p>FileName: Drink.java</p>
 * <p>Date/time: 26 март 2021 in 0:17</p>
 *
 * @author Andrei G. Pastushenko
 * @see labs.pm.data.Product
 */

public class Drink extends Product {
    /**
     * This constructor is used to create an object of an existing product obtained from the database.
     * @param id int
     * @param name String
     * @param price BigDecimal
     * @param rating Rating
     * @param DISCOUNT_RATE BigDecimal
     */
    public Drink(int id, String name, BigDecimal price, Rating rating, BigDecimal DISCOUNT_RATE) {
        super(id, name, price, rating, DISCOUNT_RATE);
    }
}

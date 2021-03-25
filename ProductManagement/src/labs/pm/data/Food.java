package labs.pm.data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>ProductManagement Created by Home Work Studio AndrHey [andreigp]</p>
 * <p>FileName: Food.java</p>
 * <p>Date/time: 26 март 2021 in 0:15</p>
 *
 * @author Andrei G. Pastushenko
 * @see labs.pm.data.Product
 */

public class Food extends Product {
    private LocalDate bestBefore;

    /**
     * This constructor is used to create an object of an existing product obtained from the database.
     * @param id int - ID[SKU] product
     * @param name String - The name of the priduct
     * @param price BigDecimal - Current product price
     * @param rating Rating - Current customer rating of the product
     * @param DISCOUNT_RATE BigDecimal - The current discount for this product
     * @param bestBefore LocalDate - Product shelf life
     */
    public Food(int id, String name, BigDecimal price, Rating rating, BigDecimal DISCOUNT_RATE, LocalDate bestBefore) {
        super(id, name, price, rating, DISCOUNT_RATE);
        this.bestBefore = bestBefore;
    }

    /**
     * Get value of the best before product
     *
     * @return LocalDate value field bestBefore
     * @see LocalDate
     */
    public LocalDate getBestBefore() {
        return bestBefore;
    }
}

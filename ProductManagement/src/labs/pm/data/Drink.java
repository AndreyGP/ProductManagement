package labs.pm.data;

import java.math.BigDecimal;
import static java.math.BigDecimal.valueOf;
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
     * <h2>This constructor is used to create an object of an existing product obtained from the database.</h2>
     * @param id int
     * @param name String
     * @param price BigDecimal
     * @param rating Rating
     * @param DISCOUNT_RATE BigDecimal
     */
    public Drink(int id, String name, BigDecimal price, Rating rating, BigDecimal DISCOUNT_RATE) {
        super(id, name, price, rating, DISCOUNT_RATE);
    }

    /**
     * <h2>Basic constructor for creating a new product.</h2>
     * <p>It is used to create and save a new unit of product in the database, with a preliminary percentage
     * and set the default primary rating (Not NOT_RATED).</p>
     * <p>When creating a new product, a unique product ID[SKU] is generated and assigned</p>
     * @param name String - The name of the priduct being created
     * @param price BigDecimal - Primary product interest
     * @param rating BigDecimal - The primary rating given to a new product
     */
    public Drink(String name, BigDecimal price, Rating rating) {
        super(name, price, rating);
    }

    @Override
    public Drink applyName(String name) {
        return new Drink(this.getId(), name, this.getPrice(), this.getRating(), this.getDiscount());
    }

    @Override
    public Drink applyPrice(BigDecimal price) {
        return new Drink(this.getId(), this.getName(), price, this.getRating(), this.getDiscount());
    }

    @Override
    public Drink applyRating(Rating rating) {
        return new Drink(this.getId(), this.getName(), this.getPrice(), rating, this.getDiscount());
    }

    @Override
    public Drink applyDiscountRate(int rate) {
        return new Drink(this.getId(), this.getName(), this.getPrice(), this.getRating(), valueOf(rate));
    }
}

package labs.pm.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Properties;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.*;
import static java.math.RoundingMode.HALF_UP;


/**
 * <p>ProductManagement Created by Home Work Studio AndrHey [andreigp]</p>
 * <p>FileName: Food.java</p>
 * <p>Date/time: 26 март 2021 in 0:15</p>
 *
 * @author Andrei G. Pastushenko
 * @see labs.pm.data.Product
 */

public class Food extends Product {
    /**
     * Product shelf life
     */
    private final LocalDate bestBefore;

    /**
     * <h2>This constructor is used to create an object of an existing product obtained from the database.</h2>
     * @param id int - ID[SKU] product
     * @param name String - The name of the priduct
     * @param price BigDecimal - Current product price
     * @param rating Rating - Current customer rating of the product
     * @param DISCOUNT_RATE BigDecimal - The current discount for this product
     * @param bestBefore LocalDate - Product shelf life
     */
    Food(final int id, final String name, final BigDecimal price, final Rating rating, final BigDecimal DISCOUNT_RATE, final LocalDate bestBefore) {
        super(id, name, price, rating, DISCOUNT_RATE);
        this.bestBefore = bestBefore;
    }

    /**
     * <h2>Basic constructor for creating new food products</h2>
     * @param name String - The name of the priduct
     * @param price BigDecimal - Current product price
     * @param rating Rating - Current customer rating of the product
     * @param bestBefore LocalDate - Shelf life
     */
    Food(final String name, final BigDecimal price, final Rating rating, final LocalDate bestBefore) {
        super(name, price, rating);
        this.bestBefore = bestBefore;
    }

    /**
     * <h2>Get value of the best before product</h2>
     * @return LocalDate value field bestBefore
     * @see LocalDate
     */
    public LocalDate getBestBefore() {
        return this.bestBefore;
    }

    @Override
    public BigDecimal getDiscount() {
        return this.bestBefore.isEqual(now()) ? getPrice().multiply(valueOf(0.50)).setScale(2, HALF_UP) : super.getDiscount();
    }

    @Override
    public Food applyName(final String name) {
        return new Food(this.getId(), name, this.getPrice(), this.getRating(), valueOf(this.getPercentageDiscount()), this.getBestBefore());
    }

    @Override
    public Food applyPrice(final BigDecimal price) {
        return new Food(this.getId(), this.getName(), price, this.getRating(), valueOf(this.getPercentageDiscount()), this.getBestBefore());
    }

    @Override
    public Food applyRating(final Rating rating) {
        return new Food(this.getId(), this.getName(), this.getPrice(), rating, valueOf(this.getPercentageDiscount()), this.getBestBefore());
    }

    @Override
    public Food applyDiscountRate(final int rate) {
        return new Food(this.getId(), this.getName(), this.getPrice(), this.getRating(), valueOf(rate), this.getBestBefore());
    }
}

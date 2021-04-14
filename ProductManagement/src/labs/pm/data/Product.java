package labs.pm.data;

import java.math.BigDecimal;
import java.util.Comparator;

import static java.math.RoundingMode.HALF_UP;
import static java.math.BigDecimal.valueOf;
import static labs.pm.data.Rating.*;

/**
 * <p>ProductManagement Created by Home Work Studio AndrHey [andreigp]</p>
 * <p>FileName: Product.java</p>
 * <p>Date/time: 21 март 2021 in 18:18</p>
 *
 * @author Andrei G. Pastushenko
 * <p>
 * Parent class for all types of food items
 */

abstract public class Product implements CommodityUnit, Rateable<Product> {
    /**
     * Assigns an ID [SKU] for each new product
     */
    private static int maxId = 0;

    /**
     * Current product ID. Assigned when creating a new product or initialized
     * with the value of the id field in the table of existing products database
     */
    private final int id;

    /**
     * Product title
     */
    private final String name;

    /**
     * Current product price
     */
    private final BigDecimal price;

    /**
     * The current customer rating of the product.
     * A new product or a product that has not yet been voted on by a customer has no rating
     */
    private final Rating rating;

    /**
     * Discount amount in percent - n%
     */
    private final int percentageDiscount;

    /**
     * Discount amount for the current product
     */
    private final BigDecimal DISCOUNT_RATE;

    /**
     * Class constructors block
     */

    /**
     * <h2>Parameterless constructor.</h2> <p>Sets "null" values to the fields of the class. Used for debugging.</p>
     */
    Product() {
        this(0, "no name", BigDecimal.ZERO, NOT_RATED, valueOf(0.00));
    }

    /**
     * <h2>Constructor for creating a completely new product.</h2>
     * <p>It is used when it is necessary to create a unit of goods in the database and first set its price
     * Calls the constructor of the current class with extended object initialization.</p>
     * <p>The default rating is passed Rating.NOT_RATED</p>
     *
     * @param name  String - The name of the product being created
     * @param price BigDecimal - Primary product interest
     */
    Product(final String name, final BigDecimal price) {
        this(name, price, NOT_RATED);
    }

    /**
     * <h2>Basic constructor for creating a new product.</h2>
     * <p>It is used to create and save a new unit of product in the database, with a preliminary percentage
     * and set the default primary rating (Not NOT_RATED).</p>
     * <p>When creating a new product, a unique product ID[SKU] is generated and assigned</p>
     *
     * @param name   String - The name of the priduct being created
     * @param price  BigDecimal - Primary product interest
     * @param rating BigDecimal - The primary rating given to a new product
     */
    Product(final String name, final BigDecimal price, final Rating rating) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.percentageDiscount = 5;
        this.DISCOUNT_RATE = valueOf((double) percentageDiscount / (double) 100);
        this.id = ++maxId;
    }

    /**
     * <h2>This constructor is used to create an object of an existing product obtained from the database.</h2>
     *
     * @param id            int - ID[SKU] product
     * @param name          String - The name of the priduct
     * @param price         BigDecimal - Current product price
     * @param rating        Rating - Current customer rating of the product
     * @param DISCOUNT_RATE BigDecimal - The current discount for this product
     * @param bestBefore    LocalDate - Product shelf life
     */
    Product(final int id, final String name, final BigDecimal price, final Rating rating, final BigDecimal DISCOUNT_RATE) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.percentageDiscount = DISCOUNT_RATE.intValue();
        this.DISCOUNT_RATE = valueOf(percentageDiscount / (double) 100);
    }

    /**
     * Getters block
     */

    /**
     * @see CommodityUnit
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * @see CommodityUnit
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @see CommodityUnit
     */
    @Override
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @see CommodityUnit
     */
    @Override
    public BigDecimal getDiscount() {
        return price.multiply(DISCOUNT_RATE).setScale(2, HALF_UP);
    }

    /**
     * @see CommodityUnit
     */
    @Override
    public int getPercentageDiscount() {
        return percentageDiscount;
    }

    /**
     * @see CommodityUnit
     */
    @Override
    public Rating getRating() {
        return rating;
    }


    /**
     * Overridden methods and custom modification methods of the toString () method
     */

    /**
     * <h2>Method toString() overriding for convenient debugging</h2>
     *
     * @return Reference to a String object
     * <p>Returns the full product description string</p>
     * @see Object
     */

    @Override
    public String toString() {
        return new StringBuffer()
                .append(getName())
                .append(": SKU " + getId())
                .append("; Full price " + getPrice())
                .append("; Your personal discount " + getDiscount())
                .append("; Current consumer rating " + getRating().getStars())
                .toString();
    }

    /**
     * @see Object
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Product)) return false;
        if (getClass() != o.getClass()) return false;

        final Product product = (Product) o;

        if (id != product.id) return false;
        return true;
    }

    /**
     * @see Object
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + rating.hashCode();
        result = 31 * result + percentageDiscount;
        result = 31 * result + DISCOUNT_RATE.hashCode();
        return result;
    }

    /**
     * @see CommodityUnit
     */
    @Override
    public String toStringJSON() {
        return new StringBuffer()
                .append("{")
                .append("id: " + getId())
                .append(", title: \"" + getName() + "\"")
                .append(", price: " + getPrice())
                .append(", basic_discount: " + getDiscount())
                .append(", current_consumer_rating: " + getRating().ordinal())
                .append("}")
                .toString();
    }

}

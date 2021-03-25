package labs.pm.data;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;
import static labs.pm.data.Rating.*;

/**
 * <p>ProductManagement Created by Home Work Studio AndrHey [andreigp]</p>
 * <p>FileName: Product.java</p>
 * <p>Date/time: 21 март 2021 in 18:18</p>
 *
 * @author Andrei G. Pastushenko
 */

public class Product {
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
     * Product name
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
     * Discount amount for the current product
     */
    private final BigDecimal DISCOUNT_RATE;

    /**
     * Parameterless constructor. Sets "null" values to the fields of the class. Used for debugging.
     */
    public Product() {
        this(0, "no name", BigDecimal.ZERO, NOT_RATED, BigDecimal.valueOf(0.00));
    }

    /**
     * Constructor for creating a completely new product.
     * It is used when it is necessary to create a unit of goods in the database and first set its price
     * Calls the constructor of the current class with extended object initialization.
     * The default rating is passed Rating.NOT_RATED
     * @param name String - The name of the product being created
     * @param price BigDecimal - Primary product interest
     */
    public Product(String name, BigDecimal price) {
        this(name, price, NOT_RATED);
    }

    /**
     * Basic constructor for creating a new product.
     * It is used to create and save a new unit of product in the database, with a preliminary percentage
     * and set the default primary rating (Not NOY_RATED).
     * When creating a new product, a unique product ID[SKU] is generated and assigned
     * @param name String - The name of the priduct being created
     * @param price BigDecimal - Primary product interest
     * @param rating BigDecimal - The primary rating given to a new product
     */
    public Product(String name, BigDecimal price, Rating rating) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.DISCOUNT_RATE = BigDecimal.valueOf(0.05);
        this.id = ++maxId;
    }
    /**
     * This constructor is used to create an object of an existing product obtained from the database.
     * @param id int - ID[SKU] product
     * @param name String - The name of the priduct
     * @param price BigDecimal - Current product price
     * @param rating Rating - Current customer rating of the product
     * @param DISCOUNT_RATE BigDecimal - The current discount for this product
     * @param bestBefore LocalDate - Product shelf life
     */
    public Product(int id, String name, BigDecimal price, Rating rating, BigDecimal DISCOUNT_RATE) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.DISCOUNT_RATE = DISCOUNT_RATE;
    }

    /**
     * Returns the product ID
     * @return int id - SKU product
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the product
     * @return String name product
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the current price of a product
     * @return BigDecimal price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Returns the price of a product taking into account the current or personal discount
     * @return BigDecimal final price
     */
    public BigDecimal getDiscount() {
        return price.multiply(DISCOUNT_RATE).setScale(2, HALF_UP);
    }

    /**
     * Returns the current customer rating for a product
     * @return Rating rating
     * @see Rating
     */
    public Rating getRating() {
        return rating;
    }

    /**
     * A factory method that creates a new instance of a product using the state of the current object,
     * but with a new name. Used to change the name of a product unit in the database.
     * @param name String - New product name with current ID [SKU]
     * @return Product - New instance.
     */
    public Product applyName(String name) {
        return new Product(this.id, name, this.price, this.rating, this.DISCOUNT_RATE);
    }

    /**
     * A factory method that creates a new instance of a product using the state of the current object,
     * but with a new price. Used to change the price of a product unit in the database.
     * @param price BigDecimal - New product price with current ID [SKU]
     * @return Product - New instance
     */
    public Product applyPrice(BigDecimal price) {
        return new Product(this.id, this.name, price, this.rating, this.DISCOUNT_RATE);
    }

    /**
     * A factory method that creates a new instance of a product using the state of the current object,
     * but with a new customer rating. Used to change the customer rating of a product unit in the database.
     * @param rating Rating - New correct customer rating
     * @return Product - New instance
     */
    public Product applyRating(Rating rating) {
        return new Product(this.id, this.name, this.price, rating, this.DISCOUNT_RATE);
    }

    /**
     * A factory method that creates a new instance of a product using the state of the current object,
     * but with a new discount rate. Used to change the discount rate of a product unit in the database.
     * @param rate int - New correct discount rate
     * @return Product - New instance
     */
    public Product applyDiscountRate(int rate) {
        return new Product(this.id, this.name, this.price, rating, BigDecimal.valueOf((double) rate / 100));
    }
}

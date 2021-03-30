/*
 * Copyright (c) 2021. Andrei G. Pastushenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package labs.pm.data;

import java.math.BigDecimal;

/**
 * ProductManagement Created by Home Work Studio AndrHey [andreigp]
 * FileName: CommodityUnit.java
 * Date/time: 29 март 2021 in 1:51
 *
 * A common interface for any trade item, describing the behavior common to all trade items. All inheritors
 * of the interface must be immutable, creating a new instance based on the object whose state is being changed.
 */

public interface CommodityUnit {

    /**
     * <h2>Returns the commodity unit ID</h2>
     *
     * @return int id - SKU commodity unit
     */
    int getId();

    /**
     * <h2>Returns the title of the commodity unit</h2>
     *
     * @return String name commodity unit
     */
    String getName();

    /**
     * <h2>Returns the current price of a commodity unit</h2>
     *
     * @return BigDecimal price
     */
    BigDecimal getPrice();

    /**
     * <h2>Returns the price of a commodity unit taking into account the current or personal discount</h2>
     *
     * @return BigDecimal final price
     */
    BigDecimal getDiscount();

    /**
     * <h2>Returns the amount of the discount in %</h2>
     * @return int final amount of the discount
     */
    int getPercentageDiscount();

    /**
     * <h2>Returns the current customer rating for a commodity unit</h2>
     *
     * @return Rating rating
     * @see Rating
     */
    Rating getRating();


    /**
     * <h2>A factory method</h2> <p>that creates a new instance of a commodity unit using the state of the current object,
     * but with a new name. Used to change the name of a commodity unit unit in the database.</p>
     *
     * @param name String - New commodity unit name with current ID [SKU]
     * @return commodity unit - New instance.
     */
    CommodityUnit applyName(String name);

    /**
     * <h2>A factory method</h2> <p>that creates a new instance of a commodity unit using the state of the current object,
     * but with a new price. Used to change the price of a commodity unit unit in the database.</p>
     *
     * @param price BigDecimal - New commodity unit price with current ID [SKU]
     * @return CommodityUnit - New instance
     */
    CommodityUnit applyPrice(BigDecimal price);


    /**
     * <h2>A factory method</h2> <p>that creates a new instance of a commodity unit using the state of the current object,
     * but with a new customer rating. Used to change the customer rating of a commodity unit unit in the database.</p>
     *
     * @param rating Rating - New correct customer rating
     * @return CommodityUnit - New instance
     */
    CommodityUnit applyRating(Rating rating);

    /**
     * <h2>A factory method</h2> <p>that creates a new instance of a commodity unit using the state of the current object,
     * but with a new discount rate. Used to change the discount rate of a commodity unit unit in the database.</p>
     *
     * @param rate int - New correct discount rate
     * @return CommodityUnit - New instance
     */
    CommodityUnit applyDiscountRate(int rate);

    /**
     * <h2>API for getting JSON string of commodity unit object description</h2>
     *
     * @return Reference to a String object JSON string type, example:
     * <p>{</p>
     * <p>  id: 1,</p>
     * <p>  title: "Tea",</p>
     * <p>  price: 89.99,</p>
     * <p>  basic_discount: 9.00,</p>
     * <p>  current_consumer_rating: 0</p>
     * <p>}</p>
     * @see <a href="#{@link https://www.json.org/json-ru.html}">JSON</a>
     */
    String toStringJSON();
}

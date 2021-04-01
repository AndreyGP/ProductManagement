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

import static java.math.BigDecimal.valueOf;

/**
 * <p>ProductManagement Created by Home Work Studio AndrHey [andreigp]</p>
 * <p>FileName: NonFood.java</p>
 * <p>Date/time: 30 март 2021 in 22:50</p>
 *
 * @author Andrei G. Pastushenko
 * @see labs.pm.data.Product
 */

public class NonFood extends Product {
    /**
     * <h2>This constructor is used to create an object of an existing non-food product obtained from the database.</h2>
     * @param id int
     * @param name String
     * @param price BigDecimal
     * @param rating Rating
     * @param DISCOUNT_RATE BigDecimal
     */
    NonFood(final int id, final String name, final BigDecimal price, final Rating rating, final BigDecimal DISCOUNT_RATE) {
        super(id, name, price, rating, DISCOUNT_RATE);
    }

    /**
     * <h2>Basic constructor for creating a new non-food product.</h2>
     * <p>It is used to create and save a new unit of non-food product in the database, with a preliminary percentage
     * and set the default primary rating (Not NOT_RATED).</p>
     * <p>When creating a new product, a unique product ID[SKU] is generated and assigned</p>
     * @param name String - The name of the non-food product being created
     * @param price BigDecimal - Primary non-food product interest
     * @param rating BigDecimal - The primary rating given to a new non-food product
     */
    NonFood(final String name, final BigDecimal price, final Rating rating) {
        super(name, price, rating);
    }


    @Override
    public NonFood applyName(final String name) {
        return new NonFood(this.getId(), name, this.getPrice(), this.getRating(), this.getDiscount());
    }

    @Override
    public NonFood applyPrice(final BigDecimal price) {
        return new NonFood(this.getId(), this.getName(), price, this.getRating(), this.getDiscount());
    }

    @Override
    public NonFood applyRating(Rating rating) {
        return new NonFood(this.getId(), this.getName(), this.getPrice(), rating, this.getDiscount());
    }

    @Override
    public NonFood applyDiscountRate(final int rate) {
        return new NonFood(this.getId(), this.getName(), this.getPrice(), this.getRating(), valueOf(rate));
    }
}

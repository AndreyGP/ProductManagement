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

/**
 * <p>ProductManagement Created by Home Work Studio AndrHey [andreigp]</p>
 * <p>FileName: Rateable.java</p>
 * <p>Date/time: 01 апрель 2021 in 16:30</p>
 * @author Andrei G. Pastushenko
 *
 * <p>Functional interface for assessing a commodity item with default valuation and methods</p>
 */

@FunctionalInterface
public interface Rateable<T> {
    public static final Rating DEFAULT_RATING = Rating.NOT_RATED;

    T applyRating(Rating rating);
    /**
     * <h2>A factory method</h2> <p>that creates a new instance of a commodity unit using the state of the current object,
     * but with a new customer rating. Used to change the customer rating of a commodity unit unit in the database.</p>
     *
     * @param stars int - New correct customer rating from 0 to 5
     * @return T - New instance T type
     */
    public default T applyRating(int stars) {
        return applyRating(Rateable.convert(stars));
    }

    public default Rating getDefaultRating() {
        return DEFAULT_RATING;
    }

    public default Rating getRating() {
        return getDefaultRating();
    }

    public static Rating convert(int stars) {
        return 5 >= stars && stars >= 0 ? Rating.values()[stars] : DEFAULT_RATING;
    }
}

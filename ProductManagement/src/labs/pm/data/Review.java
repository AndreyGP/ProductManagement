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

import java.io.Serializable;

/**
 * <h2>Product Review Class</h2>
 * <p>ProductManagement Created by Home Work Studio AndrHey [andreigp]</p>
 * <p>FileName: Review.java</p>
 * <p>Date/time: 01 апрель 2021 in 22:38</p>
 * @author Andrei G. Pastushenko
 */

public class Review implements Comparable<Review>, Serializable {
    /**
     * Current review rating
     */
    private final Rating rating;
    /**
     * Current review comment
     */
    private final String comment;

    /**
     * <p>Default constructor for every new product review</p>
     * @param rating Rating - The number of stars that the consumer gave to the product
     * @param comment String - Review of a product left by a consumer
     */
    public Review(final Rating rating, final String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    /**
     * <p>Review class API to get a rating of current consumer review</p>
     * @return Rating
     */
    public Rating getRating() {
        return rating;
    }

    /**
     * <p>Review class API to get a comment of current consumer review</p>
     * @return String
     */
    public String getComment() {
        return comment;
    }

    /**
     * <p>Stub for receiving JSON of the current revocation string. Not final!!!</p>
     * @return String JSON format
     */
    @Override
    public String toString() {
        return "Review: {rating: " + getRating() + ", comment: " + getComment() + "}";
    }

    @Override
    public int compareTo(Review other) {
        return other.getRating().ordinal() - this.getRating().ordinal();
    }
}

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
 * <h2>Product Review Class</h2>
 * <p>ProductManagement Created by Home Work Studio AndrHey [andreigp]</p>
 * <p>FileName: Review.java</p>
 * <p>Date/time: 01 апрель 2021 in 22:38</p>
 * @author Andrei G. Pastushenko
 */

public class Review {
    private final Rating rating;
    private final String comment;

    public Review(final Rating rating, final String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public Rating getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "Review: {rating: " + getRating() + ", comment: " + getComment() + "}";
    }
}

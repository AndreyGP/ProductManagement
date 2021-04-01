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

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.now;
import static labs.pm.data.Rating.*;

/**
 * <p>ProductManagement Created by Home Work Studio AndrHey [andreigp]</p>
 * <p>FileName: CommodityManager.java</p>
 * <p>Date/time: 30 март 2021 in 23:13</p>
 *
 * @author Andrei.G.Pastushenko
 * <p>Factory class for instantiating items from outside the package</p>
 */

public class CommodityManager {
    private Product product;
    private Review review;
    private Locale locale;
    private ResourceBundle resource;
    private DateTimeFormatter dateFormat;
    private NumberFormat moneyFormat;

    public CommodityManager(Locale locale) {
        this.locale = locale;
        resource = ResourceBundle.getBundle("labs.pm.data.productbundle", locale);
        dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).localizedBy(locale);
        moneyFormat = NumberFormat.getCurrencyInstance(locale);
    }

    public CommodityManager() {
        this(Locale.getDefault());
    }

    public Product createNewProduct(final String name, final double price, final ProductType productType) {
        return switch (productType) {
            case FOOD -> product = new Food(name, valueOf(price), NOT_RATED, now().plusDays(7));
            case DRINK -> product = new Drink(name, valueOf(price), NOT_RATED);
            case NONFOOD -> product = new NonFood(name, valueOf(price), NOT_RATED);
        };
    }

    public Product reviewProduct(final Product product, final Rating rating, final String comment) {
        review = new Review(rating, comment);
        return this.product = product.applyRating(rating);
    }

    public void printProductReport() {
        StringBuilder report = new StringBuilder();
        report.append(MessageFormat.format(resource.getString("product"),
                product.getName(),
                moneyFormat.format(product.getPrice()),
                product.getRating().getStars(),
                dateFormat.format(LocalDate.now())));
        report.append('\n');
        if (review != null) {
            report.append(MessageFormat.format(resource.getString("review"),
                    review.getRating().getStars(),
                    review.getComment()));
        } else {
            report.append(resource.getString("no.reviews"));
        }
        report.append('\n');
        System.out.println(report);
    }
}

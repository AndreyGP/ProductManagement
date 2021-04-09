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
import java.util.*;

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
 * <p>NOT FINAL REALISATION!!!</p>
 */

public class CommodityManager {
    private Map<Product, List<Review>> products = new HashMap<>();
    private ResourceFormatter formatter;

    private static Map<String, ResourceFormatter> formatters = Map.of(
            "en-US", new ResourceFormatter(Locale.US),
            "en-UK", new ResourceFormatter(Locale.UK),
            "ru-RU", new ResourceFormatter(new Locale("ru", "RU"))
    );

    public CommodityManager(final Locale locale) {
        this(locale.toLanguageTag());
    }

    public CommodityManager(final String languageTag) {
        changeLocal(languageTag);
    }

    public void changeLocal(final String languageTag) {
        formatter = formatters.getOrDefault(languageTag, formatters.get("ru-RU"));
    }

    public static Set<String> getSupportedLocales() {
        return formatters.keySet();
    }

    public CommodityManager() {
        this(Locale.getDefault());
    }

    public Product createNewProduct(final String name, final double price, final ProductType productType) {
        return switch (productType) {
            case FOOD -> createNewFood(name, price);
            case DRINK -> createNewDrink(name, price);
            case NONFOOD -> createNewNonFood(name, price);
        };
    }

    private Product createNewFood(final String name, final double price) {
        Product product = new Food(name, valueOf(price), NOT_RATED, now().plusDays(7));
        products.put(product, new ArrayList<>());
        return product;
    }

    private Product createNewDrink(final String name, final double price) {
        Product product = new Drink(name, valueOf(price), NOT_RATED);
        products.put(product, new ArrayList<>());
        return product;
    }

    private Product createNewNonFood(final String name, final double price) {
        Product product = new NonFood(name, valueOf(price), NOT_RATED);
        products.put(product, new ArrayList<>());
        return product;
    }

    public Product findProductById(final int id) {
        for (Product product : products.keySet())
            if (product.getId() == id) return product;
        return null;
    }

    public Product reviewProduct(final int id, final Rating rating, final String comment) {
        return reviewProduct(findProductById(id), rating, comment);
    }

    public Product reviewProduct(final Product product, final Rating rating, final String comment) {
        List<Review> reviews = products.get(product);
        products.remove(product, reviews);
        reviews.add(new Review(rating, comment));
        Collections.sort(reviews);
        int sum = 0;
        for (Review review : reviews) sum += review.getRating().ordinal();
        Product newProduct = product.applyRating(Math.round((float) sum / reviews.size()));
        products.put(newProduct, reviews);
        return newProduct;
    }

    public void printProductReport(final int id) {
        printProductReport(findProductById(id));
    }

    public void printProductReport(final Product product) {
        StringBuilder report = new StringBuilder();
        report.append(formatter.formatProduct(product)).append('\n');
        if (products.get(product).isEmpty()) report.append(formatter.getText("no.reviews")).append('\n');
        else report.append(reviewAvailable(product));
        System.out.println(report);
    }

    public void printProducts(Comparator<Product> sorter) {
        List<Product> productList = new ArrayList<>(products.keySet());
        productList.sort(sorter);
        StringBuilder text = new StringBuilder();
        for (Product product : productList)
            text.append(formatter.formatProduct(product)).append('\n');
        System.out.println(text);
    }

    private String reviewAvailable(Product product) {
        StringBuilder txt = new StringBuilder();
        for (Review review : products.get(product)) {
            if (review == null) break;
            txt.append(formatter.formatReviews(review));
            txt.append('\n');
        }
        return txt.toString();
    }

    private static class ResourceFormatter {

        private final Locale locale;
        private final ResourceBundle resource;
        private final DateTimeFormatter dateFormat;
        private final NumberFormat moneyFormat;

        private ResourceFormatter(final Locale locale) {
            this.locale = locale;
            resource = ResourceBundle.getBundle("labs.pm.data.productbundle", locale);
            dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).localizedBy(locale);
            moneyFormat = NumberFormat.getCurrencyInstance(locale);
        }

        private String formatProduct(final Product product) {
            return MessageFormat.format(resource.getString("product"),
                    product.getName(),
                    moneyFormat.format(product.getPrice()),
                    product.getRating().getStars(),
                    dateFormat.format(LocalDate.now()));
        }

        private String formatReviews(final Review review) {
            return MessageFormat.format(getText("review"),
                    review.getRating().getStars(),
                    review.getComment());
        }

        private String getText(final String key) {
            return resource.getString(key);
        }
    }
}

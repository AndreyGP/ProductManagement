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
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    /**
     * HashMap containing all the localizations supported by the application
     * Not the final option!
     * <p>
     * TO DO: Add a nesting level so that the getSupportedLocales () method displays localizations as:
     * "English UK"
     * "English US"
     * "Русский РФ"
     * etc.
     */
    private static Map<String, ResourceFormatter> formatters = Map.of(
            "en-US", new ResourceFormatter(Locale.US),
            "en-UK", new ResourceFormatter(Locale.UK),
            "ru-RU", new ResourceFormatter(new Locale("ru", "RU"))
    );

    /**
     * Default constructor method. Sets default locale
     */
    public CommodityManager() {
        this(Locale.getDefault());
    }

    /**
     * <p>Constructor method sets the locale of the class to the passed parameter.
     * Pass the locale string tag to the overloaded constructor</p>
     *
     * @param locale Locale - The specified locale Locale type. Effectively the final variable of the method.
     */
    public CommodityManager(final Locale locale) {
        this(locale.toLanguageTag());
    }

    /**
     * <p>Constructor method sets the locale of the class to the passed parameter.
     * Sets the locale via the changeLocal () method</p>
     *
     * @param languageTag String - The specified locale String type. Effectively the final variable of the method.
     */
    public CommodityManager(final String languageTag) {
        changeLocal(languageTag);
    }

    /**
     * <p>Changes the current or sets the original (when instantiating the class) locale</p>
     *
     * @param languageTag String - Locale string tag
     */
    public void changeLocal(final String languageTag) {
        formatter = formatters.getOrDefault(languageTag, formatters.get("ru-RU"));
    }

    /**
     * <p>Returns a set of string tags of all locales supported by the application
     * String tags are keys of HashMap</p>
     *
     * @return Set - All supported locales
     * @see HashMap HashMap::keySet()
     */
    public static Set<String> getSupportedLocales() {
        return formatters.keySet();
    }

    /**
     * <p>Returns a new product object according to the passed parameters and the required Product type</p>
     *
     * @param name        String - new product name
     * @param price       double - original price of a new product
     * @param productType ProductType - the specific type of product being created
     * @return new ProductType reference
     */
    public Product createNewProduct(final String name, final double price, final ProductType productType) {
        return switch (productType) {
            case FOOD -> createNewFood(name, price);
            case DRINK -> createNewDrink(name, price);
            case NONFOOD -> createNewNonFood(name, price);
        };
    }

    /**
     * Helper method for createNewProduct ()
     *
     * @param name  String - new Food name
     * @param price double - original price of a new product
     * @return new Food reference
     */
    private Product createNewFood(final String name, final double price) {
        Product product = new Food(name, valueOf(price), NOT_RATED, now().plusDays(7));
        products.put(product, new ArrayList<>());
        return product;
    }

    /**
     * Helper method for createNewProduct ()
     *
     * @param name  String - new Drink name
     * @param price double - original price of a new product
     * @return new Drink reference
     */
    private Product createNewDrink(final String name, final double price) {
        Product product = new Drink(name, valueOf(price), NOT_RATED);
        products.put(product, new ArrayList<>());
        return product;
    }

    /**
     * Helper method for createNewProduct ()
     *
     * @param name  String - new NonFood name
     * @param price double - original price of a new product
     * @return new NonFood reference
     */
    private Product createNewNonFood(final String name, final double price) {
        Product product = new NonFood(name, valueOf(price), NOT_RATED);
        products.put(product, new ArrayList<>());
        return product;
    }

    public Product findProductById(final int id) {
        return products.keySet()
                .stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElseGet(() -> null);
    }

    public Product reviewProduct(final int id, final Rating rating, final String comment) {
        return reviewProduct(findProductById(id), rating, comment);
    }

    public Product reviewProduct(final Product product, final Rating rating, final String comment) {
        List<Review> reviews = products.get(product);
        products.remove(product, reviews);
        reviews.add(new Review(rating, comment));
        Collections.sort(reviews);
        Product newProduct = product.applyRating(
                Rateable.convert(
                        (int) Math.round(
                                reviews.stream()
                                        .mapToInt(review -> review.getRating().ordinal())
                                        .average()
                                        .orElse(0))));
        products.put(newProduct, reviews);
        return newProduct;
    }

    public void printProductReport(final int id) {
        printProductReport(findProductById(id));
    }

    public void printProductReport(final Product product) {
        StringBuilder report = new StringBuilder();
        report.append(formatter.formatProduct(product)).append('\n');
        List<Review> reviews = products.get(product);
        if (reviews.isEmpty()) {
            report.append(formatter.getText("no.reviews")).append('\n');
        } else {
            report.append(reviews.stream()
                    .map(review -> formatter.formatReviews(review) + '\n')
                    .collect(Collectors.joining()));
        }
        System.out.println(report);
    }

    public void printProducts(Predicate<Product> filter, Comparator<Product> sorter) {
        StringBuilder text = new StringBuilder();
        products.keySet()
                .stream()
                .sorted(sorter)
                .filter(filter)
                .forEach(p -> text.append(formatter.formatProduct(p)).append('\n'));
        if (!text.isEmpty()) System.out.println(text);
        else System.out.println("No product items");
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

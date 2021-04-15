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

import labs.pm.exceptions.CommodityManagerException;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private final ResourceBundle config = ResourceBundle.getBundle("resources.config", Locale.getDefault());
    private final MessageFormat reviewFormat = new MessageFormat(config.getString("review.data.format"));
    private final MessageFormat productFormat = new MessageFormat(config.getString("product.data.format"));
    private final Path reportsFolder = Path.of(config.getString("reports.folder"));
    private final Path dataFolder = Path.of(config.getString("data.folder"));
    private final Path tempFolder = Path.of(config.getString("temp.folder"));
    private static final CommodityManager instance = new CommodityManager();

    /**
     * HashMap containing all the localizations supported by the application
     * Not the final option!
     * <p>
     * <p>TO DO: </p>
     * <p>Add a nesting level so that the getSupportedLocales () method displays localizations as:</p>
     * <li>"English UK"</li>
     * <li>"English US"</li>
     * <li>"Русский РФ"</li>
     * <li>etc.</li>
     */
    private static final Map<String, ResourceFormatter> formatters = Map.of(
            "en-US", new ResourceFormatter(Locale.US),
            "en-UK", new ResourceFormatter(Locale.UK),
            "ru-RU", new ResourceFormatter(new Locale("ru", "RU")));

    private static final Logger logger = Logger.getLogger(CommodityManager.class.getName());

    /**
     * Default constructor method. Sets default locale
     */
    private CommodityManager() {
        loadAllData();
    }

    public static CommodityManager getInstance() {
        return instance;
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
     * @return
     */
    public Map<String, String> getDiscounts(final String languageTag) {
        ResourceFormatter formatter = formatters.getOrDefault(languageTag, formatters.get("ru-RU"));
        return products.keySet()
                .stream()
                .collect(Collectors.groupingBy(
                        product -> product.getName() + "\t" + product.getRating().getStars(),
                        Collectors.collectingAndThen(
                                Collectors.summingDouble(product -> product.getDiscount().doubleValue()),
                                discount -> formatter.moneyFormat.format(discount))));
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

    public Product findProductById(final int id) throws CommodityManagerException {
        return products
                .keySet()
                .stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElseThrow(() -> new CommodityManagerException("Wrong! Product item with id " + id + " no such!"));
    }

    public Product reviewProduct(final int id, final Rating rating, final String comment) {
        try {
            return reviewProduct(findProductById(id), rating, comment);
        } catch (CommodityManagerException e) {
            logger.log(Level.INFO, e.getMessage());
        }
        return null;
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

    public void printProductReport(final int id, final String languageTag) {
        try {
            printProductReport(findProductById(id), languageTag);
        } catch (CommodityManagerException e) {
            logger.log(Level.INFO, e.getLocalizedMessage() + "\n");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error printing product report " + e.getMessage(), e);
        }
    }

    public void printProductReport(final Product product, final String languageTag) throws IOException {
        ResourceFormatter formatter = formatters.getOrDefault(languageTag, formatters.get("ru-RU"));
        List<Review> reviews = products.get(product);
        Path reportFile = reportsFolder.resolve(
                MessageFormat.format(config.getString("report.file"), product.getId()));
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(
                Files.newOutputStream(reportFile, StandardOpenOption.CREATE), "UTF-8"))) {
            out.append(formatter.formatProduct(product) + System.lineSeparator());
            if (reviews.isEmpty()) {
                out.append(formatter.getText("no.reviews"));
            } else {
                out.append(reviews.stream()
                        .map(review -> formatter.formatReviews(review) + System.lineSeparator())
                        .collect(Collectors.joining()));
            }
        }
    }

    public void printProducts(Predicate<Product> filter, Comparator<Product> sorter) {
        String text = products.keySet()
                .stream()
                .sorted(sorter)
                .filter(filter)
                .map(p -> p.toString())
                .collect(Collectors.joining("\n"));
        if (!text.isEmpty()) System.out.println(text + "\n");
        else System.out.println("No product items" + "\n");
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
        dumpProduct(product);
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
        dumpProduct(product);
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
        dumpProduct(product);
        return product;
    }

    private void dumpProduct(Product product) {
        products.put(product, new ArrayList<>());
        Path productFile = dataFolder.resolve(
                MessageFormat.format(config.getString("product.data.file"), product.getId()));
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(
                Files.newOutputStream(productFile, StandardOpenOption.CREATE), "UTF-8"))) {
            String productSaveFormat = MessageFormat.format(
                    config.getString("product.data.format"),
                    product.getClass().getSimpleName().toString().toUpperCase(),
                    product.getId(),
                    product.getName(),
                    product.getPrice().toString(),
                    product.getRating().ordinal(),
                    product.getPercentageDiscount(),
                    product.getBestBefore().toString());
            out.append(productSaveFormat);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error create product file " + e.getMessage(), e);
        }
    }

    private void loadAllData() {
        try {
            products = Files.list(dataFolder)
                    .filter(file -> file.getFileName().toString().startsWith("product"))
                    .map(file -> loadProduct(file))
                    .filter(product -> product != null)
                    .collect(Collectors.toMap(product -> product, product -> loadReviews(product)));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading all data " + e.getMessage(), e);
        }
    }

    private List<Review> loadReviews(Product product) {
        List<Review> reviews = null;
        Path reviewFile = dataFolder.resolve(
                MessageFormat.format(config.getString("reviews.data.file"), product.getId()));
        if (Files.notExists(reviewFile)) reviews = new ArrayList<>();
        else {
            try {
                reviews = Files.lines(reviewFile, Charset.forName("UTF-8"))
                        .map(text -> parseReview(text))
                        .filter(review -> review != null)
                        .sorted((p1, p2) -> p2.getRating().ordinal() - p1.getRating().ordinal())
                        .collect(Collectors.toList());
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Warning! Error loading product review " + e.getMessage(), e);
            }
        }
        return reviews;
    }

    private Product loadProduct(Path file) {
        Product product = null;
        try {
            product = parseProduct(
                    Files.lines(dataFolder.resolve(file), Charset.forName("UTF-8"))
                            .findFirst().orElseThrow());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Warning! Error loading product! " + e.getMessage(), e);
        }
        return product;
    }

    private Review parseReview(String text) {
        Review review = null;
        try {
            Object[] value = reviewFormat.parse(text);
            review = new Review(Rateable.convert(Integer.parseInt((String) value[0])), (String) value[1]);
        } catch (ParseException | NumberFormatException e) {
            logger.log(Level.WARNING, "Warning! Error parsing review \"" + text + "\" ");
        }
        return review;
    }

    private Product parseProduct(String text) {
        Product product = null;
        try {
            Object[] value = productFormat.parse(text);
            ProductType productType = ProductType.valueOf((String) value[0]);
            int id = Integer.parseInt((String) value[1]);
            String name = (String) value[2];
            BigDecimal price = new BigDecimal((String) value[3]);
            Rating rating = Rateable.convert(Integer.parseInt((String) value[4]));
            BigDecimal discountRate = new BigDecimal((String) value[5]);
            LocalDate bestBefore = LocalDate.now();
            if (productType == ProductType.FOOD) bestBefore = LocalDate.parse((String) value[6]);
            product = switch (productType) {
                case DRINK -> new Drink(id, name, price, rating, discountRate);
                case FOOD -> new Food(id, name, price, rating, discountRate, bestBefore);
                case NONFOOD -> new NonFood(id, name, price, rating, discountRate);
            };
        } catch (ParseException | NumberFormatException | DateTimeParseException e) {
            logger.log(Level.WARNING, "Warning! Error parsing product \"" + text + "\" " + e.getLocalizedMessage());
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Warning! Product type not correct to  \"" + text + "\" " + e.getLocalizedMessage());
        }
        return product;
    }

    private void dumpData() {

    }

    @SuppressWarnings("unchecked")
    private void restoreData() {

    }

    private static class ResourceFormatter {

        private final Locale locale;
        private final ResourceBundle resource;
        private final DateTimeFormatter dateFormat;
        private final NumberFormat moneyFormat;

        private ResourceFormatter(final Locale locale) {
            this.locale = locale;
            resource = ResourceBundle.getBundle("resources.productbundle", locale);
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

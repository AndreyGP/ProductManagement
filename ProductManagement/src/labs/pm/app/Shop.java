package labs.pm.app;

import labs.pm.data.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;


/**
 * <h2>Main file Product Management</h2>
 * <p>ProductManagement Created by Home Work Studio AndrHey [andreigp]</p>
 * <p>FileName: Shop.java</p>
 * <p>Date/time: 21 март 2021 in 18:17</p>
 *
 * @author Andrei G. Pastushenko
 */

public class Shop {
    public static void main(String[] args) {
//        Predicate<Product> negativeRatingFilter = p -> p.getRating().ordinal() <= 3;
//        Predicate<Product> positiveRatingFilter = p -> p.getRating().ordinal() > 3;
//        Predicate<Product> allRatingFilter = p -> true;
//
//        Comparator<Product> ratingSorter = (p1, p2) -> p2.getRating().ordinal() - p1.getRating().ordinal();
//        Comparator<Product> priceSorter = (p1, p2) -> p2.getPrice().compareTo(p1.getPrice());
//        Comparator<Product> idSorter = (p1, p2) -> p1.getId() - p2.getId();
//        Comparator<Product> ratingThenPriceAscSorter = ratingSorter.thenComparing(priceSorter);
//        Comparator<Product> ratingThenPriceDescSorter = ratingThenPriceAscSorter.reversed();

//        System.out.println("This app supports the following languages:");
//        CommodityManager.getSupportedLocales().forEach(System.out::println);

        CommodityManager cm = CommodityManager.getInstance();
    }
}

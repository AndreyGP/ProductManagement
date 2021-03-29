package labs.pm.app;

import labs.pm.data.*;

import java.time.LocalDate;

import static java.math.BigDecimal.valueOf;
import static java.util.Locale.*;
import static java.util.ResourceBundle.getBundle;
import static java.text.MessageFormat.format;
import static labs.pm.data.Rating.*;

/**
 * <p>ProductManagement Created by Home Work Studio AndrHey [andreigp]</p>
 * <p>FileName: Shop.java</p>
 * <p>Date/time: 21 март 2021 in 18:17</p>
 *
 * @author Andrei G. Pastushenko
 */

public class Shop {
    public static void main(String[] args) {
        Drink p1 = new Drink("Tea", valueOf(89.99), NOT_RATED);
        Food p2 = new Food("Bun", valueOf(69.99), NOT_RATED, LocalDate.now().plusDays(7));

        String pattern = getBundle("resources.descriptions", US).getString("product");

        String product = format(pattern, p1.getId(), p1.getName(), p1.getPrice(), p1.getDiscount(), p1.getRating().getStars());
        System.out.println(product);
        product = format(pattern, p2.getId(), p2.getName(), p2.getPrice(), p2.getDiscount(), p2.getRating().getStars());
        System.out.println(product);

        System.out.println();
        System.out.println("Change some parameter");

        p1 = p1.applyPrice(valueOf(89.99))
                .applyDiscountRate(7)
                .applyRating(FOUR_STARS);
        p2 = p2.applyName("Fancy bread")
                .applyRating(TWO_STARS)
                .applyPrice(valueOf(75.08));

        product = format(pattern, p1.getId(), p1.getName(), p1.getPrice(), p1.getDiscount(), p1.getRating().getStars());
        System.out.println(product);
        product = format(pattern, p2.getId(), p2.getName(), p2.getPrice(), p2.getDiscount(), p2.getRating().getStars());
        System.out.println(product);

        System.out.println();
        System.out.println("""
                Product presentation:
                """);

        System.out.println("Drink:");
        System.out.print("String type --> ");
        System.out.println(p1.toString());
        System.out.print("JSON type --> ");
        System.out.println(p1.toStringJSON());

        System.out.println("Food:");
        System.out.print("String type --> ");
        System.out.println(p2.toString());
        System.out.print("JSON type --> ");
        System.out.println(p2.toStringJSON());

        System.out.println();
        System.out.println(p1 instanceof CommodityUnit ? "This object Drink instance of CommodityUnit" : "This object not instance of CommodityUnit");
        System.out.println(p2 instanceof CommodityUnit ? "This object Food instance of CommodityUnit" : "This object not instance of CommodityUnit");
    }

}

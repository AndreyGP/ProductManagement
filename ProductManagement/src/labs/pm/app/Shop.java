package labs.pm.app;

import labs.pm.data.Product;
import labs.pm.data.Rating;

import static java.math.BigDecimal.valueOf;
import static java.util.Locale.*;
import static java.util.ResourceBundle.getBundle;
import static java.text.MessageFormat.format;
import static labs.pm.data.Rating.*;

/**
 * <p>ProductManagement Created by Home Work Studio AndrHey [andreigp]</p>
 * <p>FileName: Shop.java</p>
 * <p>Date/time: 21 март 2021 in 18:17</p>
 * @author Andrei G. Pastushenko
 */

public class Shop {
    public static void main(String[] args) {
        Product p1 = new Product("Tea", valueOf(89.99), NOT_RATED);
        String pattern = getBundle("resources.descriptions", US).getString("product");
        String product = format(pattern, p1.getId(), p1.getName(), p1.getPrice(), p1.getDiscount(), p1.getRating().getStars());
        System.out.println(product);
        p1 = p1.applyDiscountRate(10);
        product = format(pattern, p1.getId(), p1.getName(), p1.getPrice(), p1.getDiscount(), p1.getRating().getStars());
        System.out.println(product);
    }

}

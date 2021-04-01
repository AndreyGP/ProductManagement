package labs.pm.app;

import labs.pm.data.*;

import java.math.BigDecimal;
import java.time.LocalDate;


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
        CommodityManager cm = new CommodityManager();
        Product drink = cm.createNewProduct("Juice", 76.99, ProductType.DRINK);
        cm.printProductReport();
        System.out.println();
        
        drink = cm.reviewProduct(drink, Rating.FOUR_STARS, "А сочок-то ничёвский!");
        cm.printProductReport();
        System.out.println();

        Product food = cm.createNewProduct("Chocolate", 99.89, ProductType.FOOD);
        Product nonfood = cm.createNewProduct("Fairy", 176.99, ProductType.NONFOOD);

        System.out.println(drink.toString());
        System.out.println(food.toString());
        System.out.println(nonfood.toString());

        Food food1 = (Food) food;
        System.out.println(food1.getBestBefore());
        System.out.println(((Food) food).getBestBefore());

        Product choko = (Food) food.applyRating(5);
        System.out.println(choko);
        System.out.println(choko instanceof Food);
        
//        Product failed = new Drink("Water", BigDecimal.valueOf(99.00), Rating.NOT_RATED);
    }

}

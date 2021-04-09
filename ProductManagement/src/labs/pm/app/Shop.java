package labs.pm.app;

import labs.pm.data.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;


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
        System.out.println("This app supports the following languages:");
        CommodityManager.getSupportedLocales().forEach(System.out::println);
        System.out.println();

        CommodityManager cm = new CommodityManager("ru-RU");
        Product drink = cm.createNewProduct("Juice", 76.99, ProductType.DRINK);
//        cm.printProductReport(drink);
        drink = cm.reviewProduct(drink, Rating.TWO_STARS, "Бычья моча!");
        drink = cm.reviewProduct(drink, Rating.FOUR_STARS, "А сочок-то ничёвский!");
        drink = cm.reviewProduct(drink, Rating.FIVE_STARS, "Отличный сок!");
        drink = cm.reviewProduct(drink, Rating.THREE_STARS, "Ну тетрапак дешёвый я и сам себе налью дешевле!");
        drink = cm.reviewProduct(drink, Rating.ONE_STARS, "В соке была муха!");
        drink = cm.reviewProduct(drink, Rating.FOUR_STARS, "Не свежевыжатый, но тоже норм!");
        drink = cm.reviewProduct(drink, Rating.FOUR_STARS, "Пробовал лучше, но и этот норм!");
        drink = cm.reviewProduct(drink, Rating.FIVE_STARS, "Сегодня был свежевыжатый!");
        drink = cm.reviewProduct(drink, Rating.THREE_STARS, "Сегодня не свежевыжатый был!");
        cm.reviewProduct(drink, Rating.FOUR_STARS, "Выше среднего сок!");
//        cm.printProductReport(drink);


        int FoodId = cm.createNewProduct("Cookie", 66.99, ProductType.FOOD).getId();
//        cm.printProductReport(FoodId);
        cm.reviewProduct(FoodId, Rating.TWO_STARS, "Пресное и крошится!");
        cm.reviewProduct(FoodId, Rating.THREE_STARS, "Только со сладким чаем можно есть!");
        cm.reviewProduct(FoodId, Rating.FOUR_STARS, "За такую цену пойдёт!");
        cm.reviewProduct(FoodId, Rating.THREE_STARS, "Дешёвый средняк!");
        cm.reviewProduct(FoodId, Rating.ONE_STARS, "Кто это ещё покупает?!");
        cm.reviewProduct(FoodId, Rating.FOUR_STARS, "Ну так себе, а упаковка красивая!");
        cm.reviewProduct(FoodId, Rating.FOUR_STARS, "Дешёвое и диетическое!");
        cm.reviewProduct(FoodId, Rating.FIVE_STARS, "Голубей кормить самое то!");
        cm.reviewProduct(FoodId, Rating.THREE_STARS, "Как совдеповские галеты!");
        cm.reviewProduct(FoodId, Rating.TWO_STARS, "Вспомнил далёкое серое детство!");
//        cm.printProductReport(FoodId);

        cm.changeLocal("en-US");

        int nonFoodId = cm.createNewProduct("Fairy", 79.99, ProductType.FOOD).getId();
//        cm.printProductReport(nonFoodId);
        cm.reviewProduct(nonFoodId, Rating.FOUR_STARS, "Хорошее средство!");
        cm.reviewProduct(nonFoodId, Rating.THREE_STARS, "Я из Виллабаджо!");
        cm.reviewProduct(nonFoodId, Rating.FOUR_STARS, "За такую цену пойдёт!");
        cm.reviewProduct(nonFoodId, Rating.FOUR_STARS, "Не то уже, но ещё хорошо отмывает!");
        cm.reviewProduct(nonFoodId, Rating.FIVE_STARS, "Я из Вилларибо!");
        cm.reviewProduct(nonFoodId, Rating.FOUR_STARS, "Ну так себе, а упаковка красивая!");
        cm.reviewProduct(nonFoodId, Rating.FOUR_STARS, "Не сушит руки!");
        cm.reviewProduct(nonFoodId, Rating.FIVE_STARS, "С аллоэ прям лучше не найти!");
        cm.reviewProduct(nonFoodId, Rating.FIVE_STARS, "Я тоже из Вилларибо!");
        cm.reviewProduct(nonFoodId, Rating.FIVE_STARS, "Я даже похудела, как оно борется с жиром!");
//        cm.printProductReport(nonFoodId);

//        cm.printProductReport(1);

        Comparator<Product> ratingSorter = (p1, p2) -> p2.getRating().ordinal() - p1.getRating().ordinal();
        Comparator<Product> priceSorter = (p1, p2) -> p2.getPrice().compareTo(p1.getPrice());
        Comparator<Product> idSorter = (p1, p2) -> p1.getId() - p2.getId();
        cm.printProducts(ratingSorter);
        cm.printProducts(priceSorter);
        cm.printProducts(idSorter);
        cm.printProducts(ratingSorter.thenComparing(priceSorter));
        cm.printProducts(ratingSorter.thenComparing(priceSorter).reversed());
    }
}

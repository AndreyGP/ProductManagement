package labs.pm.app;

import labs.pm.data.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
        /**
         * TO DO : Move filtering and sorting logic into separate enum files
         */
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

        AtomicInteger clientCount = new AtomicInteger(0);
        Callable<String> client = () -> {
            String clientId = "Client" + clientCount.incrementAndGet();
            String threadName = Thread.currentThread().getName();
            int productId = ThreadLocalRandom.current().nextInt(3) + 1;
            int rating = ThreadLocalRandom.current().nextInt(5) + 1;
            String languageTag = CommodityManager.getSupportedLocales()
                    .stream()
                    .skip(ThreadLocalRandom.current().nextInt(2))
                    .findFirst().get();
            StringBuilder log = new StringBuilder();
            log.append(clientId + " " + threadName + "\n-\tstart of log\t-\n");
            log.append(cm.getDiscounts(languageTag)
                    .entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + "\t" + entry.getValue())
                    .collect(Collectors.joining(System.lineSeparator())));
            Product product = cm.reviewProduct(productId, Rateable.convert(rating), "Some another review");
            cm.printProductReport(productId, languageTag, threadName);
            log.append((product != null)
                    ? "\nProduct " + productId + " reviewed\n"
                    : "\nProduct " + productId + " not reviewed\n");
            log.append(clientId + " generated report for " + productId + " product");
            log.append("\n-\tend of log\t-\n");
            return log.toString();
        };

        List<Callable<String>> clients = Stream.generate(() -> client)
                .limit(24)
                .collect(Collectors.toList());
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        try {
            List<Future<String>> results = executorService.invokeAll(clients);
            executorService.shutdown();
            results.stream().forEach(result -> {
                try {
                    System.out.println(result.get());
                } catch (InterruptedException | ExecutionException e) {
                    Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, "Error retrieving client log " + e.getMessage(), e);
                }
            });
        } catch (InterruptedException e) {
            Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, "Error invoking clients " + e.getMessage(), e);
        }
    }
}

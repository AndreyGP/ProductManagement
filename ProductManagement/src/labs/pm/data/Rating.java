package labs.pm.data;

/**
 * <p>ProductManagement Created by Home Work Studio </p>
 * FileName: Rating.java
 * @see java.lang.Enum
 * @author Andrei G. Pastushenko : 21 март 2021 in 23:48
 */

public enum Rating {
    NOT_RATED("\u2606\u2606\u2606\u2606\u2606"),
    ONE_STARS("\u2605\u2606\u2606\u2606\u2606"),
    TWO_STARS("\u2605\u2605\u2606\u2606\u2606"),
    THREE_STARS("\u2605\u2605\u2605\u2606\u2606"),
    FOUR_STARS("\u2605\u2605\u2605\u2605\u2606"),
    FIVE_STARS("\u2605\u2605\u2605\u2605\u2605");
    private final String stars;

    private Rating(String stars) {
        this.stars = stars;
    }

    String getStars() {
        return this.stars;
    }
}

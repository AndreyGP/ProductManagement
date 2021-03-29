package labs.pm.data;

/**
 * <p>ProductManagement Created by Home Work Studio </p>
 * FileName: Rating.java
 * @see java.lang.Enum
 * @author Andrei G. Pastushenko : 21 март 2021 in 23:48
 */

public enum Rating {
    NOT_RATED("\u2606\u2606\u2606\u2606\u2606", 0),
    ONE_STARS("\u2605\u2606\u2606\u2606\u2606", 1),
    TWO_STARS("\u2605\u2605\u2606\u2606\u2606", 2),
    THREE_STARS("\u2605\u2605\u2605\u2606\u2606", 3),
    FOUR_STARS("\u2605\u2605\u2605\u2605\u2606", 4),
    FIVE_STARS("\u2605\u2605\u2605\u2605\u2605", 5);
    private final String stars;
    private final int ratingLevel;

    private Rating(String stars, int ratingLevel) {
        this.stars = stars;
        this.ratingLevel = ratingLevel;
    }

    public String getStars() {
        return this.stars;
    }

    public int getRatingLevel() {
        return this.ratingLevel;
    }
}

/**
 * Dinh Nguyen Hai Dang - B1704721
 */
package sources;

import java.util.Random;

public class RandomNumberGenerator {

    public static int rng(int boundary) {
        Random rng = new Random();
        return rng.nextInt(boundary);
    }

    public static int rng(int min, int max) {
        Random rng = new Random();
        return rng.nextInt((max - min) + 1) + min;
    }
}

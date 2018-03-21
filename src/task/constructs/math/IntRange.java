package task.constructs.math;

/**
 * Used to store integer ranges as an object. The bounds are included in the range.
 */
public class IntRange {
    private int lowerBound;
    private int upperBound;

    /**
     * Instantiates a simple Integer-Range.
     * @param lowerBound The lower (included) bound of the Integer range.
     * @param upperBound The upper (included) bound of the Integer range.
     */
    public IntRange(int lowerBound, int upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    /* ****** GETTERS ****** */

    /**
     * Gets the lower bound of the range.
     * @return Returns the lower bound.
     */
    public int getLowerBound() {
        return lowerBound;
    }

    /**
     * Gets the upper bound of the range.
     * @return Returns the upper bound.
     */
    public int getUpperBound() {
        return upperBound;
    }


    /* ****** SETTERS ****** */

    /**
     * Sets the lower (included) bound of the Integer range.
     * @param lowerBound The new (included) lower bound of the range.
     */
    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    /**
     * Sets the upper (included) bound of the Integer range.
     * @param upperBound The new (included) upper bound of the range.
     */
    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }
}

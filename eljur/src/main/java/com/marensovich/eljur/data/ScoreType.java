package com.marensovich.eljur.data;

/**
 * The enum Score type.
 */
public enum ScoreType {
    /**
     * Two score type.
     */
    TWO(2),
    /**
     * Three score type.
     */
    THREE(3),
    /**
     * Four score type.
     */
    FOUR(4),
    /**
     * Five score type.
     */
    FIVE(5);

    private final int score;

    ScoreType(int score) {
        this.score = score;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

}

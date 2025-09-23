package com.marensovich.eljur.data;

/**
 * Represents basic grade types (2–5).
 *
 * <p>Used for regular assessments in the system.</p>
 */
public enum ScoreType {

    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int score;

    ScoreType(int score) {
        this.score = score;
    }

    /**
     * Gets numeric score.
     *
     * @return the score value
     */
    public int getScore() {
        return score;
    }
}

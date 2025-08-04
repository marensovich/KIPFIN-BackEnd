package com.marensovich.eljur.data;

public enum ScoreType {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int score;

    ScoreType(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

}

package com.marensovich.eljur.data;

/**
 * The enum Score work type.
 */
public enum ScoreWorkType {

    /**
     * Digital homework assignment score work type.
     */
    Digital_Homework_Assignment("Цифровое домашнее задание"),
    /**
     * Homework score work type.
     */
    Homework("Домашнее задание"),
    /**
     * Survey score work type.
     */
    Survey("Опрос"),
    /**
     * Test score work type.
     */
    Test("Тест"),
    /**
     * Educational work score work type.
     */
    Educational_work("Учебная работа"),
    /**
     * Oral interview score work type.
     */
    Oral_interview("Устный опрос"),
    /**
     * Written survey score work type.
     */
    Written_survey("Письменный опрос"),
    /**
     * Dialogue score work type.
     */
    Dialogue("Диалог"),
    /**
     * Polylogue score work type.
     */
    Polylogue("Полилог"),
    /**
     * Practical work score work type.
     */
    Practical_work("Практическая работа"),
    /**
     * Control work score work type.
     */
    Control_work("Контрольная работа"),
    /**
     * Creative work score work type.
     */
    Creative_work("Творческая работа"),
    /**
     * Research work score work type.
     */
    Research_work("Исследовательская работа"),
    /**
     * Dictation score work type.
     */
    Dictation("Диктант"),
    /**
     * Presentation score work type.
     */
    Presentation("Изложение"),
    /**
     * Essay score work type.
     */
    Essay("Сочинение"),
    /**
     * Grammar assignment score work type.
     */
    Grammar_assignment("Грамматическое задание"),
    /**
     * Task solving score work type.
     */
    task_solving("Решение задач"),
    /**
     * Educational exercise score work type.
     */
    Educational_exercise("Учебное упражнение"),
    /**
     * Study assignment score work type.
     */
    Study_assignment("Учебное задание"),
    /**
     * Keeping a notebook score work type.
     */
    Keeping_a_notebook("Ведение тетради"),
    /**
     * Combined work score work type.
     */
    Combined_work("Комбинированная работа"),
    /**
     * Report score work type.
     */
    Report("Доклад");

    private final String scoreWorkType;

    ScoreWorkType(String scoreWorkType) {
        this.scoreWorkType = scoreWorkType;
    }

    /**
     * Gets score work type.
     *
     * @return the score work type
     */
    public String getScoreWorkType() {
        return scoreWorkType;
    }
}
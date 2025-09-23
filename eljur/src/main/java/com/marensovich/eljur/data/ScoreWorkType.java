package com.marensovich.eljur.data;

/**
 * Represents the types of academic work for which scores can be assigned.
 *
 * <p>Each constant stores a human-readable description.</p>
 */
public enum ScoreWorkType {

    Digital_Homework_Assignment("Цифровое домашнее задание"),
    Homework("Домашнее задание"),
    Survey("Опрос"),
    Test("Тест"),
    Educational_work("Учебная работа"),
    Oral_interview("Устный опрос"),
    Written_survey("Письменный опрос"),
    Dialogue("Диалог"),
    Polylogue("Полилог"),
    Practical_work("Практическая работа"),
    Control_work("Контрольная работа"),
    Creative_work("Творческая работа"),
    Research_work("Исследовательская работа"),
    Dictation("Диктант"),
    Presentation("Изложение"),
    Essay("Сочинение"),
    Grammar_assignment("Грамматическое задание"),
    task_solving("Решение задач"),
    Educational_exercise("Учебное упражнение"),
    Study_assignment("Учебное задание"),
    Keeping_a_notebook("Ведение тетради"),
    Combined_work("Комбинированная работа"),
    Report("Доклад");

    private final String scoreWorkType;

    ScoreWorkType(String scoreWorkType) {
        this.scoreWorkType = scoreWorkType;
    }

    /**
     * Gets the description of the work type.
     *
     * @return the description string
     */
    public String getScoreWorkType() {
        return scoreWorkType;
    }
}

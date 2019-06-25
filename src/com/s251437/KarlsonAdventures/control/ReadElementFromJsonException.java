package com.s251437.KarlsonAdventures.control;

public class ReadElementFromJsonException extends Exception {
    public String toString() {
        return "Ошибка, элемент задан неверно, возможно вы указали не все значения.\n" +
                "Требуется указать следующие поля: name, speed, currentLoc.\n";
    }
}
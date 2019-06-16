package com.s251437.KarlsonAdventures.control;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.s251437.KarlsonAdventures.Kid;
import com.s251437.KarlsonAdventures.control.CollectionManager;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandHandler {
    private CollectionManager manager;
    private boolean isStopped;

    public CommandHandler(CollectionManager collectionManager) {
        manager = collectionManager;
        if (collectionManager != null) manager = collectionManager;
        isStopped = false;
    }



    private Kid getElementFromJSON(Gson gson, String elementInString) throws ReadElementFromJsonException, JsonSyntaxException {
        Kid element = gson.fromJson(elementInString, Kid.class);
        if (element == null || element.getName() == null || element.getAge() == 0) {
            throw new ReadElementFromJsonException();
        }
        return element;
    }

    private class ReadElementFromJsonException extends Exception {
        public String toString() {
            return "Ошибка, элемент задан неверно, возможно вы указали не все значения.\n" +
                    "Требуется указать следующие поля: name, speed, currentLoc.\n";
        }
    }

    public String control(String command) {
            String[] fullCommand = command.trim().split(" ", 2);;
            Gson gson = new Gson();
            String answer;

            switch (fullCommand[0]) {
                case "remove_lower":
                case "remove":
                case "add":
                case "add_if_min":
                    try {
                        Kid element = getElementFromJSON(gson, fullCommand[1]);
                        switch (fullCommand[0]) {
                            case "remove":
                                return manager.remove(element);
                            case "remove_lower":
                                return manager.removeLower(element);
                            case "add_if_min":
                                return manager.addIfMin(element);
                            case "add":
                                return manager.add(element);
                        }
                    } catch (JsonSyntaxException ex) {
                        return "Ошибка, элемент задан неверно. Используйте формат JSON.";
                    } catch (ReadElementFromJsonException ex) {
                        return ex.toString();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        return "Неверный аргумент.";
                    }
                    break;
                case "import":
                    try {
                        return manager.importItem(fullCommand[1]);
                    }
                    catch (ArrayIndexOutOfBoundsException e) {
                        return "Неверный аргумент.";
                    }
                case "info":
                    return manager.info();
                case "show":
                    return manager.show();
                case "stop":
                    manager.show();
                    isStopped = true;
                    return manager.finishWork();
                default:
                    return "Ошибка, Неизвестная команда.";

            }
        return "Ошибка, Неизвестная команда.";
    }
}
package com.s251437.KarlsonAdventures.control;



import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.s251437.KarlsonAdventures.Kid;

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

    private String[] readCommand() {
        Scanner scanner = new Scanner(System.in);
        String command;

        try {
            System.out.println("Введите команду");
            command = scanner.nextLine();
        } catch (NoSuchElementException ex) {
            command = "stop";
        }

        /*if (fullCommand.length > 1) {
            while (fullCommand[1].contains(" ")) {
                fullCommand[1] = fullCommand[1].replaceAll(" ", " ");
            }
        }*/

        return command.trim().split(" ", 2);

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

    public void control() {
        while (!isStopped) {
            String[] fullCommand = readCommand();
            Gson gson = new Gson();

            switch (fullCommand[0]) {
                case "remove_lower":
                case "remove":
                case "add":
                case "add_if_min":
                    try {
                        Kid element = getElementFromJSON(gson, fullCommand[1]);
                        switch (fullCommand[0]) {
                            case "remove":
                                manager.remove(element);
                            case "remove_lower":
                                manager.removeLower(element);
                                break;
                            case "add_if_min":
                                manager.addIfMin(element);
                                break;
                            case "add":
                                manager.add(element);
                                break;
                        }
                    } catch (JsonSyntaxException ex) {
                        System.out.println("Ошибка, элемент задан неверно. Используйте формат JSON.");
                    } catch (ReadElementFromJsonException ex) {
                        System.out.print(ex.toString());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Неверный аргумент.");
                    }
                    break;
                case "import":
                    try {
                        manager.importItem(fullCommand[1]);
                    }
                    catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Неверный аргумент.");
                    }
                    break;
                case "info":
                    manager.info();
                    break;
                case "show":
                    manager.show();
                    break;
                case "stop":
                    manager.show();
                    isStopped = true;
                    manager.finishWork();
                    break;
                default:
                    System.out.println("Ошибка, Неизвестная команда.");
                    break;
            }
        }
    }
}
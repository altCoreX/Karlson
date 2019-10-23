package com.s251437.KarlsonAdventures.control;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.s251437.KarlsonAdventures.journey.Kid;

public class CommandHandler {
    protected CollectionManager manager;

    public CommandHandler(CollectionManager collectionManager) {
        manager = collectionManager;
        if (collectionManager != null) manager = collectionManager;
    }

    public String control(String command) {
        String[] fullCommand = command.trim().split(" ", 2);
        return commandSwitcher(fullCommand);
    }

    private String commandSwitcher(String[] fullCommand){
        String collectionAnswer = collectionControl(fullCommand);
        if (!collectionAnswer.equals("null")){
            return collectionAnswer;
        } else {
            return "Ошибка, Неизвестная команда.";
        }
    }

    private String collectionControl(String[] fullCommand) {
        Gson gson = new Gson();
        try {
        switch (fullCommand[0]) {
            case "remove_lower":
            case "remove":
            case "add":
            case "add_if_min":
                try {
                    Kid element = Utils.getElementFromJSON(gson, fullCommand[1]);
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
                    return manager.importItem(fullCommand[1]);
            case "info":
                return manager.info();
            case "show":
                return manager.show();
            case "stop":
                return manager.finishWork();
            case "save":
                return manager.save();


        }
        return "null";
        } catch (ArrayIndexOutOfBoundsException e) {
            return "Неверный аргумент.";
        }
    }

}
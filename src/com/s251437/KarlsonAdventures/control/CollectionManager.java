package com.s251437.KarlsonAdventures.control;

import com.s251437.KarlsonAdventures.Kid;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class CollectionManager {
    private ConcurrentSkipListSet<Kid> collection;
    private Date initDate;
    private File fileForIO;

    private XmlReaderAndWriter manager = new XmlReaderAndWriter();

    CollectionManager(File collectionFile) {
        initDate = new Date();
        collection = new ConcurrentSkipListSet<Kid>();
        if (collectionFile != null && !importFile(collectionFile)){
            fileForIO = collectionFile;
            importFile(fileForIO);
        }
    }

    public CollectionManager() {
        try{
            fileForIO = new File(System.getenv("labFile"));
        }
        catch (NullPointerException e){
            System.out.println("Переменная окружения не задана.");
            System.exit(0);
        }
        initDate = new Date();
        collection = new ConcurrentSkipListSet<Kid>();
        importFile(fileForIO);
    }

    /**
     * Method to remove elements from collection.
     * @param element   :(Kid) - element to remove.
     */

    public String remove(Kid element){
         if(collection.remove(element))
            return "Элемент удален";
         else
            return "Такого элемента не существует";

    }

    /**
     * Method to show elements in collection.
     */

    public String show(){
        return collection.toString();
    }

    /**
     * Method to add element to collection if this element is lower than minimal element in the collection.
     * @param element   :(Kid) - element to compare.
     */

    public String addIfMin(Kid element){
        if(element.compareTo(Collections.min(collection)) < 0)
            if(collection.add(element)){
                return "Элемент добавлен.";
            }
        return "Элемент не добавлен.";
    }

    /**
     * Method to add elements to collection.
     * @param element   :(Kid) - element to add.
     */

    public String add(Kid element) {
        if (collection.add(element)) {
            return "Элемент добавлен.";
        }
        else {
            return "Элемент не добавлен.";
        }
    }

    /**
     * Method to import collection from file.
     * @param importFile    :(File) File which is using to IO.
     * @return              :(boolean) Returns true if file wis imported.
     */

    boolean importFile(File importFile) {
        boolean readed = manager.read(collection, importFile);
        if(readed){
            System.out.println("Элементы добавлены.");
        }
        else{
            System.out.println("Элементы не добавлены.");
        }
        return readed;
    }

    /**
     * Method to remove elements from collection if this elements are smaller than given.
     * @param element   :(Kid) - element to compare.
     */

    public String removeLower(Kid element){
        collection.removeIf(n -> n.compareTo(element) < 0);
        return "Готово.";
    }

    /**
     * Method to import collection from choosen file.
     * @param filename  :(String) - Name of file to import.
     */

    public String importItem(String filename){
        if(this.importFile(new File(filename))) {
            return "Элементы добавлены.";
        }
        else {
            return "Элементы не добавлены.";
        }
    }

    /**
     * Method to display info about collection.
     */

    public String info() {
        return "Коллекция имеет тип ConcurrentSkipListSet и содержит объекты класса Kid\n" +
                "Коллекция инициализировалась на основе следующих данных: " + initDate.toString() +
                String.format("Коллекция содержит %d элементов\n", collection.size());
    }

    /**
     * Method to save collection and finish work.
     */

    public String finishWork() {
        manager.write(collection, fileForIO);
        return "Выполняется сохранение коллекции и закрытие программы.";
    }

    public ConcurrentSkipListSet<Kid> getCollection(){
        return collection;
    }
}

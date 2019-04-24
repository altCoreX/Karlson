package com.s251437.KarlsonAdventures.control;

import com.s251437.KarlsonAdventures.Kid;

import java.io.*;
import java.util.*;

public class CollectionManager {

    private HashSet<Kid> collection;
    private Date initDate;
    private File fileForIO;
    private XmlReaderAndWriter manager = new XmlReaderAndWriter();

    CollectionManager(File collectionFile) {
        initDate = new Date();
        collection = new HashSet<>();
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
        collection = new HashSet<>();
        importFile(fileForIO);
    }

    /**
     * Method to remove elements from collection.
     * @param element   :(Kid) - element to remove.
     */

    public void remove(Kid element){
         if(collection.remove(element))
            System.out.println("Элемент удален");
         else
            System.out.println("Такого элемента не существует");

    }

    /**
     * Method to show elements in collection.
     */

    public void show(){
        for(Kid item: collection){
            System.out.println(item.toString());
        }
    }

    /**
     * Method to add element to collection if this element is lower than minimal element in the collection.
     * @param element   :(Kid) - element to compare.
     */

    public void addIfMin(Kid element){
        if(element.compareTo(Collections.min(collection)) < 0)
            if(collection.add(element)){
                System.out.println("Элемент добавлен.");
            }
            else {
                System.out.println("Элемент не добавлен.");
            }
    }

    /**
     * Method to add elements to collection.
     * @param element   :(Kid) - element to add.
     */

    public void add(Kid element) {
        if (collection.add(element)) {
            System.out.println("Элемент добавлен.");
        }
        else {
            System.out.println("Элемент не добавлен.");
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

    public void removeLower(Kid element){
        collection.removeIf(n -> n.compareTo(element) < 0);
    }

    /**
     * Method to import collection from choosen file.
     * @param filename  :(String) - Name of file to import.
     */

    public void importItem(String filename){
        this.importFile(new File(filename));
    }

    /**
     * Method to display info about collection.
     */

    public void info() {
        System.out.println("Коллекция имеет тип HashSet и содержит объекты класса Kid");
        System.out.println("Коллекция инициализировалась на основе следующих данных: " + initDate);
        System.out.printf("Коллекция содержит %d элементов\n", collection.size());
    }

    /**
     * Method to save collection and finish work.
     */

    public void finishWork() {
        System.out.println("Выполняется сохранение коллекции и закрытие программы.");
        manager.write(collection, fileForIO);
    }
}

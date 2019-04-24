package com.s251437.KarlsonAdventures.control;

import com.s251437.KarlsonAdventures.Kid;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;

public class XmlReaderAndWriter {

    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;

    XmlReaderAndWriter() {
        try {

            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e){
            System.out.println("Ошибка конфигурации парсера.");
        }
    }

    boolean write(HashSet<Kid> hashSet, File exportFile){
        boolean isexported = true;
        Transformer transformer;
        Document doc = builder.newDocument();
        Element RootElement = doc.createElement("visitors");
        for(Kid kid:hashSet){
            Element NameElementTitle = doc.createElement("visitor");
            NameElementTitle.setAttribute("name", kid.getName());
            NameElementTitle.setAttribute("age", Short.toString(kid.getAge()));
            RootElement.appendChild(NameElementTitle);
        }
        doc.appendChild(RootElement);
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(exportFile)));
            System.out.println("Коллекция сохранена в файл " + exportFile.getAbsolutePath());
        }
        catch (TransformerException e){
            System.out.println("Ошибка конфигурации файла.");
            isexported = false;
        }
        catch (FileNotFoundException e) {
            try {
                exportFile.createNewFile();
            }
            catch (IOException ioe){
                System.out.println("Ошибка записи в файл.");
                isexported = false;
            }
        }

        return isexported;
    }

    boolean read(HashSet<Kid> hashSet, File importFile) {
        Document document;
        NodeList list;
        boolean imported = false;
        try {
            document = builder.parse(importFile);
            list = document.getDocumentElement().getElementsByTagName("visitor");
            for (int i = 0; i < list.getLength(); i++) {
                String name = list.item(i).getAttributes().getNamedItem("name").getNodeValue();
                byte age = Byte.parseByte(list.item(i).getAttributes().getNamedItem("age").getNodeValue());
                hashSet.add(new Kid(name, age));
                imported = true;
            }
        }
        catch (org.xml.sax.SAXException | java.io.IOException e){
            System.out.println("Ошибка ввода-вывода.");
        }
        catch (NullPointerException e){
            System.out.println("Указаны неверные поля");
        }
        catch (NumberFormatException e){
            System.out.println("Неверное значения поля \"age\"");
        }
        return imported;
    }
}
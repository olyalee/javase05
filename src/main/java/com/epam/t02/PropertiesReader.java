package com.epam.t02;

/*Задание 2. Обработка исключительных ситуаций

Создать “универсальный” класс, позволяющий получить значение из любого properties-файла. Физическое чтение файла должно происходить только один раз.
Обработайте следующие исключительные ситуации: нет файла *.properties, нет ключа в properties-файле.
*/

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class PropertiesReader {
    String fileName = "";               //c:\epam\javase05\javase05\src\resources\some.properties
    Properties properties = new Properties();

    public void dialogWithUser() {
        System.out.println("Please, enter the path of the properties-file you would like to read:");
        Scanner scanner = new Scanner(System.in);
        fileName = scanner.next();
        try {
            getProperties(fileName);
            System.out.println("Please, enter the key if you want to know a value:");
            String key = scanner.next();
            System.out.println("The value is " + getValueByKey(key) + ".");
        } catch (FileIsNotProperties fileIsNotProperties) {
            fileIsNotProperties.getMessage();
        } catch (KeyNotFound keyNotFound) {
            keyNotFound.getMessage();
        } catch (FileNotFoundException e) {
            System.out.println("Error: there is no such file...");
        } catch (IOException e) {
            System.out.println("Error: could not read the file...");
        }
    }

    public void getProperties(String fileName) throws FileIsNotProperties, IOException {
        if (!fileName.endsWith(".properties")) {
            throw new FileIsNotProperties("Sorry, but this is not a .properties file.");
        }
        try (FileInputStream fis = new FileInputStream(fileName)) {
            properties.load(fis);
        }
    }

    public String getValueByKey(String key) throws KeyNotFound {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new KeyNotFound("Could not find such key in the file...");
        } else return value;
    }

    class KeyNotFound extends Exception {

        public KeyNotFound(String text) {
            System.out.println(text);
        }
    }

    class FileIsNotProperties extends Exception {

        public FileIsNotProperties(String text) {
            System.out.println(text);
        }
    }
}
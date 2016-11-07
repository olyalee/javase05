package com.epam.t01;

/*Задание 1. Обработка исключительных ситуаций

Разработать приложение, позволяющее просматривать файлы и каталоги файловой системы, а также создавать и удалять текстовые файлы.
Для работы с текстовыми файлами необходимо реализовать функциональность записи (дозаписи) в файл.
Требуется определить исключения для каждого слоя приложения и корректно их обработать.

*/

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Scanner;

public class Explorer {
    Scanner scanner;
    static File dir;   // = new File("c:\\")
    static File file; //= new File("default.txt")

    Explorer() {
        scanner = new Scanner(System.in);
        dir = new File("c:\\");
        file = new File("default.txt");
    }

    //dialog with user
    public void dialog() {
        while (true) {
            System.out.println("Please choose the key to express your next action:");
            scanner = new Scanner(System.in);
            System.out.println(" (B)rowse directory \n (C)reate text file \n (A)dd new info to text file \n (S)how file content \n (D)elete text file \n (E)xit programm");
            String choice = (scanner.next()).toLowerCase();
            switch (choice) {
                case "b":
                    browseDirectory();
                    break;
                case "c":
                    createTextFile();
                    break;
                case "a":
                    addInTextFile();
                    break;
                case "d":
                    deleteFile();
                    break;
                case "s":
                    showFileContent();
                    break;
                case "e":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Input error");
                    break;
            }
        }
    }


    //browse the directory
    public void browseDirectory() {
        System.out.println("Please write the path for the directory you want to browse");
        String path = scanner.next();
        dir = new File(path);
        if (!dir.isDirectory()) {
            System.out.println(">>It's not a directory.");
        } else {
            String[] dirContent = dir.list();
            for (String s : dirContent) {
                System.out.println(s);
            }
        }
        System.out.println(">>The current directory is " + dir.toString());
    }

    //create new file
    public void createTextFile() {
        System.out.println("Please write the name for the file you want to create in the current directory:");
        String fileName = scanner.next();
        if (!fileName.equals("")) {
            file = new File(dir + "\\" + fileName);
            try {
                if (!file.exists()) {
                    file.createNewFile();
                    System.out.println(">>The file "+fileName+" was created in the directory "+dir.toString());
                }
                else{
                    System.out.println(">>The file with such name is already exist in the directory.");
                }
            } catch (IOException e) {
                System.out.println("Couldn't create the file.");
                e.printStackTrace();
            }

        }

    }

    //
    private void addInTextFile() {
        System.out.println("Please write the name of the file in the current directory you want to read and add a new info:");
        String fileName = scanner.next();
        try {
            ArrayList<String> tmp = getFromFile(dir + "\\" + fileName);
            System.out.println("The current content of the file " + fileName + ":");
            for (String s : tmp) {
                System.out.println(s);
            }
            System.out.println("Please write your info and it will be added to the file:");
            String add = "";
            add = new Scanner(System.in).useDelimiter("\n").next();
            writeToFile(dir + "\\" + fileName, add);
            System.out.println(">>The file was updated.");
        } catch (FileNotFoundException e) {
            System.out.println(">>There is no such file in the directory.");
        } catch (IOException e) {
            System.out.println(">>Something goes wrong: input/output error");
            e.printStackTrace();
        }

    }

    //
    private void deleteFile() {
        System.out.println("Please write the name of the file in the current directory you want to delete:");
        String fileName = scanner.next();
        file = new File(dir + "\\" + fileName);
        if (file.exists()) {
            file.delete();
            System.out.println(">>The file " + fileName + " was deleted.");
        }else {
            System.out.println(">>There is no such file in the directory.");
        }
    }

    //read from file
    public ArrayList<String> getFromFile(String from) throws FileNotFoundException {
        ArrayList<String> textLines = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(from), Charset.forName("UTF-8")))) {
            String nextLine;
            while ((nextLine = bf.readLine()) != null) {
                textLines.add(nextLine);
            }
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            System.out.println(">>Something goes wrong: input/output error");
            e.printStackTrace();
        }
        return textLines;
    }

    //write to file
    public void writeToFile(String to, String add) throws FileNotFoundException {
        try (FileWriter writer = new FileWriter(to, true)) {
            writer.append(add + " \n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            System.out.println(">>Something goes wrong: input/output error");
            e.printStackTrace();
        }
    }

    //show file's content
    public void showFileContent() {
        System.out.println("Please write the name of the file you want to read:");
        String fileName = scanner.next();
        ArrayList<String> content = null;
        try {
            content = getFromFile(dir + "\\" + fileName);
            for (String s : content) {
                System.out.println(s);
            }
        } catch (FileNotFoundException e) {
            System.out.println(">>There is no such file in the directory.");
        }

    }
}
package org.tyaa.demo.java.se.io;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    private static final Integer CREATE_FILE_OPTION = 1;
    private static final Integer WRITE_OPTION = 2;
    private static final Integer READ_OPTION = 3;
    private static final Integer EXIT_OPTION = 4;
    public static final File FILES_DIRECTORY = new File(String.format("out%sfiles", File.separator));

    public static void main(String[] args) {
        Integer option;
        do {
            System.out.println("Выберите действие и нажмите Ввод: ");
            System.out.printf("%s. Создать файл\n", CREATE_FILE_OPTION);
            System.out.printf("%s. Записать текст в файл\n", WRITE_OPTION);
            System.out.printf("%s. Прочесть текст из файла\n", READ_OPTION);
            System.out.printf("%s. Завершить работу\n", EXIT_OPTION);
            Scanner sc = new Scanner(System.in);
            String inputString;
            do {
                System.out.print("> ");
                inputString = sc.nextLine();
            } while (!inputString.matches(String.format("^[%s%s%s%s]$", CREATE_FILE_OPTION, WRITE_OPTION, READ_OPTION, EXIT_OPTION)));
            option = Integer.parseInt(inputString);
            if (option.equals(CREATE_FILE_OPTION)) {
                System.out.println("Введите имя для файла и нажмите Ввод: ");
                try {
                    OutSwitch.switchToFile();
                    if (createFile(sc.nextLine())) {
                        System.out.println(stringToLog("Файл создан."));
                    } else {
                        System.out.println(stringToLog("Ошибка создания файла."));
                    }
                } catch (IOException e) {
                    System.out.println(stringToLog("Ошибка ввода-вывода при создании файла."));
                } finally {
                    OutSwitch.switchToConsole();
                }
            } else if (option.equals(WRITE_OPTION)) {
                System.out.println("Введите название файла для записи: ");
                String fileName = sc.nextLine();
                System.out.println("Введите текст для записи и нажмите Ввод: ");
                try {
                    OutSwitch.switchToFile();
                    writeToFile(fileName, sc.nextLine());
                    System.out.println(stringToLog("Запись в файл выполнена."));
                } catch (IOException e) {
                    System.out.println(stringToLog("Ошибка ввода-вывода при записи в файл."));
                } finally {
                    OutSwitch.switchToConsole();
                }
            } else if (option.equals(READ_OPTION)) {
                System.out.println("Введите название файла для чтения: ");
                try {
                    OutSwitch.switchToFile();
                    String text = readFromFile(sc.nextLine());
                    System.out.println(stringToLog("Чтение из файла выполнено: " + text));
                } catch (IOException e) {
                    System.out.println(stringToLog("Ошибка ввода-вывода при записи в файл."));
                } finally {
                    OutSwitch.switchToConsole();
                }
            } else if (option.equals(EXIT_OPTION)) {
                break;
            }
        } while (true);
    }

    public static Boolean createFile (String fileName) throws IOException {
        if(!prepareFilesDirectory()) {
            return false;
        }
        File file = new File(FILES_DIRECTORY, fileName);
        return !file.exists() && file.createNewFile();
    }

    public static void writeToFile (String fileName, String text) throws IOException {
        /* FileWriter writer = new FileWriter(FILES_DIRECTORY + File.separator + fileName, true);
        writer.append(text);
        writer.close(); */
        try (FileWriter writer = new FileWriter(FILES_DIRECTORY + File.separator + fileName, true)) {
            writer.append(text);
        }
    }

    public static String readFromFile (String fileName) throws IOException {
        FileReader reader = new FileReader(FILES_DIRECTORY + File.separator + fileName);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();
        while (bufferedReader.ready()) {
            stringBuilder.append(bufferedReader.readLine());
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

    private static boolean prepareFilesDirectory () {
        if (!FILES_DIRECTORY.exists()) {
            return FILES_DIRECTORY.mkdirs();
        }
        return true;
    }

    private static String stringToLog (String text) {
        return String.format(
            "%s - %s",
            text,
            new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date())
        );
    }
}

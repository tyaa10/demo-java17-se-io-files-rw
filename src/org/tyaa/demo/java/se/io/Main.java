package org.tyaa.demo.java.se.io;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final Integer CREATE_FILE_OPTION = 1;
    private static final Integer EXIT_OPTION = 2;

    public static void main(String[] args) {
        Integer option;
        do {
            System.out.println("Выберите действие и нажмите Ввод: ");
            System.out.printf("%s. Создать файл\n", CREATE_FILE_OPTION);
            System.out.printf("%s. Завершить работу\n", EXIT_OPTION);
            Scanner sc = new Scanner(System.in);
            String inputString;
            do {
                System.out.print("> ");
                inputString = sc.nextLine();
            } while (!inputString.matches(String.format("^[%s%s]$", CREATE_FILE_OPTION, EXIT_OPTION)));
            option = Integer.parseInt(inputString);
            if (option.equals(CREATE_FILE_OPTION)) {
                System.out.println("Введите имя для файла и нажмите Ввод: ");
                try {
                    if (createFile(sc.nextLine())) {
                        System.out.println("Файл создан.");
                    } else {
                        System.out.println("Ошибка создания файла.");
                    }
                } catch (IOException e) {
                    System.out.println("Ошибка ввода-вывода при создании файла.");
                }
            } else if (option.equals(EXIT_OPTION)) {
                break;
            }
        } while (true);
    }

    public static Boolean createFile (String fileName) throws IOException {
        File directory = new File("out/files");
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                return false;
            }
        }
        File file = new File(directory, fileName);
        return !file.exists() && file.createNewFile();
    }
}

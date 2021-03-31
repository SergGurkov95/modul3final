package util;

import model.PostStatus;

import java.math.BigDecimal;
import java.util.Scanner;

import static util.InputLiterals.*;

public class Ui implements CorrectInputChecking {
    private static Scanner scanner = new Scanner(System.in);

    private String command;
    private String table;


    public String choiceTable() {
        System.out.println("\nВзаимодействовать с \"" +
                USERS + "\" или \"" +
                POSTS + "\" или \"" +
                ANOTHER_COMMANDS + "\":");
        table = Ui.readInput();

        if (tableInputNotCorrect(table)) {
            System.out.println("Неверный ввод!");
            return choiceTable();
        }
        return table;
    }

    public String choiceCRUD() {
        System.out.println("\nДля создания записи введите \"" + CREATE + "\";" +
                "\nДля изменения записи введите \"" + UPDATE + "\";" +
                "\nДля удаления записи введите \"" + DELETE + "\";" +
                "\nДля отображения информации по записям введите \"" + READ + "\";" +
                "\nДля отображения информации по записям введите \"" + READ_ALL + "\":");
        command = Ui.readInput();

        if (crudInputNotCorrect(command)) {
            System.out.println("Неверный ввод!");
            return choiceCRUD();
        }
        return command;
    }

    public String choiceAnother() {
        System.out.println("\nДля отображения всех подписчиков пользователя введите \"" + USER_FOLLOWERS + "\";" +
                "\nДля отображения всех подписок пользователя введите \"" + USER_SUBSCRIPTIONS + "\";" +
                "\nДля отображения всех авторов введите \"" + AUTHORS + "\";" +
                "\nДля отображения лучших авторов введите \"" + TOP_AUTHORS + "\";" +
                "\nДля отображения всех публикаций автора введите \"" + FIND_ALL_POST_BY_AUTHOR + "\";" +
                "\nДля отображения лучших публикаций введите \"" + FIND_TOP_POSTS + "\";" +
                "\nДля отображения информации по записям введите \"" + FIND_TOP_AUTHORS_POSTS + "\":");
        command = Ui.readInput();

        if (anotherInputNotCorrect(command)) {
            System.out.println("Неверный ввод!");
            return choiceAnother();
        }
        return command;
    }

    public String setStringField(String fieldName) {
        System.out.println("\nВведите " + fieldName + ":");
        String str = readInput();
        if (stringInputNotCorrect(str)) {
            System.out.println("Неверный ввод!");
            return setStringField(fieldName);
        }
        return str;
    }

    public BigDecimal setBigDecimalField(String fieldName) {
        System.out.println("\nВведите " + fieldName + ":");
        String str = readInput();
        if (bigDecimalInputNotCorrect(str)) {
            System.out.println("Неверный ввод!");
            return setBigDecimalField(fieldName);
        }
        return new BigDecimal(str);
    }

    public int setIntegerField(String fieldName) {
        System.out.println("\nВведите " + fieldName + ":");
        String str = readInput();
        if (integerInputNotCorrect(str)) {
            System.out.println("Неверный ввод!");
            return setIntegerField(fieldName);
        }
        return Integer.parseInt(str);
    }


    public boolean changeField(String fieldName) {
        System.out.println("\nИзменить " + fieldName + "(yes/no)?:");
        String str = readInput();
        if (CorrectInputChecking.yesNoAnswerNotCorrect(str)) {
            System.out.println("Неверный ввод!");
            return changeField(fieldName);
        }
        if (str.equals("yes")) {
            return true;
        } else return false;
    }

    public static String readInput() {
        return scanner.nextLine();
    }

    public PostStatus setStatusField() {
        System.out.println("\nВведите " + STATUS + " (PUBLISHED/DRAFT/WORKSHEET/IN_PROGRESS):");
        String str = readInput();
        if (statusInputNotCorrect(str)) {
            System.out.println("Неверный ввод!");
            return setStatusField();
        }
        return PostStatus.valueOf(str);
    }

    public boolean setBooleanField(String fieldName) {
        System.out.println("\n" + fieldName + "(yes/no)?:");
        String str = readInput();
        if(CorrectInputChecking.yesNoAnswerNotCorrect(str)){
            System.out.println("Неверный ввод!");
            return setBooleanField(fieldName);
        }if (str.equals("yes")) {
            return true;
        } else return false;
    }

    public boolean yesNoQuestion(String message){
        System.out.println(message + "(yes/no):");
        String str = readInput();
        if(CorrectInputChecking.yesNoAnswerNotCorrect(str)){
            System.out.println("Неверный ввод!");
            return yesNoQuestion(message);
        }if (str.equals("yes")) {
            return true;
        } else return false;
    }

//    public boolean addFollSub(String subscription) {
//    }
}



package util;

import model.PostStatus;

import java.math.BigDecimal;

import static util.InputLiterals.*;

public interface CorrectInputChecking {

    default boolean tableInputNotCorrect(String table) {
        if (table.equals(USERS) || table.equals(POSTS) || table.equals(ANOTHER_COMMANDS)) {
            return false;
        } else return true;
    }

    default boolean crudInputNotCorrect(String command) {
        if (command.equals(CREATE) || command.equals(UPDATE) || command.equals(DELETE) ||
                command.equals(READ) || command.equals(READ_ALL)) {
            return false;
        } else return true;
    }

    default boolean anotherInputNotCorrect(String command) {
        if (command.equals(USER_FOLLOWERS) || command.equals(USER_SUBSCRIPTIONS) || command.equals(AUTHORS) ||
                command.equals(TOP_AUTHORS) || command.equals(FIND_ALL_POST_BY_AUTHOR) ||
                command.equals(FIND_TOP_POSTS)|| command.equals(FIND_TOP_AUTHORS_POSTS)) {
            return false;
        } else return true;
    }

    default boolean stringInputNotCorrect(String str) {
        if (!str.equals("")) {
            return false;
        } else return true;
    }

    default boolean bigDecimalInputNotCorrect(String str) {
        try {
            BigDecimal bigDecimal = new BigDecimal(str);
            return false;
        } catch (NumberFormatException exc) {
            return true;
        }
    }

    default boolean integerInputNotCorrect(String str) {
        try {
            Integer integer = Integer.parseInt(str);
            return false;
        } catch (NumberFormatException exc) {
            return true;
        }
    }

    default boolean statusInputNotCorrect(String str) {
        if (str.equals(PostStatus.DRAFT) || str.equals(PostStatus.IN_PROGRESS) || str.equals(PostStatus.PUBLISHED) || str.equals(PostStatus.WORKSHEET)) {
            return false;
        } else return true;
    }

    static boolean yesNoAnswerNotCorrect(String str) {
        if (str.equals("yes") || str.equals("no")) {
            return false;
        } else return true;
    }
}
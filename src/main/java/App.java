import dao.*;
import model.*;
import util.*;

import static util.InputLiterals.*;

public class App {

    private static boolean AppIsRunning = true;

    public static void main(String[] args) {
        UserDaoClass userDaoClass = new UserDaoClass();
        PostDaoClass postDaoClass = new PostDaoClass();
        User user;
        Post post;

        Ui ui = new Ui();
        WorkWithUser workWithUser;
        WorkWithPost workWithPost;
        WorkWithAnotherCommands workWithAnotherCommands;

      //  TablesFiller.tablesFiller(); //заполняет таблицу небольшим колличеством записей


        String crudOrAnotherCommand;
        String table;

        while (AppIsRunning) {
            while (true) {

                user = new User();
                post = new Post();

                table = ui.choiceTable();

                if(!table.equals(ANOTHER_COMMANDS)) {
                    crudOrAnotherCommand = ui.choiceCRUD();
                }else{
                    crudOrAnotherCommand = ui.choiceAnother();
                }

                switch (table) {
                    case (USERS):
                        workWithUser = new WorkWithUser(ui, crudOrAnotherCommand, user, userDaoClass);
                        break;
                    case (POSTS):
                        workWithPost = new WorkWithPost(ui, crudOrAnotherCommand, post, postDaoClass);
                        break;
                    case(ANOTHER_COMMANDS):
                        workWithAnotherCommands = new WorkWithAnotherCommands(ui, crudOrAnotherCommand, user, userDaoClass, postDaoClass);
                        break;
                }
            }
        }
    }
}

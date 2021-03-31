package util;

import dao.UserDaoClass;
import model.FollSub;
import model.User;

import java.util.List;

import static util.InputLiterals.*;

public class WorkWithUser implements WorkWithModel<User, UserDaoClass> {


    public WorkWithUser(Ui ui, String crudCommand, User user, UserDaoClass userDaoClass) {
        if (crudCommand.equals(CREATE)) {
            create(user, userDaoClass, ui);
        }
        if (crudCommand.equals(READ)) {
            readById(user, userDaoClass, ui);
        }
        if (crudCommand.equals(READ_ALL)) {
            reedAll(userDaoClass);
        }
        if (crudCommand.equals(UPDATE)) {
            update(user, userDaoClass, ui);
        }
        if (crudCommand.equals(DELETE)) {
            delete(userDaoClass, ui);
        }
    }


    @Override
    public void create(User model, UserDaoClass dao, Ui ui) {
        model.setFullName(ui.setStringField(FULL_NAME));
        model.setLogin(ui.setStringField(LOGIN));
        while (!model.setEmail(ui.setStringField(EMAIL))) ;
        if (!model.setAge(ui.setIntegerField(AGE))) {
            System.out.println("Пользователь не создан!");
            return;
        }
        model.setModerator(ui.setBooleanField(IS_MODERATOR));

        dao.create(model);
    }

    @Override
    public User readById(User model, UserDaoClass dao, Ui ui) {
        model = dao.findById(ui.setIntegerField(ID));

        if (model == null) {
            System.out.println("Такого id не существует!");
            if (ui.yesNoQuestion("\nПолучить все записи?")) {
                reedAll(dao);
                if (ui.yesNoQuestion("\nПолучить конкретную запись?")) {
                    return readById(model, dao, ui);
                }
            } else {
                return null;
            }
        }
        if (model != null) {
            System.out.println(model);
        }

        return model;
    }

    @Override
    public void reedAll(UserDaoClass dao) {
        List<User> userList = dao.findAll();
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Override
    public void update(User model, UserDaoClass dao, Ui ui) {
        model = readById(model, dao, ui);

        if (!model.getExist() && ui.yesNoQuestion("Восстановить пользователя?")) {
            model.setExist(true);
            dao.update(model);
        } else if (model.getExist()) {
            if (ui.changeField(FULL_NAME)) {
                model.setFullName(ui.setStringField(FULL_NAME));
            }
            if (ui.changeField(EMAIL)) {
                while (!model.setEmail(ui.setStringField(EMAIL))) ;
            }
            if (ui.changeField(AGE)) {
                while (!model.setAge(ui.setIntegerField(AGE))) ;
            }
            if (ui.changeField(IS_MODERATOR)) {
                if (model.getModerator()) {
                    model.setModerator(false);
                } else {
                    model.setModerator(true);
                }
            }

            dao.update(model);

            addFollSub(model, dao, ui);
        }
    }

    @Override
    public void delete(UserDaoClass dao, Ui ui) {
        dao.deleteById(ui.setIntegerField(ID));
    }

    private void addFollSub(User model, UserDaoClass dao, Ui ui) {
        if (ui.yesNoQuestion("Добавить " + SUBSCRIPTION + "?")) {
            FollSub follSub = new FollSub();
            follSub.setSlave(model);
            User master;
            do {
                master = dao.findById(ui.setIntegerField(ID + " автора"));
                if (master != null) {
                    follSub.setMaster(master);
                    if (follSub.getMaster().getId() == follSub.getSlave().getId()) {
                        System.out.println("Нельзя подписаться на самого себя!");
                    } else if (!follSub.getMaster().getExist()) {
                        System.out.println("Нельзя подписаться на несуществующего пользователя!");
                    } else if (follSub.getMaster().getAuthor()) {
                        if (dao.checkFollSubConnection(follSub)) {
                            dao.addFollSub(follSub);
                        } else {
                            System.out.println("\nТакая связь уже существует!");
                        }
                    } else {
                        System.out.println("Этот пользователь не является автором и на него нельзя подиписаться!");
                    }
                }else{
                    System.out.println("Такого пользователя не существует!");
                }
            }
            while (ui.yesNoQuestion("Добавить еще одну " + SUBSCRIPTION + "?")) ;
        }

        if (model.getAuthor() && ui.yesNoQuestion("Добавить " + FOLLOWER + "?")) {
            FollSub follSub = new FollSub();
            follSub.setMaster(model);
            User slave;
            do {
                slave = dao.findById(ui.setIntegerField(ID + " подписчика"));
                if (slave != null) {
                    follSub.setSlave(slave);
                    if (follSub.getMaster().getId() == follSub.getSlave().getId()) {
                        System.out.println("Нельзя подписаться на самого себя!");
                    } else if (!follSub.getSlave().getExist()) {
                        System.out.println("Нельзя подписать несуществующего пользователя!");
                    } else {
                        if (dao.checkFollSubConnection(follSub)) {
                            dao.addFollSub(follSub);
                        } else {
                            System.out.println("\nТакая связь уже существует!");
                        }
                    }
                }else{
                    System.out.println("Такого пользователя не существует!");
                }
            }
            while (ui.yesNoQuestion("Добавить еще одного " + FOLLOWER + "?")) ;
        }
    }
}

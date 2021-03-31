package util;

import dao.PostDaoClass;
import model.Post;
import model.PostStatus;

import java.util.List;

import static util.InputLiterals.*;

public class WorkWithPost implements WorkWithModel<Post, PostDaoClass> {
    public WorkWithPost(Ui ui, String crudCommand, Post post, PostDaoClass postDaoClass) {
        if (crudCommand.equals(CREATE)) {
            create(post, postDaoClass, ui);
        }
        if (crudCommand.equals(READ)) {
            readById(post, postDaoClass, ui);
        }
        if (crudCommand.equals(READ_ALL)) {
            reedAll(postDaoClass);
        }
        if (crudCommand.equals(UPDATE)) {
            update(post, postDaoClass, ui);
        }
        if (crudCommand.equals(DELETE)) {
            delete(postDaoClass, ui);
        }
    }

    @Override
    public void create(Post model, PostDaoClass dao, Ui ui) {
        while (!model.setTitle(ui.setStringField(TITLE))) {
            if (!ui.yesNoQuestion("Задать другой " + TITLE)) {
                break;
            }
        }
        while (!model.setContent(ui.setStringField(CONTENT))) {
            if (!ui.yesNoQuestion("Задать другой " + CONTENT)) {
                break;
            }
        }
        while (true) {
            model.setAuthor(dao.getAuthorOrModerator(ui.setIntegerField(AUTHOR)));
            if ((model.getAuthor() == null) || !model.getAuthor().getExist()) {
                System.out.println("Пользователя с таким id нет в базе");
            } else {
                break;
            }
        }
        while (true) {
            model.setModerator(dao.getAuthorOrModerator(ui.setIntegerField(MODERATOR)));
            if ((model.getModerator() == null) || !model.getModerator().getExist()) {
                System.out.println("Пользователя с таким id нет в базе");
            } else if (!model.getModerator().getModerator()) {
                System.out.println("Этот пользователь не является модератором!");
            } else if (model.getAuthor().getId() == model.getModerator().getId()) {
                System.out.println("Пользователь не может модерировать свою статью!");
            } else {
                break;
            }
        }
        model.setRating(ui.setIntegerField(RATING));

        dao.create(model);
    }

    @Override
    public Post readById(Post model, PostDaoClass dao, Ui ui) {
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
        System.out.println(model);
        return model;
    }

    @Override
    public void reedAll(PostDaoClass dao) {
        List<Post> postList = dao.findAll();
        for (Post post : postList) {
            System.out.println(post);
        }
    }


    @Override
    public void update(Post model, PostDaoClass dao, Ui ui) {
        model = readById(model, dao, ui);

        if (!model.getModerator().getExist() && !model.getStatus().equals(PostStatus.PUBLISHED)) {
            System.out.println("\nУ публикации нет модератора!");
            while (true) {
                model.setModerator(dao.getAuthorOrModerator(ui.setIntegerField(MODERATOR)));
                if ((model.getModerator() == null) || !model.getModerator().getExist()) {
                    System.out.println("Пользователя с таким id нет в базе");
                } else if (!model.getModerator().getModerator()) {
                    System.out.println("Этот пользователь не является модератором!");
                } else if (model.getAuthor().getId() == model.getModerator().getId()) {
                    System.out.println("Пользователь не может модерировать свою статью!");
                } else {
                    break;
                }
            }
            dao.update(model);
        } else if (ui.changeField(MODERATOR)) {
            while (true) {
                model.setModerator(dao.getAuthorOrModerator(ui.setIntegerField(MODERATOR)));
                if ((model.getModerator() == null) || !model.getModerator().getExist()) {
                    System.out.println("Пользователя с таким id нет в базе");
                } else if (!model.getModerator().getModerator()) {
                    System.out.println("Этот пользователь не является модератором!");
                } else if (model.getAuthor().getId() == model.getModerator().getId()) {
                    System.out.println("Пользователь не может модерировать свою статью!");
                } else {
                    break;
                }
            }
        }

        if (ui.changeField(TITLE)) {
            while (!model.setTitle(ui.setStringField(TITLE))) {
                if (!ui.yesNoQuestion("Задать другой " + TITLE)) {
                    break;
                }
            }
        }
        if (ui.changeField(CONTENT)) {
            while (!model.setContent(ui.setStringField(CONTENT))) {
                if (!ui.yesNoQuestion("Задать другой " + CONTENT)) {
                    break;
                }
            }
        }
        if (ui.changeField(RATING)) {
            model.setRating(ui.setIntegerField(RATING));
        }
        if (ui.changeField(STATUS)) {
            model.setStatus(ui.setStatusField());
        }

        dao.update(model);
    }

    @Override
    public void delete(PostDaoClass dao, Ui ui) {
        dao.deleteById(ui.setIntegerField(ID));
    }
}

package util;

import dao.PostDaoClass;
import dao.UserDaoClass;
import model.Post;
import model.User;

import java.util.List;

import static util.InputLiterals.*;

public class WorkWithAnotherCommands {
    public WorkWithAnotherCommands(Ui ui, String crudOrAnotherCommand, User user, UserDaoClass userDao, PostDaoClass postDao) {
        if (crudOrAnotherCommand.equals(USER_FOLLOWERS)) {
            getUserFollowers(ui, user, userDao);
        }
        if (crudOrAnotherCommand.equals(USER_SUBSCRIPTIONS)) {
            getUserSubscriptions(ui, user, userDao);
        }
        if (crudOrAnotherCommand.equals(AUTHORS)) {
            getAllAuthors(userDao);
        }
        if (crudOrAnotherCommand.equals(TOP_AUTHORS)) {
            getTopAuthors(ui, userDao);
        }
        if (crudOrAnotherCommand.equals(FIND_ALL_POST_BY_AUTHOR)) {
            findAllAuthorPosts(ui, user, userDao, postDao);
        }
        if (crudOrAnotherCommand.equals(FIND_TOP_POSTS)) {
            findTopPosts(ui, postDao);
        }
        if (crudOrAnotherCommand.equals(FIND_TOP_AUTHORS_POSTS)) {
            findTopAuthorsPosts(ui, user, userDao, postDao);
        }
    }

    private void findTopAuthorsPosts(Ui ui, User user, UserDaoClass userDao, PostDaoClass postDao) {
        user = userDao.findById(ui.setIntegerField(ID));
        if (!user.getAuthor()) {
            System.out.println("Этот пользователь не является автором!");
        } else {
            List<Post> topPostsList = postDao.findTopAuthorsPosts(user, ui.setIntegerField(LIMIT));
            for (Post post : topPostsList) {
                System.out.println(post);
            }
        }
    }

    private void findTopPosts(Ui ui, PostDaoClass dao) {
        List<Post> postList = dao.findTopPosts(ui.setIntegerField(LIMIT));
        for (Post post : postList) {
            System.out.println(post);
        }
    }

    private void findAllAuthorPosts(Ui ui, User user, UserDaoClass userDao, PostDaoClass postDao) {
        user = userDao.findById(ui.setIntegerField(ID));
        if (!user.getAuthor()) {
            System.out.println("Пользователь не является автором!");
        } else {
            List<Post> postList = postDao.findAllPostsByAuthor(user);
            for (Post post : postList) {
                System.out.println(post);
            }
        }
    }

    private void getTopAuthors(Ui ui, UserDaoClass dao) {
        List<User> authorsList = dao.findTopAuthors(ui.setIntegerField(LIMIT));
        for (User author : authorsList) {
            System.out.println(author);
        }
    }


    private void getAllAuthors(UserDaoClass dao) {
        List<User> authorsList = dao.findAllAuthors();
        if (authorsList.size() == 0) {
            System.out.println("В базе нет ни одного автора!");
        } else {
            for (User author : authorsList) {
                System.out.println(author);
            }
        }
    }

    private void getUserSubscriptions(Ui ui, User user, UserDaoClass dao) {
        user = dao.findById(ui.setIntegerField(ID));
        List<User> subscriptionsList = dao.findAllSubscriptions(user);
        if (subscriptionsList.size() == 0) {
            System.out.println("\nУ пользователя нет подписок!");
        } else {
            System.out.println("\nАвтор " + user + "\nОн подписан на:\n");
            for (User subscription : subscriptionsList) {
                System.out.println(subscription);
            }
        }
    }

    private void getUserFollowers(Ui ui, User user, UserDaoClass dao) {
        user = dao.findById(ui.setIntegerField(ID));
        if (!user.getAuthor()) {
            System.out.println("Пользователь не является автором, и у него не может быть подписчиков!");
        } else {
            List<User> followersList = dao.findAllFollowers(user);
            if (followersList.size() == 0) {
                System.out.println("\nУ автора нет подписчиков!");
            } else {
                System.out.println("\nАвтор " + user + "\nСписок его подписчиков:\n");
                for (User follower : followersList) {
                    System.out.println(follower);
                }
            }
        }
    }
}

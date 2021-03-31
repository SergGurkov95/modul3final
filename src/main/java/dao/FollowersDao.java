package dao;

import model.User;

import java.util.List;

public interface FollowersDao {
    List<User> findAllFollowers(User author);

}

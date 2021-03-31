package dao;

import model.User;

import java.util.List;

public interface SubscriptionsDao {
    List<User> findAllSubscriptions(User subscriber);
}

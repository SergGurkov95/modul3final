package dao;

import model.User;

import java.util.List;

public interface AuthorDao {
    List<User> findAllAuthors();

    List<User> findTopAuthors(int limit);
}

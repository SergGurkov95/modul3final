package dao;

import java.util.List;

public interface Dao<T> {
    T create(T model);

    T update(T model);

    T findById(int id);

    List<T> findAll();

    boolean deleteById(int id);
}

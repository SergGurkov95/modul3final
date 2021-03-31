package util;


public interface WorkWithModel<T, E> {

    void create(T model, E dao, Ui ui);

    T readById(T model, E dao, Ui ui);

    void reedAll(E dao);

    void update(T model, E dao, Ui ui);

    void delete(E dao, Ui ui);
}

package dao;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateSessionFactory;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.InputLiterals.*;

public class UserDaoClass extends FollSubDaoClass implements Dao<User>, FollowersDao, SubscriptionsDao, AuthorDao {
    private SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();


    @Override
    public User create(User model) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if (model.getAuthor()) {
                model.setAuthor(false);
            }
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(builder.or(builder.equal(root.get("login"), model.getLogin()), builder.equal(root.get("email"), model.getEmail())));
            if (session.createQuery(query).getResultList().size() != 0) {
                System.out.println("Логин и/или email уже заняты!");
                session.getTransaction().commit();
                return model;
            }

            model = session.get(User.class, session.save(model));
            session.getTransaction().commit();
            return model;
        }

    }

    @Override
    public User update(User model) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if (model.getAuthor()) {                                                  //Еди пользователь помечен как
                CriteriaBuilder builder = session.getCriteriaBuilder();               //автор, но у него нет публикаций,
                CriteriaQuery<Post> query = builder.createQuery(Post.class);          //метка "автор" снимается
                Root<Post> root = query.from(Post.class);
                query.select(root).where(builder.equal(root.<User>get("author"), model));
                List<Post> postList = session.createQuery(query).getResultList();
                if (postList.size() == 0) {
                    model.setAuthor(false);
                    System.out.println("У пользователя нет ни одной статьи!");
                }
                session.clear();
            }
            session.update(model);
            model = session.get(User.class, model.getId());
            session.getTransaction().commit();
            return model;
        }
    }

    @Override
    public User findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public List findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            List<User> userList = session.createQuery(query).getResultList();
            session.getTransaction().commit();
            return userList;
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();                         //Пользователи не удаляются, на них ставится метка
            User user = session.get(User.class, id);
            user.setExist(false);
            session.update(user);
            session.getTransaction().commit();
            return false;
        }
    }

    @Override
    public List<User> findAllFollowers(User author) {
        List<User> followersList = new ArrayList<>();
        List<FollSub> follList = getFoll(author);
        for (FollSub foll : follList) {
            followersList.add(foll.getSlave());
        }
        return followersList;
    }


    @Override
    public List<User> findAllSubscriptions(User subscriber) {
        List<User> subscriptionsList = new ArrayList<>();
        List<FollSub> subList = getSub(subscriber);
        for (FollSub sub : subList) {
            subscriptionsList.add(sub.getMaster());
        }
        return subscriptionsList;
    }

    @Override
    public List<User> findAllAuthors() {
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(builder.equal(root.<Boolean>get("isAuthor"), true));
            try {
                List <User> authorList = session.createQuery(query).getResultList();
                return authorList;
            }catch (NoResultException exc){
                return new ArrayList<>();
            }
        }
    }

    @Override
    public List<User> findTopAuthors(int limit) {           // для запроса содержащего вложенные селекты использовал
        List<User> topAuthorList = new ArrayList<>();       // jdbc, в классе InputLiterals нужно ввести URL, USER, PASS
        try {
            String sql = "select best from (select max(master_id)as best, count(master_id) as top " +
                    "from module3_db.following_connection group by (master_id) order by top desc) as t limit ";
            Connection connection = DriverManager.getConnection(URL, USER, PASS);
            ResultSet resultSet = connection.createStatement().executeQuery(sql + limit);
            while (resultSet.next()){
                topAuthorList.add(findById(resultSet.getInt("best")));
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
        return topAuthorList;
    }
}

package dao;

import model.FollSub;
import model.Post;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateSessionFactory;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class FollSubDaoClass {
    private SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();


    public void addFollSub(FollSub follSub) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(follSub);
            session.getTransaction().commit();
        }
    }


    public boolean checkFollSubConnection(FollSub follSub) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<FollSub> query = builder.createQuery(FollSub.class);
            Root<FollSub> root = query.from(FollSub.class);
            query.select(root).where(builder.and(builder.equal(root.<User>get("master"), follSub.getMaster()), builder.equal(root.<User>get("slave"), follSub.getSlave())));
            try {
                session.createQuery(query).getSingleResult();
                return false;
            } catch (NoResultException exc) {
                return true;
            }
        }
    }

    public List<FollSub> getFoll(User user) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<FollSub> query = builder.createQuery(FollSub.class);
            Root<FollSub> root = query.from(FollSub.class);
            query.select(root).where(builder.or(builder.equal(root.<User>get("master"), user.getId())));
            return session.createQuery(query).getResultList();
        }
    }

    public List<FollSub> getSub(User user) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<FollSub> query = builder.createQuery(FollSub.class);
            Root<FollSub> root = query.from(FollSub.class);
            query.select(root).where(builder.or(builder.equal(root.<User>get("slave"), user.getId())));
            return session.createQuery(query).getResultList();
        }
    }
}

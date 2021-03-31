package dao;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateSessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class PostDaoClass implements Dao<Post>, PostDao{
    private SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();

    @Override
    public Post create(Post model) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if(!model.getAuthor().getAuthor()){ //Если это первая статья пользователя, то он становится автором
                model.getAuthor().setAuthor(true);
                model.setAuthor(model.getAuthor());
            }
            model = session.get(Post.class, session.save(model));
            session.update(model.getAuthor());
            session.getTransaction().commit();
            return model;
        }
    }

    @Override
    public Post update(Post model) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(model);
            model = session.get(Post.class, model.getId());
            session.getTransaction().commit();
            return model;
        }
    }

    @Override
    public Post findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Post.class, id);
        }
    }

    @Override
    public List findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Post> query = builder.createQuery(Post.class);
            Root<Post> root = query.from(Post.class);
            List<Post> postsList = session.createQuery(query).getResultList();
            session.getTransaction().commit();
            return postsList;
        }
    }

    @Override
    public boolean deleteById(int id) {
        Post post;
        boolean lastPost = false;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            post = session.get(Post.class, id);
            session.remove(post);

            CriteriaBuilder builder = session.getCriteriaBuilder();      //Если это была последняя статья пользователя,
            CriteriaQuery<Post> query = builder.createQuery(Post.class); //то он перестает быть автором
            Root<Post> root = query.from(Post.class);
            query.select(root).where(builder.equal(root.<User>get("author"), post.getAuthor()));
            List<Post> postList = session.createQuery(query).getResultList();
            if (postList.size() == 0) {
                post.getAuthor().setAuthor(false);
                lastPost = true;
                System.out.println("У пользователя " + post.getAuthor().getLogin() +
                        " больше нет ни одной статьи! Он теперь не автор!");
                session.update(post.getAuthor());
            }
        }
        return false;
    }

    public User getAuthorOrModerator(int id){
        try(Session session = sessionFactory.openSession()){
            return session.get(User.class, id);
        }
    }

    @Override
    public List<Post> findAllPostsByAuthor(User author) {
        try (Session session = sessionFactory.openSession()) {
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery<Post> query = builder.createQuery(Post.class);
                Root<Post> root = query.from(Post.class);
                query.select(root).where(builder.equal(root.<User>get("author"), author));
            return session.createQuery(query).getResultList();
        }
    }

    @Override
    public List<Post> findTopPosts(int limit) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Post> query = builder.createQuery(Post.class);
            Root<Post> root = query.from(Post.class);
            query.select(root).orderBy(builder.desc(root.get("rating")));
            return session.createQuery(query).setMaxResults(limit).getResultList();
        }
    }

    @Override
    public List<Post> findTopAuthorsPosts(User author, int limit) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Post> query = builder.createQuery(Post.class);
            Root<Post> root = query.from(Post.class);
            query.select(root).where(builder.equal(root.<User>get("author"), author)).orderBy(builder.desc(root.get("rating")));
            return session.createQuery(query).setMaxResults(limit).getResultList();
        }
    }
}

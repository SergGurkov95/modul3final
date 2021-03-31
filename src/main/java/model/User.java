package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "full_name")
    private String fullName;
    private String login;
    private String email;
    private int age;

    @Transient
    private List<User> subscriptions = new ArrayList<>();
    @Column(name = "is_author")
    private Boolean isAuthor = false;
    @Column(name = "is_moderator")
    private Boolean isModerator = false;

    private Boolean exist = true;

    @Transient
    private List<User> followers = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public boolean setEmail(String email) {
        if (!email.contains("@")) {
            System.out.println("email не содержит \"@\"!");
            return false;
        } else if (email.indexOf("@") != email.lastIndexOf("@")) {
            System.out.println("email сожержит больше одного символа \"@\"!");
            return false;
        } else {
            this.email = email;
            return true;
        }
    }

    public int getAge() {
        return age;
    }

    public boolean setAge(int age) {
        if (age < 18) {
            System.out.println("Пользователи должны быть совершеннолетними!");
            return false;
        } else {
            this.age = age;
            return true;
        }
    }

    public List<User> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(User subscription) {
        this.subscriptions.add(subscription);
    }

    public Boolean getAuthor() {
        return isAuthor;
    }

    public void setAuthor(Boolean author) {
        isAuthor = author;
    }

    public Boolean getModerator() {
        return isModerator;
    }

    public void setModerator(Boolean moderator) {
        isModerator = moderator;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(User follower) {
        this.followers.add(follower);
    }

    public Boolean getExist() {
        return exist;
    }

    public void setExist(Boolean exist) {
        this.exist = exist;
    }

    @Override
    public String toString() {
        if (!exist) {
            return  "\nПользователь удален\t" +
                    "id=" + id +
                    ", fullName='" + fullName + '\'' +
                    ", login='" + login + '\'' +
                    ", email='" + email + '\'' +
                    ", age=" + age +
                    ", isAuthor=" + isAuthor +
                    ", isModerator=" + isModerator;
        } else {
            return  "\nid=" + id +
                    ", fullName='" + fullName + '\'' +
                    ", login='" + login + '\'' +
                    ", email='" + email + '\'' +
                    ", age=" + age +
                    ", isAuthor=" + isAuthor +
                    ", isModerator=" + isModerator;
        }
    }
}

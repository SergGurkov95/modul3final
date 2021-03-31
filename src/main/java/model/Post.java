package model;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "moderator_id")
    private User moderator;

    private int rating;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_status")
    private PostStatus status = PostStatus.DRAFT;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public boolean setTitle(String title) {
        if (title.length() > 100) {
            System.out.println("Название не может быть длиннее 100 символов и будет обрезано!");
            this.title = title.substring(0, 100);
            return false;
        } else {
            this.title = title;
            return true;
        }
    }

    public String getContent() {
        return content;
    }

    public boolean setContent(String content) {
        if (content.length() > 1000) {
            System.out.println("публикация не может быть длиннее 1000 символов и будет обрезана!");
            this.content = content.substring(0, 1000);
            return false;
        } else {
            this.content = content;
            return true;
        }
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getModerator() {
        return moderator;
    }

    public void setModerator(User moderator) {
        this.moderator = moderator;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return  "\nid=" + id +
                ", title='" + title + '\'' +
                ",\ncontent='" + content + '\'' +
                ",\nauthor=" + author.getId() +
                ", moderator=" + moderator.getId() +
                ", rating=" + rating +
                ", status=" + status;
    }
}

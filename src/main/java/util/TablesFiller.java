package util;

import dao.FollSubDaoClass;
import dao.PostDaoClass;
import dao.UserDaoClass;
import model.FollSub;
import model.Post;
import model.User;

public class TablesFiller {

    public static void tablesFiller(){
        User user = new User();
        Post post = new Post();
        FollSub follSub = new FollSub();
        UserDaoClass userDaoClass = new UserDaoClass();
        PostDaoClass postDaoClass = new PostDaoClass();
        FollSubDaoClass follSubDaoClass = new FollSubDaoClass();


        user.setFullName("CarolusXII");
        user.setLogin("Carolus");
        user.setEmail("xII@Xii");
        user.setAge(36);
        user.setModerator(true);

        userDaoClass.create(user);


        user.setFullName("PeterTheGreat");
        user.setLogin("Great");
        user.setEmail("Peter@Great");
        user.setAge(53);
        user.setModerator(true);

        userDaoClass.create(user);

        user.setFullName("Elizabeth");
        user.setLogin("eli2");
        user.setEmail("eli@2");
        user.setAge(94);
        user.setModerator(true);

        userDaoClass.create(user);

        user.setFullName("King Arthur");
        user.setLogin("arthur");
        user.setEmail("arthur@fromfairytail");
        user.setAge(51);
        user.setModerator(true);

        userDaoClass.create(user);

        user.setFullName("Cleopatra");
        user.setLogin("cleoVII");
        user.setEmail("the.last@queen");
        user.setAge(39);
        user.setModerator(true);

        userDaoClass.create(user);

        user.setFullName("Gaius Julius Caesar ");
        user.setLogin("greatest!general!");
        user.setEmail("bestof@thebest");
        user.setAge(55);
        user.setModerator(true);

        userDaoClass.create(user);

        post.setTitle("caesar");
        post.setContent("caesar");
        post.setAuthor(userDaoClass.findById(6));
        post.setModerator(userDaoClass.findById(5));
        post.setRating(150);
        postDaoClass.create(post);


        post.setTitle("ImTheBest");
        post.setContent("caesar");
        post.setAuthor(userDaoClass.findById(6));
        post.setModerator(userDaoClass.findById(2));
        post.setRating(180);
        postDaoClass.create(post);


        post.setTitle("<3");
        post.setContent("imSexyAndINowIt");
        post.setAuthor(userDaoClass.findById(5));
        post.setModerator(userDaoClass.findById(6));
        post.setRating(100);
        postDaoClass.create(post);

        post.setTitle("imGREAT");
        post.setContent("lol");
        post.setAuthor(userDaoClass.findById(2));
        post.setModerator(userDaoClass.findById(1));
        post.setRating(100);
        postDaoClass.create(post);


        post.setTitle("my legend");
        post.setContent("about me and excalibur and Lancelot and other and other and other");
        post.setAuthor(userDaoClass.findById(4));
        post.setModerator(userDaoClass.findById(3));
        post.setRating(2000);
        postDaoClass.create(post);

        post.setTitle("Long Life Queen");
        post.setContent("Really Long");
        post.setAuthor(userDaoClass.findById(3));
        post.setModerator(userDaoClass.findById(4));
        post.setRating(2000);
        postDaoClass.create(post);

        follSub.setMaster(userDaoClass.findById(1));
        follSub.setSlave(userDaoClass.findById(2));
        follSubDaoClass.addFollSub(follSub);

        follSub.setMaster(userDaoClass.findById(1));
        follSub.setSlave(userDaoClass.findById(3));
        follSubDaoClass.addFollSub(follSub);

        follSub.setMaster(userDaoClass.findById(4));
        follSub.setSlave(userDaoClass.findById(3));
        follSubDaoClass.addFollSub(follSub);

        follSub.setMaster(userDaoClass.findById(3));
        follSub.setSlave(userDaoClass.findById(4));
        follSubDaoClass.addFollSub(follSub);

        follSub.setMaster(userDaoClass.findById(6));
        follSub.setSlave(userDaoClass.findById(5));
        follSubDaoClass.addFollSub(follSub);

        follSub.setMaster(userDaoClass.findById(5));
        follSub.setSlave(userDaoClass.findById(6));
        follSubDaoClass.addFollSub(follSub);

        follSub.setMaster(userDaoClass.findById(6));
        follSub.setSlave(userDaoClass.findById(1));
        follSubDaoClass.addFollSub(follSub);

        follSub.setMaster(userDaoClass.findById(6));
        follSub.setSlave(userDaoClass.findById(2));
        follSubDaoClass.addFollSub(follSub);

        follSub.setMaster(userDaoClass.findById(6));
        follSub.setSlave(userDaoClass.findById(3));
        follSubDaoClass.addFollSub(follSub);
    }
}

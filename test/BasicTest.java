import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {
	
	@SuppressWarnings("deprecation")
	@Before
    public void setup() {
        Fixtures.deleteAll();
    }

    @Test
    public void createAndretrieveUser(){
    	// Create a new user and save it	
       new User ("bob@gmail.com","secret","bob").save();
       
       /*
       User bob = User.find("byEmail", "bob@gmail.com").first();       
       assertNotNull(bob);
       assertEquals("bob",bob.fullName);
       */
       
       //Test
       assertNotNull(User.connect("bob@gmail.com", "secret"));
       assertNull(User.connect("bob@gmail.com", "badpassword"));
       assertNull(User.connect("tom@gmail.com", "secret"));
    }
    
    @Test
    public void testPosts(){
    	//create new user and save it
    	User bob = new User("bob@gmail.com","secret","Bob").save();
    	
    	new Posts(bob, "My first post", "Hello Dude you made it !!! :)").save();
    	
    	assertEquals(1,Posts.count());
    	
    	List<Posts> bobPosts = Posts.find("byAuthor", bob).fetch();
    	
    	assertEquals(1,bobPosts.size());
    	Posts firstPost = bobPosts.get(0);
    	assertNotNull(firstPost);
        assertEquals(bob, firstPost.author);
        assertEquals("My first post", firstPost.title);
        assertEquals("Hello Dude you made it !!! :)", firstPost.content);
        assertNotNull(firstPost.postedAt);
    }

    @Test
    public void postComments() {
    	//create new user and save it
    	User bob = new User("bob@gmail.com","secret","Bob").save();
    	
        // Create a new post
        Posts bobPost = new Posts(bob, "My first post", "Hello world").save();
        
        new Comment(bobPost, "Jeff", "Nice post").save();
        new Comment(bobPost, "Tom", "I knew that !").save();
        
        List<Comment> bobPostComments  = Comment.find("byPost", bobPost).fetch();
        
        assertEquals(2, bobPostComments.size());
        
        Comment firstComment = bobPostComments.get(0);
        assertNotNull(firstComment);
        assertEquals("Jeff", firstComment.author);
        assertEquals("Nice post", firstComment.content);
        assertNotNull(firstComment.postedAt);
        
        Comment secondComment = bobPostComments.get(1);
        assertNotNull(secondComment);
        assertEquals("Tom", secondComment.author);
        assertEquals("I knew that !", secondComment.content);
        assertNotNull(secondComment.postedAt);
    }

    @Test
    public void useTheCommentsRelation() {
        // Create a new user and save it
        User bob = new User("bob@gmail.com", "secret", "Bob").save();
     
        // Create a new post
        Posts bobPost = new Posts(bob, "My first post", "Hello world").save();
     
        // Post a first comment
        bobPost.addComment("Jeff", "Nice post");
        bobPost.addComment("Tom", "I knew that !");
     
        // Count things
        assertEquals(1, User.count());
        assertEquals(1, Posts.count());
        assertEquals(2, Comment.count());
     
        // Retrieve Bob's post
        bobPost = Posts.find("byAuthor", bob).first();
        assertNotNull(bobPost);
     
        // Navigate to comments
        assertEquals(2, bobPost.comments.size());
        assertEquals("Jeff", bobPost.comments.get(0).author);
        
        // Delete the post
        bobPost.delete();
        
        // Check that all comments have been deleted
        assertEquals(1, User.count());
        assertEquals(0, Posts.count());
        assertEquals(0, Comment.count());
    }
}

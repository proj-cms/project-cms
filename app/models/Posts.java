package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Posts extends Model{
   public String title;
   public Date postedAt;
   
   /**
    * Here we use the @Lob annotation to tell JPA 
    * to use a large text database type to store the post content. 
    */
   @Lob  //data type
   public String content;
   
   
   @ManyToOne  //mapping
   public User author;

   @OneToMany(mappedBy="post", cascade=CascadeType.ALL)
   public List<Comment> comments;
   // post in the mappedBy clause is a variable in the Comment class
   // of type Posts
   
   public Posts(User author, String title, String content){
	   this.title = title;
	   this.content=content;
	   this.author=author;
	   this.postedAt=new Date();
	   this.comments = new ArrayList<Comment>();
   }

   public Posts addComment(String author, String content) {
	    Comment newComment = new Comment(this, author, content).save();
	    this.comments.add(newComment);
	    this.save();
	    return this;
	}
   
   
}

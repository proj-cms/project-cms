package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

/**
 * The @Entity annotation marks this class as a managed JPA entity, 
 * and the Model superclass automatically provides a set of useful 
 * JPA helpers that we will discover later. 
 * All fields of this class will be automatically 
 * persisted to the database. 
 */
@Entity
public class User extends Model {
	/*
	 * By deafult table is 'User'
	 * If incase table name is different than the class name 
	 * then @Table(name=“blog_user”) where table name is 'bolg_user'
	 */
   public String email;
   public String password;
   public String fullName;
   public boolean isAdmin;
   
   public User(String email,String password,String fullName){
	   this.email = email;
	   this.password = password;
	   this.fullName = fullName;
   }
   
   public static User connect(String email, String password) {
	    return find("byEmailAndPassword", email, password).first();
	}
}

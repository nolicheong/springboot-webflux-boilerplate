package core.model;


import java.security.NoSuchAlgorithmException;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "Users")
public class User{

    // String id;
    @Id
    String username;
    String name;
    String lastname;
    Integer age;

    public User() {}

    public User(String username, String name, String lastname, Integer age) throws NoSuchAlgorithmException {


        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.age = age;
    }

//    public String getId() {
//        return id;
//    }
//    public void setId(String id) {
//        this.id = id;
//    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
//    @Override
//    public String toString() {
//        return "Users [id=" + id + ", name=" + name + ", age=" + age + "]";
//    }

}

package core.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * An entity to represent a Person.
 *
 * @author Mark Paluch
 */
@Data
@RequiredArgsConstructor
@Document
public class Person {

    private @Id String id;
    private final String firstname;
    private final String lastname;
    private final int age;

    public Person(String id, String firstname, String lastname, int age) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }
}
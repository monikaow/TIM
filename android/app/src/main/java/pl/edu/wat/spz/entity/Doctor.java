package pl.edu.wat.spz.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Doctor implements Serializable {

    private String id;

    private String firstName;

    private String lastName;

    private String title;

    private String price;

    public Doctor(String id, String firstName, String lastName, String title) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
    }

    public String getFullTitle() {
        return String.format("%s %s %s", title, firstName, lastName);
    }
}

package pl.edu.wat.spz.backend.dto;

import lombok.Data;

@Data
public class UserDetails {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public UserDetails(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}

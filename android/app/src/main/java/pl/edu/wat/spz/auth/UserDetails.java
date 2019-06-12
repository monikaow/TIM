package pl.edu.wat.spz.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetails {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;
}

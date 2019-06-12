package pl.edu.wat.spz.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Specialization implements Serializable {

    private String id;

    private String name;

    public Specialization(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

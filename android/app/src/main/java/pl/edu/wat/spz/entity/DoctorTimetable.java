package pl.edu.wat.spz.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorTimetable implements Serializable {

    private String id;

    private String date;

    private boolean enable;

    public DoctorTimetable(String id, String date) {
        this.id = id;
        this.date = date;
    }
}

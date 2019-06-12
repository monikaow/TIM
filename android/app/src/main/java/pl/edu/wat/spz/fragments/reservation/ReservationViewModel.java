package pl.edu.wat.spz.fragments.reservation;

import android.arch.lifecycle.ViewModel;

import lombok.Getter;
import lombok.Setter;
import pl.edu.wat.spz.entity.Doctor;

@Getter
@Setter
public class ReservationViewModel extends ViewModel {

    private String specializationId;

    private Doctor doctor;

    private String timetableId;
}

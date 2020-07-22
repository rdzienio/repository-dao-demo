package pl.sda.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SdaUser {
    private String pesel;
    private String name;
    private String assignedCourse;
    private double coursePrice;
    private boolean payed;
}

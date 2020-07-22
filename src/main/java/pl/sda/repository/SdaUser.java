package pl.sda.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SdaUser {
    private String pesel;
    private String name;
    private String assignedCourse;
    private double coursePrice;
    private boolean payed;
}

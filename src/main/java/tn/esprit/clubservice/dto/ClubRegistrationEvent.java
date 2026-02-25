package tn.esprit.clubservice.dto;

import java.io.Serializable;

public class ClubRegistrationEvent implements Serializable {
    private String email;
    private String fullName;
    private String clubName;

    public ClubRegistrationEvent() {
    }

    public ClubRegistrationEvent(String email, String fullName, String clubName) {
        this.email = email;
        this.fullName = fullName;
        this.clubName = clubName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    @Override
    public String toString() {
        return "ClubRegistrationEvent{" +
                "email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", clubName='" + clubName + '\'' +
                '}';
    }
}

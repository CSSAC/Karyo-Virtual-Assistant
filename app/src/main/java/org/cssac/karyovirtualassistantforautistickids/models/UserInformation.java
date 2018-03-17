package org.cssac.karyovirtualassistantforautistickids.models;

/**
 * Created by prrateekk on 17/3/18.
 */

public class UserInformation {

    public String firstName;
    public String lastName;
    public int dobDate, dobMonth, dobYear;

    public UserInformation() {

    }

    public UserInformation(String firstName, String lastName, int dobDate, int dobMonth, int dobYear) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dobDate = dobDate;
        this.dobMonth = dobMonth;
        this.dobYear = dobYear;
    }
}

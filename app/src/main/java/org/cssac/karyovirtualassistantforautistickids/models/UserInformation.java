package org.cssac.karyovirtualassistantforautistickids.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by prrateekk on 17/3/18.
 */

public class UserInformation implements Serializable{

    public String firstName;
    public String lastName;
    public int dobDate, dobMonth, dobYear;

    public HashMap<String, Integer> level;
    public HashMap<String, Integer> maxLevel;

    public UserInformation() {

    }

    public UserInformation(String firstName, String lastName, int dobDate, int dobMonth, int dobYear) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dobDate = dobDate;
        this.dobMonth = dobMonth;
        this.dobYear = dobYear;
        level = new HashMap<>();
        level.put("colour", 1);
        level.put("shapes", 1);
        level.put("animals", 1);
        level.put("fruits", 1);
        level.put("vegetables", 1);
        level.put("numbers", 1);
        level.put("emotions", 1);

        maxLevel = new HashMap<>();
        maxLevel.put("colour", 2);
        maxLevel.put("shapes", 2);
        maxLevel.put("animals", 2);
        maxLevel.put("fruits", 2);
        maxLevel.put("vegetables", 2);
        maxLevel.put("numbers", 2);
        maxLevel.put("emotions", 2);
    }
}

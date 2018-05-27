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

    public HashMap<String, Integer> attempts;
    public HashMap<String, Integer> correctAttempts;

    public HashMap<String, List<Integer> > correctList;

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

        attempts = new HashMap<>();
        attempts.put("colour", 0);
        attempts.put("shapes", 0);
        attempts.put("animals", 0);
        attempts.put("fruits", 0);
        attempts.put("vegetables", 0);
        attempts.put("numbers", 0);
        attempts.put("emotions", 0);

        correctAttempts = new HashMap<>();
        correctAttempts.put("colour", 0);
        correctAttempts.put("shapes", 0);
        correctAttempts.put("animals", 0);
        correctAttempts.put("fruits", 0);
        correctAttempts.put("vegetables", 0);
        correctAttempts.put("numbers", 0);
        correctAttempts.put("emotions", 0);

        correctList = new HashMap<>();
        correctList.put("colour", new ArrayList<Integer>());
        correctList.put("shapes", new ArrayList<Integer>());
        correctList.put("animals", new ArrayList<Integer>());
        correctList.put("fruits", new ArrayList<Integer>());
        correctList.put("vegetables", new ArrayList<Integer>());
        correctList.put("numbers", new ArrayList<Integer>());
        correctList.put("emotions", new ArrayList<Integer>());

        correctList.get("colour").add(0);
        correctList.get("shapes").add(0);
        correctList.get("animals").add(0);
        correctList.get("fruits").add(0);
        correctList.get("numbers").add(0);
        correctList.get("vegetables").add(0);
        correctList.get("emotions").add(0);
    }
}

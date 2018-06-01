package org.cssac.karyovirtualassistantforautistickids.models;

import android.nfc.Tag;

import org.cssac.karyovirtualassistantforautistickids.constants.Tags;

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
    public HashMap<String, List<Integer> > accuracy;

    public UserInformation() {

    }

    public UserInformation(String firstName, String lastName, int dobDate, int dobMonth, int dobYear) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dobDate = dobDate;
        this.dobMonth = dobMonth;
        this.dobYear = dobYear;

        level = new HashMap<>();
        maxLevel = new HashMap<>();
        attempts = new HashMap<>();
        correctAttempts = new HashMap<>();
        correctList = new HashMap<>();
        accuracy = new HashMap<>();

        for (int i=0;i< Tags.TAGS.length;i++) {
            level.put(Tags.TAGS[i], 1);
            maxLevel.put(Tags.TAGS[i], 3);
            attempts.put(Tags.TAGS[i], 0);
            correctAttempts.put(Tags.TAGS[i], 0);
            correctList.put(Tags.TAGS[i], new ArrayList<Integer>());
            correctList.get(Tags.TAGS[i]).add(0);;
            accuracy.put(Tags.TAGS[i], new ArrayList<Integer>());
            accuracy.get(Tags.TAGS[i]).add(0);
        }

        maxLevel.put("numbers", 2);
        maxLevel.put("emotions", 2);
    }
}

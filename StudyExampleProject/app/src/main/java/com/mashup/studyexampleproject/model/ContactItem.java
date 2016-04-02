package com.mashup.studyexampleproject.model;

/**
 * Created by Omjoon on 16. 3. 24..
 */
public class ContactItem {
    private String callNumber;
    private String name;
    private int imageId;


    public ContactItem(String callNumber, String name, int imageId) {
        this.callNumber = callNumber;
        this.name = name;
        this.imageId = imageId;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}

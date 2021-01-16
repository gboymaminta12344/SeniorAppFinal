package com.android2.seniorappclone;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class GiveMedicinItem {

    private String FullName;
    private String MemberMedicineDescription;
    private String MemberMedicineNote;
    private @ServerTimestamp
    Date timestamp;
    private String member_id;
    private String given_by;
    private String given_by_position;
    private String image_of_sen;


    //important dont delete
    public GiveMedicinItem() {
        // no args constructor
    }

    public GiveMedicinItem(String FullName, String MemberMedicineDescription,String MemberMedicineNote,Date timestamp,String member_id,String given_by,String image_of_sen,String given_by_position) {
        this.FullName = FullName;
        this.MemberMedicineDescription = MemberMedicineDescription;
        this.MemberMedicineNote = MemberMedicineNote;
        this.timestamp = timestamp;
        this.member_id = member_id;
        this.given_by = given_by;
        this.image_of_sen = image_of_sen;
        this.given_by_position = given_by_position;

    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName)
    {
        FullName = fullName;
    }

    public String getMemberMedicineDescription()
    {
        return MemberMedicineDescription;
    }

    public void setMemberMedicineDescription(String memberMedicineDescription)
    {
        MemberMedicineDescription = memberMedicineDescription;

    }

    public String getMemberMedicineNote() {
        return MemberMedicineNote;
    }

    public void setMemberMedicineNote(String memberMedicineNote) {
        MemberMedicineNote = memberMedicineNote;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getGiven_by() {
        return given_by;
    }

    public void setGiven_by(String given_by) {
        this.given_by = given_by;
    }

    public String getImage_of_sen() {
        return image_of_sen;
    }

    public void setImage_of_sen(String image_of_sen) {
        this.image_of_sen = image_of_sen;
    }

    public String getGiven_by_position() {
        return given_by_position;
    }

    public void setGiven_by_position(String given_by_position) {
        this.given_by_position = given_by_position;
    }
}

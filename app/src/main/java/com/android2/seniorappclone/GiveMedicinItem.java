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


    //important dont delete
    public GiveMedicinItem() {
        // no args constructor
    }

    public GiveMedicinItem(String FullName, String MemberMedicineDescription,String MemberMedicineNote,Date timestamp,String member_id) {
        this.FullName = FullName;
        this.MemberMedicineDescription = MemberMedicineDescription;
        this.MemberMedicineNote = MemberMedicineNote;
        this.timestamp = timestamp;
        this.member_id = member_id;

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
}

package com.android2.seniorappclone;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Members {

    //ito yung custome object na ginawa natin para matawag mo kahi san gami lgn ang Members mb = new Members

    private String Fullname;
    private String Birthday;
    private @ServerTimestamp
    Date timestamp;
    private String Gender;
    private String Municipality;
    private String Barangay;
    private String Zone;
    private String member_id;
    private String member_ImageUri;
    private String AddedBy;
    private String Status;

   //important dont delete
    public Members() {
        // no args constructor
    }

    public Members(String Fullname, String Birthday, Date timestamp, String Gender, String member_id, String Barangay, String Zone, String member_ImageUri, String AddedBy, String Status) {

        this.Birthday = Fullname;
        this.Birthday = Birthday;
        this.timestamp = timestamp;
        this.Gender = Gender;
        this.Municipality = Municipality;
        this.Barangay = Barangay;
        this.Zone = Zone;
        this.member_id = member_id;
        this.member_ImageUri = member_ImageUri;
        this.AddedBy = AddedBy;
        this.Status = Status;


    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAddedBy() {
        return AddedBy;
    }

    public void setAddedBy(String addedBy) {
        AddedBy = addedBy;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getMunicipality() {
        return Municipality;
    }

    public void setMunicipality(String municipality) {
        Municipality = municipality;
    }

    public String getBarangay() {
        return Barangay;
    }

    public void setBarangay(String barangay) {
        Barangay = barangay;
    }

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_ImageUri() {
        return member_ImageUri;
    }

    public void setMember_ImageUri(String member_ImageUri) {
        this.member_ImageUri = member_ImageUri;
    }
}

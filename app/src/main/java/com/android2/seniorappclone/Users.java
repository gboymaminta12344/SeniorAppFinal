package com.android2.seniorappclone;

public class Users {


    private String FullName;
    private String UserPosition;
    private String UserEmail;
    private String UserPhone;
    private String UserId;
    private String User_ImageUri;


    //important dont delete
    public Users() {
        // no args constructor
    }

    public Users(String FullName, String UserEmail, String UserPhone, String UserId,String Position,String User_ImageUri) {
        this.FullName = FullName;
        this.UserEmail = UserEmail;
        this.UserPhone = UserPhone;
        this.UserId = UserId;
        this.UserPosition = UserPosition;
        this.User_ImageUri = User_ImageUri;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getUserPosition() {
        return UserPosition;
    }

    public void setUserPosition(String userPosition) {
        UserPosition = userPosition;
    }

    public String getUser_ImageUri() {
        return User_ImageUri;
    }

    public void setUser_ImageUri(String user_ImageUri) {
        User_ImageUri = user_ImageUri;
    }
}

package com.abedkhan.multimedia.Model;

public class UserModel {
    String userID, profileImgUrl;
    String fullName, userName, email, gender, dateOfBirth, password;
    long idCreationTimeMillis;
    String userBio, profession, livingCountry, livingCity;

    public UserModel(String userID, String profileImgUrl, String fullName, String userName, String email, String gender, String dateOfBirth, String password, long idCreationTimeMillis, String userBio, String profession, String livingCountry, String livingCity) {
        this.userID = userID;
        this.profileImgUrl = profileImgUrl;
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.idCreationTimeMillis = idCreationTimeMillis;
        this.userBio = userBio;
        this.profession = profession;
        this.livingCountry = livingCountry;
        this.livingCity = livingCity;
    }

    public UserModel() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getLivingCountry() {
        return livingCountry;
    }

    public void setLivingCountry(String livingCountry) {
        this.livingCountry = livingCountry;
    }

    public String getLivingCity() {
        return livingCity;
    }

    public void setLivingCity(String livingCity) {
        this.livingCity = livingCity;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public long getIdCreationTimeMillis() {
        return idCreationTimeMillis;
    }

    public void setIdCreationTimeMillis(long idCreationTimeMillis) {
        this.idCreationTimeMillis = idCreationTimeMillis;
    }
}

package com.satria.authenticguards.agv3mvvm.model;

import android.util.Patterns;

public class User {

    private String name;
    private String image;
    private String numberPhone;
    private String totalPoint;
    private String email;
    private String gender;
    private String age;
    private String address;
    private String onVerifiedNumber;
    private String completeProfile;
    private String strPassword;
    private String strConfirmPassword;

    public User(String name, String image, String numberPhone, String totalPoint, String email, String gender, String age, String address, String onVerifiedNumber, String completeProfile, String strPassword, String strConfirmPassword) {
        this.name = name;
        this.image = image;
        this.numberPhone = numberPhone;
        this.totalPoint = totalPoint;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.onVerifiedNumber = onVerifiedNumber;
        this.completeProfile = completeProfile;
        this.strPassword = strPassword;
        this.strConfirmPassword = strConfirmPassword;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getStrPassword() {
        return strPassword;
    }

    public void setStrPassword(String strPassword) {
        this.strPassword = strPassword;
    }

    public String getStrConfirmPassword() {
        return strConfirmPassword;
    }

    public void setStrConfirmPassword(String strConfirmPassword) {
        this.strConfirmPassword = strConfirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(String totalPoint) {
        this.totalPoint = totalPoint;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOnVerifiedNumber() {
        return onVerifiedNumber;
    }

    public void setOnVerifiedNumber(String onVerifiedNumber) {
        this.onVerifiedNumber = onVerifiedNumber;
    }

    public String getCompleteProfile() {
        return completeProfile;
    }

    public void setCompleteProfile(String completeProfile) {
        this.completeProfile = completeProfile;
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
    }


    public boolean isPasswordLengthGreaterThan5() {
        return getStrPassword().length() > 5;
    }

    public boolean isPasswordConfirmation() {
        return getStrPassword().equals(getStrConfirmPassword());
    }
}

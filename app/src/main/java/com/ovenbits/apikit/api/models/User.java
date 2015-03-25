package com.ovenbits.apikit.api.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private int mId;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("full_name")
    private String mFullName;
    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("last_name")
    private String mLastName;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("token")
    private String mToken;
    @SerializedName("birthdate")
    private String mBirthdate;
    @SerializedName("has_history_me")
    private boolean mHasHistoryMe;
    @SerializedName("has_history_sister")
    private boolean mHasHistorySister;
    @SerializedName("has_history_daughter")
    private boolean mHasHistoryDaughter;
    @SerializedName("has_history_mother")
    private boolean mHasHistoryMother;
    @SerializedName("last_mammogram")
    private String mLastMammogram;
    @SerializedName("last_clinical_exam")
    private String mLastClinicalExam;
    @SerializedName("last_self_exam")
    private String mLastSelfExam;
    @SerializedName("first_period_age")
    private int mFirstPeriodAge;
    @SerializedName("regular_birth_control")
    private boolean mRegularBirthControl;
    @SerializedName("breastfed_children")
    private boolean mBreastfedChildren;
    @SerializedName("brca_positive")
    private boolean mBRCAPositive;

    public String getToken() {
        return mToken;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getBirthdate() {
        return mBirthdate;
    }

    public void setmBirthdate(String birthdate) {
        mBirthdate = birthdate;
    }

    public boolean hasHistoryMe() {
        return mHasHistoryMe;
    }

    public boolean hasHistorySister() {
        return mHasHistorySister;
    }

    public boolean hasHistoryDaughter() {
        return mHasHistoryDaughter;
    }

    public boolean hasHistoryMother() {
        return mHasHistoryMother;
    }
}

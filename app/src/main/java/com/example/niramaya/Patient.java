package com.example.niramaya;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Patient {
    private String PFname;
    private String PLname;
    private String PAge;
    private String PGender;
    private String PDisease;
    private String PBloodGroup;
    private String PArea;
    private @ServerTimestamp    Date PDate;
    private String PAddress;
    private String userId;
    private GeoPoint PLocation;
    private @ServerTimestamp Date ReportDate;

    public Patient(){
        //Empty Constructor
    }

    public Patient(String pFname, String pLname, String pAge, String pGender, String pDisease, String pBloodGroup, String pArea, Date pDate, String pAddress, String userId, GeoPoint pLoaction, Date reportDate) {
        this.PFname = pFname;
        this.PLname = pLname;
        this.PAge = pAge;
        this.PGender = pGender;
        this.PDisease = pDisease;
        this.PBloodGroup = pBloodGroup;
        this.PArea = pArea;
        this.PDate = pDate;
        this.PAddress = pAddress;
        this.userId = userId;
        this.PLocation = pLoaction;
        this.ReportDate = reportDate;
    }

    public String getPFname() {
        return PFname;
    }

    public void setPFname(String PFname) {
        this.PFname = PFname;
    }

    public String getPLname() {
        return PLname;
    }

    public void setPLname(String PLname) {
        this.PLname = PLname;
    }

    public String getPAge() {
        return PAge;
    }

    public void setPAge(String PAge) {
        this.PAge = PAge;
    }

    public String getPGender() {
        return PGender;
    }

    public void setPGender(String PGender) {
        this.PGender = PGender;
    }

    public String getPDisease() {
        return PDisease;
    }

    public void setPDisease(String PDisease) {
        this.PDisease = PDisease;
    }

    public String getPBloodGroup() {
        return PBloodGroup;
    }

    public void setPBloodGroup(String PBloodGroup) {
        this.PBloodGroup = PBloodGroup;
    }

    public String getPArea() {
        return PArea;
    }

    public void setPArea(String PArea) {
        this.PArea = PArea;
    }

    public Date getPDate() {
        return PDate;
    }

    public void setPDate(Date PDate) {
        this.PDate = PDate;
    }

    public String getPAddress() {
        return PAddress;
    }

    public void setPAddress(String PAddress) {
        this.PAddress = PAddress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public GeoPoint getPLocation() {
        return PLocation;
    }

    public void setPLocation(GeoPoint PLocation) {
        this.PLocation = PLocation;
    }

    public Date getReportDate() {
        return ReportDate;
    }

    public void setReportDate(Date reportDate) {
        ReportDate = reportDate;
    }
}

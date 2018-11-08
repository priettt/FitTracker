package com.pps.globant.fittracker.model.fitness;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exercise {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("license_author")
    @Expose
    private String licenseAuthor;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("is_main")
    @Expose
    private Boolean isMain;
    @SerializedName("license")
    @Expose
    private Integer license;
    @SerializedName("exercise")
    @Expose
    private Integer exercise;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLicenseAuthor() {
        return licenseAuthor;
    }

    public void setLicenseAuthor(String licenseAuthor) {
        this.licenseAuthor = licenseAuthor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getIsMain() {
        return isMain;
    }

    public void setIsMain(Boolean isMain) {
        this.isMain = isMain;
    }

    public Integer getLicense() {
        return license;
    }

    public void setLicense(Integer license) {
        this.license = license;
    }

    public Integer getExercise() {
        return exercise;
    }

    public void setExercise(Integer exercise) {
        this.exercise = exercise;
    }

}

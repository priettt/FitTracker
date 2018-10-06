
package com.pps.globant.fittracker.model.avatars.jsonAvatarClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeriesItem {

    @SerializedName("resourceURI")
    @Expose
    private String resourceURI;
    @SerializedName("name")
    @Expose
    private String name;

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

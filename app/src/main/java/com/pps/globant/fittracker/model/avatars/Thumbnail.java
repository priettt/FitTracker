
package com.pps.globant.fittracker.model.avatars;

import com.google.gson.annotations.Expose;

public class Thumbnail {

    public static final String STANDARD_XLARGE = "standard_xlarge";
    public static final String STANDARD_LARGE = "standard_large";
    public static final String FULL_DETAIL = "detail";
    @Expose
    private String path;
    @Expose
    private String extension;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String toUrlRequest(String size) {
        return String.format("%1$s/%2$s.%3$s",path,size,extension);
    }
}

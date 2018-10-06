
package com.pps.globant.fittracker.model.avatars.jsonAvatarClasses;

import com.google.gson.annotations.Expose;
import com.pps.globant.fittracker.model.avatars.Data;

public class AvatarsData {

    @Expose(serialize = false, deserialize = false)
    private Integer code;
    @Expose(serialize = false, deserialize = false)
    private String status;
    @Expose(serialize = false, deserialize = false)
    private String copyright;
    @Expose(serialize = false, deserialize = false)
    private String attributionText;
    @Expose(serialize = false, deserialize = false)
    private String attributionHTML;
    @Expose(serialize = false, deserialize = false)
    private String etag;
    @Expose
    private Data data;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public void setAttributionText(String attributionText) {
        this.attributionText = attributionText;
    }

    public String getAttributionHTML() {
        return attributionHTML;
    }

    public void setAttributionHTML(String attributionHTML) {
        this.attributionHTML = attributionHTML;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}

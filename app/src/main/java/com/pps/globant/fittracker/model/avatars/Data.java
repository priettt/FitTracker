
package com.pps.globant.fittracker.model.avatars;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @Expose(serialize = false, deserialize = false)
    private Integer offset;
    @Expose(serialize = false, deserialize = false)
    private Integer limit;
    @Expose(serialize = false, deserialize = false)
    private Integer total;
    @Expose(serialize = false, deserialize = false)
    private Integer count;
    @SerializedName("results")
    @Expose
    private List<Avatar> avatars = null;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Avatar> getResults() {
        return avatars;
    }

    public void setResults(List<Avatar> avatars) {
        this.avatars = avatars;
    }

}

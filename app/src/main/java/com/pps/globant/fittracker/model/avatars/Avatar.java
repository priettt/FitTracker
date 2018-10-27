package com.pps.globant.fittracker.model.avatars;

import com.google.gson.annotations.Expose;
import com.pps.globant.fittracker.model.avatars.jsonAvatarClasses.Comics;
import com.pps.globant.fittracker.model.avatars.jsonAvatarClasses.Events;
import com.pps.globant.fittracker.model.avatars.jsonAvatarClasses.Series;
import com.pps.globant.fittracker.model.avatars.jsonAvatarClasses.Stories;
import com.pps.globant.fittracker.model.avatars.jsonAvatarClasses.Url;

import java.util.List;

public class Avatar {

    @Expose(serialize = false, deserialize = false)
    private Integer id;
    @Expose
    private String name;
    @Expose(serialize = false, deserialize = false)
    private String description;
    @Expose(serialize = false, deserialize = false)
    private String modified;
    @Expose
    private Thumbnail thumbnail;
    @Expose(serialize = false, deserialize = false)
    private String resourceURI;
    @Expose(serialize = false, deserialize = false)
    private Comics comics;
    @Expose(serialize = false, deserialize = false)
    private Series series;
    @Expose(serialize = false, deserialize = false)
    private Stories stories;
    @Expose(serialize = false, deserialize = false)
    private Events events;
    @Expose(serialize = false, deserialize = false)
    private List<Url> urls = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public Comics getComics() {
        return comics;
    }

    public void setComics(Comics comics) {
        this.comics = comics;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Stories getStories() {
        return stories;
    }

    public void setStories(Stories stories) {
        this.stories = stories;
    }

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }

}

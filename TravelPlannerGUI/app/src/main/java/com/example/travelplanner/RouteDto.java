package com.example.travelplanner;

import java.util.Date;

public class RouteDto {

    private Integer id;
    private Date date;
    private Integer travelId;
    private String title;

    public RouteDto() {
    }

    public RouteDto(Integer id, Date date, Integer travelId, String title) {
        this.id = id;
        this.date = date;
        this.travelId = travelId;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Integer getTravelId() {
        return travelId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTravelId(Integer travelId) {
        this.travelId = travelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

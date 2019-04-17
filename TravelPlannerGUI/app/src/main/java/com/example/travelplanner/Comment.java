package com.example.travelplanner;

public class Comment {

    private long id;
    private double grade;
    private String comment;
    private Integer object;
    private String user;

    public Comment() {
    }

    public Comment(long id, double grade, String comment, Integer object, String user) {
        this.id = id;
        this.grade = grade;
        this.comment = comment;
        this.object = object;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getObject() {
        return object;
    }

    public void setObject(Integer object) {
        this.object = object;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {

        if (comment != null) {
            return user + " gave " + grade + "/5 rating\n\n"
                    + comment;
        } else {
            return user + " gave " + grade + "/5 rating";
        }
    }
}

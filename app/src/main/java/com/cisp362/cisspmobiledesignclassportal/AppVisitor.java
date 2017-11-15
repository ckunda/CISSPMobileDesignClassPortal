package com.cisp362.cisspmobiledesignclassportal;

public class AppVisitor {

    private int id;
    private String name;
    private String email;
    private String language;
    private String studentType;
    private String date;
    private String time;
    private String rating;

    public AppVisitor(){}

    public AppVisitor(String name, String email, String language,
                      String studentType, String date, String time, String rating) {
        this.name = name;
        this.email = email;
        this.language = language;
        this.studentType = studentType;
        this.date = date;
        this.time = time;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getStudentType() {
        return studentType;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getRating() {
        return rating;
    }

    public void setId(int id) { this.id = id; }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setStudentType(String studentType) {
        this.studentType = studentType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setRating(String rating) { this.rating = rating; }

    @Override
    public String toString() {
        return "AppVisitor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", language='" + language + '\'' +
                ", studentType='" + studentType + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", rating=" + rating +
                '}';
    }
}
package com.example.filmes;

public class Slider {
    private String image;
    private String title;
    private int id;
    private double vote;

    public Slider(String image, String title, int id, double vote) {
        this.image = image;
        this.title = title;
        this.id = id;
        this.vote = vote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Number getVote() {
        return vote;
    }

    public void setVote(double vote) {
        this.vote = vote;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

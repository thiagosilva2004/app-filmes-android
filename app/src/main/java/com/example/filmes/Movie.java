package com.example.filmes;

public class Movie {
    private String title;
    private String img;
    private int id;
    private Number vote;

    public Number getVote() {
        return vote;
    }

    public void setVote(Number vote) {
        this.vote = vote;
    }

    public Movie(String title, String img, int id, Number vote) {
        this.title = title;
        this.img = img;
        this.id = id;
        this.vote = vote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

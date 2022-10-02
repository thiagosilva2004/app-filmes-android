package com.example.filmes.models;

public class MovieDetail {
    private int id;
    private String img;
    private String cover;
    private String title;
    private double vote;
    private String descricao;

    public MovieDetail(int id, String img, String cover, String title, double vote, String descricao) {
        this.id = id;
        this.img = img;
        this.cover = cover;
        this.title = title;
        this.vote = vote;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVote() {
        return vote;
    }

    public void setVote(double vote) {
        this.vote = vote;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

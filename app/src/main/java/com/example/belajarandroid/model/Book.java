package com.example.belajarandroid.model;

public class Book {
    private int id;
    private String judul;
    private String penerbit;
    private String penulis;
    private int harga;
    private int userid;
    private String thumb;

    public Book() {
    }

    public Book(String judul, String penerbit, String penulis) {
        this.judul = judul;
        this.penerbit = penerbit;
        this.penulis = penulis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Book(int id, String judul, String penerbit, String penulis, int harga, int userid, String thumb) {
        this.id = id;
        this.judul = judul;
        this.penerbit = penerbit;
        this.penulis = penulis;
        this.harga = harga;
        this.userid = userid;
        this.thumb = thumb;
    }
}

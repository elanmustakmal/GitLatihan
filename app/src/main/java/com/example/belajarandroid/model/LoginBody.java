package com.example.belajarandroid.model;

public class LoginBody {

    private String username;
    private String password;

    public LoginBody(String judul, String penerbit, String penulis, String tahun, String harga) {
    }

    public LoginBody(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.example.belajarandroid.utils;

public class AppService {

    private static String token;
    private static int idBuku;

    public static int getIdBuku() {
        return idBuku;
    }

    public static void setIdBuku(int idBuku) {
        AppService.idBuku = idBuku;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        AppService.token = token;
    }
}

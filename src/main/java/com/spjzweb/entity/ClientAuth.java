package com.spjzweb.entity;

public class ClientAuth {
    private int id;
    private String encrypt_code;
    private String encrypt_code2;

    public ClientAuth() {
    }

    public ClientAuth(int id, String encrypt_code, String encrypt_code2) {
        this.id = id;
        this.encrypt_code = encrypt_code;
        this.encrypt_code2 = encrypt_code2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEncrypt_code() {
        return encrypt_code;
    }

    public void setEncrypt_code(String encrypt_code) {
        this.encrypt_code = encrypt_code;
    }

    public String getEncrypt_code2() {
        return encrypt_code2;
    }

    public void setEncrypt_code2(String encrypt_code2) {
        this.encrypt_code2 = encrypt_code2;
    }
}

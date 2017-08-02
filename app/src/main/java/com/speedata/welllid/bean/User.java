package com.speedata.welllid.bean;

/**
 * Created by echo on 2017/7/31.
 */

public class User {
    private String username;
    private String password;
    /**
     * companyId : 9
     * companyName :
     * id : 11
     * loginName : speedata
     * name : speedata
     */

    private int companyId;
    private String companyName;
    private int id;
    private String loginName;
    private String name;

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



    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", id=" + id +
                ", loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

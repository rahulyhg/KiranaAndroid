package com.example.karan_pc.kirana.com.example.karan_pc.kirana;

import java.util.List;

/**
 * Created by Karan-PC on 07-06-2015.
 */
public class UserList {
    private int majorCode;
    private List<OneUser> oneUser;

    public List<OneUser> getOneUser() {
        return oneUser;
    }

    public void setOneUser(List<OneUser> oneUser) {
        this.oneUser = oneUser;
    }

    public int getMajorCode() {

        return majorCode;
    }

    public void setMajorCode(int majorCode) {
        this.majorCode = majorCode;
    }

    public class OneUser {
        private int id;
        private String userName;
        private String userToken;
        private String userRole;
        private String email;
        private String phone;
        private String street;
        private String state;
        private Shop shop;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserToken() {
            return userToken;
        }

        public void setUserToken(String userToken) {
            this.userToken = userToken;
        }

        public String getUserRole() {
            return userRole;
        }

        public void setUserRole(String userRole) {
            this.userRole = userRole;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public Shop getShop() {
            return shop;
        }

        public void setShop(Shop shop) {
            this.shop = shop;
        }
    }
}

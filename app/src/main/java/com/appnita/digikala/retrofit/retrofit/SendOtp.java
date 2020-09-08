package com.appnita.digikala.retrofit.retrofit;

public class SendOtp {
    private String countryCode;
    private String mobileNo;
    private String type;

    public SendOtp(String countryCode, String mobileNo, String type, String username) {
        this.countryCode = countryCode;
        this.mobileNo = mobileNo;
        this.type = type;
        this.username = username;
    }

    private String username;
}

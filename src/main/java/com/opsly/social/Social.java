package com.opsly.social;

public class Social{
    private String twitter;
    private String facebook;
    private String instagram;;

    public Social(String twit, String fb, String ig){
        this.twitter = twit;
        this.facebook = fb;
        this.instagram = ig;
    }

    public String getTwitter(){
        return this.twitter;
    }

    public String getFacebook(){
        return this.facebook;
    }

    public String getInstagram(){
        return this.instagram;
    }
}

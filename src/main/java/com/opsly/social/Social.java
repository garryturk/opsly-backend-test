package com.opsly.social;

public class Social{
    private int twitter;
    private int facebook;
    private int instagram;;

    public Social(int twit, int fb, int ig){
        this.twitter = twit;
        this.facebook = fb;
        this.instagram = ig;
    }

    public int getTwitter(){
        return this.twitter;
    }

    public int getFacebook(){
        return this.facebook;
    }

    public int getInstagram(){
        return this.instagram;
    }
}

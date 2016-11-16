package com.example.materialdesign.model;


public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private String img;

    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title,String img) {
        this.showNotify = showNotify;
        this.title = title;
        this.img=img;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getImage() {
        return img;
    }

    public void setImage(String image) {
        this.img = image;
    }
}

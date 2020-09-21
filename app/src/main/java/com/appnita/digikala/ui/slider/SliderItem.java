package com.appnita.digikala.ui.slider;

public class SliderItem {
    private String title;
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public SliderItem(String title, String image) {
        this.title = title;
        this.image = image;
    }
}

package com.example.pictureit;

public class ImageCreator {
    String name;
    String imageURI;


    public  ImageCreator(String name, String imageURI){
        this.name = name;
        this.imageURI = imageURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }
}

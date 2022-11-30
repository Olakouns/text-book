package io.cassiopee.textbook.payload;

import io.cassiopee.textbook.entities.MediaFile;

public class AddActor {
    private String firstName;
    private String lastName;
    private String phone;
    private String title;
    private String address;
    private String password;
    private String email;
    private MediaFile media;

    public AddActor() {
    }

    public AddActor(String firstName, String lastName, String phone, String title, String address, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.title = title;
        this.address = address;
        this.password = password;
        this.email = email;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MediaFile getMedia() {
        return media;
    }

    public void setMedia(MediaFile media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return "AddActor{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", media=" + media +
                '}';
    }
}

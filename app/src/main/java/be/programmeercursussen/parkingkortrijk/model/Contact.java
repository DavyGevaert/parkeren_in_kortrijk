package be.programmeercursussen.parkingkortrijk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Davy on 17/01/2015.
 */
public class Contact implements Parcelable {
    private String firstName;
    private String lastName;
    private String position;
    private String telephone;
    private String fax;
    private String emailadress;

    public Contact() {
        // empty constructor
    }

    public Contact(String firstName, String lastName, String position, String telephone, String fax, String emailadress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.telephone = telephone;
        this.fax = fax;
        this.emailadress = emailadress;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmailadress() {
        return emailadress;
    }

    public void setEmailadress(String emailadress) {
        this.emailadress = emailadress;
    }

    // constructor voor Parcel
    public Contact(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        position = in.readString();
        telephone = in.readString();
        fax = in.readString();
        emailadress = in.readString();
    }

    public static final Creator<Contact> CREATOR =
            new Creator<Contact>() {
                public Contact createFromParcel(Parcel pc) {
                    return new Contact(pc);
                }

                @Override
                public Contact[] newArray (int size) {
                    return new Contact[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(position);
        dest.writeString(telephone);
        dest.writeString(fax);
        dest.writeString(emailadress);
    }
}

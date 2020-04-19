package com.bs.hw1lab.contacts;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactListContent  {

    private ContactListContent() {


    }

    public static final List<Contact> ITEMS = new ArrayList<Contact>();
    public static final Map<String, Contact> ITEM_MAP = new HashMap<String, Contact>();

    private static final int COUNT = 0;
    static  {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++)
            addItem(createDummyItem(i));
    }

    public static void addItem(Contact item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);

    }

    public static void removeItem(int position) {
        String itemId = ITEMS.get(position).id;
        ITEMS.remove(position);
        ITEM_MAP.remove(itemId);
    }

    private static Contact createDummyItem(int position) {
        return new Contact(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);

        return builder.toString();
    }


    public static class Contact implements Parcelable
             {

        public final String id;
        public final String name;
        public final String surname;
        public final String birthday;
        public final String phoneNumber;
        public final String picPath;

        public Contact(String id, String name, String surname) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.birthday = "";
            this.phoneNumber = "";
            this.picPath = "";
        }

        public Contact(String id, String name, String surname, String birthday, String phoneNumber, String picPath) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.birthday = birthday;
            this.phoneNumber = phoneNumber;
            this.picPath = picPath;
        }

        protected Contact(Parcel in) {
            id = in.readString();
            name = in.readString();
            surname = in.readString();
            birthday = in.readString();
            phoneNumber = in.readString();
            picPath = in.readString();
        }


                 public static final Creator<Contact> CREATOR = new Creator<Contact>() {
                     @Override
                     public Contact createFromParcel(Parcel in) {
                         return new Contact(in);
                     }

                     @Override
                     public Contact[] newArray(int size) {
                         return new Contact[size];
                     }
                 };

                 @Override
                 public int describeContents() {
                     return 0;
                 }

                 @Override
                 public void writeToParcel(Parcel dest, int flags) {
                     dest.writeString(id);
                     dest.writeString(name);
                     dest.writeString(surname);
                     dest.writeString(birthday);
                     dest.writeString(phoneNumber);
                     dest.writeString(picPath);
                 }
             }
}

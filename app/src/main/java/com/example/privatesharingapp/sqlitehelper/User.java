package com.example.privatesharingapp.sqlitehelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class User {

    private int id;
    String username, name, description, phone;
    byte[] image;
    private List<Truck> trucks = new List<Truck>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<Truck> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] ts) {
            return null;
        }

        @Override
        public boolean add(Truck truck) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends Truck> collection) {
            return false;
        }

        @Override
        public boolean addAll(int i, @NonNull Collection<? extends Truck> collection) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(@Nullable Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public Truck get(int i) {
            return null;
        }

        @Override
        public Truck set(int i, Truck truck) {
            return null;
        }

        @Override
        public void add(int i, Truck truck) {

        }

        @Override
        public Truck remove(int i) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<Truck> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<Truck> listIterator(int i) {
            return null;
        }

        @NonNull
        @Override
        public List<Truck> subList(int i, int i1) {
            return null;
        }
    };

    public User(String name, String username, String description, String phone, byte[] image) {
        this.username = username;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.image = image;
    }

    public String getUsername() { return username; }
    public String getDescription() { return description; }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public byte[] getImage() { return image; }

    public List<Truck> getTrucks() {
        return trucks;
    }
}

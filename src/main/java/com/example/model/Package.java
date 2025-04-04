package com.example.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Package {

    public String description;

    @Id
    public int id;

    public float height;

    public float length;

    public float width;

    public static Package of(int id, float length, float width, float height, String description) {
        Package inst = new Package();
        inst.id = id;
        inst.length = length;
        inst.width = width;
        inst.height = height;
        inst.description = description;
        return inst;
    }

    @Override
    public String toString() {
        return "Package id=" + id + "; L=" + length + "; W=" + width + "; H=" + height + " " + description;
    }
}

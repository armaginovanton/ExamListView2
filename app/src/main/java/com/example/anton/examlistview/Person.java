package com.example.anton.examlistview;

import java.util.ArrayList;
import java.util.List;

public class Person implements Comparable<Person> {
    public Person(String name, int phone_number, List<String> skills) {
        this.name = name;
        this.phone_number = phone_number;
        this.skills = skills;
    }

    private String name;
    private int phone_number;
    List<String> skills;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public String getSkills() {
        String result = "";
        for (String skill : skills) {
            result = result +", "+skill;
        }
        result = result.replaceFirst(", ","");
        return result;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    @Override
    public int compareTo(Person another) {
        return this.getName().compareTo(another.getName());
    }
}

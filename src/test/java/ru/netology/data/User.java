package ru.netology.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class User {

    private final String name;
    private final String surName;

    public String toString() {
        return this.getName() + " " + this.getSurName();
    }
}



package com.example.teabot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class TeaOrder {
    private final List<Tea> tea = new LinkedList<>();

}

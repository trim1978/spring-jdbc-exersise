package ru.otus.trim.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode()
//@ToString()
public class Author {
    private int id;
    private String name;
}

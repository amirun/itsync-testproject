package com.itsync.testproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;

    private String author;

    private String genre;

    private String description;

    private int volumeCount;

    private Timestamp createDate;

    @Enumerated(EnumType.STRING)
    private TypeEnum type;

}

package com.itsync.testproject.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class FilterDTO implements Serializable {
    Integer minVolume;
    String author;
    String genre;
    String type;
}

package com.itsync.testproject.dto;

import com.itsync.testproject.model.TypeEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

public record BooksDTO(@NotEmpty String name, @NotEmpty String author, @NotEmpty String genre, String description,
                       @Min(1) int volumeCount,
                       TypeEnum type) implements Serializable {
}

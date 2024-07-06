package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataPerson(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") Optional<Integer> birth_year,
        @JsonAlias("death_year") Optional<Integer> death_year
) {
}

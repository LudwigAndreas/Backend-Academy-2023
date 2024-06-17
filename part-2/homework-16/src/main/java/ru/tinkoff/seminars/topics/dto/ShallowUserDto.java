package ru.tinkoff.seminars.topics.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ShallowUserDto {
    private UUID id;
    private String displayName;
    private String link;
    private String profileImage;
}

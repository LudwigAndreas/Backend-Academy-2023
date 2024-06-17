package ru.tinkoff.seminars.topics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String title;

    private String body;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String link;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate lastEditDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate closedDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer answerCount;

//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private List<AnswerDto> answers;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer likeCount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer dislikeCount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer viewCount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String closedReason;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer score;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> tags;

//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private ShallowUserDto owner;

//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private ShallowUserDto lastEditor;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID authorId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean liked;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean disliked;

}

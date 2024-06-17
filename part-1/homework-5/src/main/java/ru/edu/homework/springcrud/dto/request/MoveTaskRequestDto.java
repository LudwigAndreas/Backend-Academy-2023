package ru.edu.homework.springcrud.dto.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
@Builder
public class MoveTaskRequestDto {

    @NonNull
    private Long sourceCategoryId;

    @NonNull
    private Long targetCategoryId;


}

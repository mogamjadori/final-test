package com.example.GYMFIT.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MemberSearchDto {
    private String searchBy;
    private String searchQuery="";
    private String type;
    private String str;
}

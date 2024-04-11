package com.example.GYMFIT.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MainMemberDto {

    private  Long memId;
    private String memNm;
    private String memBirthDt;
    private String memCno;
    private String memAdr;
    private String memEmlAdr;

    @QueryProjection
    public MainMemberDto(Long memId, String memNm,
                         String memAdr, String memCno,
                         String memEmlAdr, String memBirthDt){
        this.memId = memId;
        this.memNm = memNm;
        this.memBirthDt = memBirthDt;
        this.memCno = memCno;
        this.memAdr = memAdr;
        this.memEmlAdr = memEmlAdr;
    }
}

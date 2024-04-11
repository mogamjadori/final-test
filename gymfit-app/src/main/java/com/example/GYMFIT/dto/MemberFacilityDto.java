package com.example.GYMFIT.dto;

import com.example.GYMFIT.entity.Facility;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberFacilityDto {
    private int totalPrice;
    private String memNm1;
    private List<Facility> facilities;

//    public MemberFacilityDto(MemFac memFac){
//        this.memNm1 = memFac.getMember().
//    }


}

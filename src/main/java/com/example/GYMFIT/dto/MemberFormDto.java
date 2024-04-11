package com.example.GYMFIT.dto;


import com.example.GYMFIT.entity.Facility;
import com.example.GYMFIT.entity.Member;

import com.example.GYMFIT.service.FacilityService;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class MemberFormDto {
    private Long memId;
    private String memNm;
    private String memBirthDt;
    private String memCno;
    private String memAdr;
    private String memEmlAdr;
    private String memRgtDt;

    private FacilityFormDto facilityFormDto;
    private Facility facility;
    private FacilityService facilityService;

    //멤버에 등록되는 이용권 정보
    private Long facilityClCd;
    private String facilityNm;
    private int facilityPrice;
    private String facilityTerm;

    private static ModelMapper modelMapper = new ModelMapper();
    public Member createMember(){
        return modelMapper.map(this, Member.class);
    }
    public static MemberFormDto of (Member member){return modelMapper.map(member, MemberFormDto.class);
    }


}

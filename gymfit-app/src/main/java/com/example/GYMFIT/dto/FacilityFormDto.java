package com.example.GYMFIT.dto;

import com.example.GYMFIT.entity.Facility;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class FacilityFormDto {
    private Long facilityId;
    private Long facilityClCd;
    private String facilityNm;
    private int facilityPrice;
    private int entranceNo;
    private String facilityTerm;
    private static ModelMapper modelMapper = new ModelMapper();
    public Facility createFacility() {
        return modelMapper.map(this, Facility.class);
    }
    public static FacilityFormDto of (Facility facility) {return modelMapper.map(facility, FacilityFormDto.class);
    }
}
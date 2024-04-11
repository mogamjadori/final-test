package com.example.GYMFIT.entity;

import com.example.GYMFIT.dto.FacilityFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "facility")
@Table(name = "facility")
@Getter
@Setter
@ToString
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "facility_id")
    private Long facilityId;

    @Column(nullable = false)
    private Long facilityClCd; //이용권 코드

    @Column(nullable = false)
    private String facilityNm; // 이용권 이름
    @Column(nullable = false)
    private int facilityPrice; // 이용권 가격
    @Column(nullable = false)
    private int entranceNo; // 이용 횟수
    @Column(nullable = false)
    private String facilityTerm; //이용 기간


    public static Facility createFacility(FacilityFormDto facilityFormDto) {
        Facility facility = new Facility();
        facility.setFacilityClCd(facilityFormDto.getFacilityClCd());
        facility.setFacilityNm(facilityFormDto.getFacilityNm());
        facility.setFacilityPrice(facilityFormDto.getFacilityPrice());
        facility.setEntranceNo(facilityFormDto.getEntranceNo());
        facility.setFacilityTerm(facilityFormDto.getFacilityTerm());
        return facility;
    }

    public void updateFacility(FacilityFormDto facilityFormDto) {
        this.facilityClCd = facilityFormDto.getFacilityClCd();
        this.facilityNm = facilityFormDto.getFacilityNm();
        this.facilityPrice = facilityFormDto.getFacilityPrice();
        this.entranceNo = facilityFormDto.getEntranceNo();
        this.facilityTerm = facilityFormDto.getFacilityTerm();
    }
}
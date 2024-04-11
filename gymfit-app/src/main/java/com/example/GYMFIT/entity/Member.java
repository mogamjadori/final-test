package com.example.GYMFIT.entity;

import com.example.GYMFIT.dto.FacilityFormDto;
import com.example.GYMFIT.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;




@Entity(name="member")
@Table(name="member")
@Getter
@Setter
@ToString
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long memId; //회원 ID


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = true)
    private Facility facilityId;  // 이용권  Id

    @Column(nullable = false)
    private String memNm; //회원 이름
    @Column(nullable = false)
    private String memBirthDt; //회원 출생일자
    @Column(nullable = false, name="memCno")
    private String memCno; //회원 연락처
    @Column(nullable = false)
    private String memAdr; //회원 주소
    @Column(nullable = false)
    private String memEmlAdr; //회원 Email 주소

    @Column(nullable = false)
    private String memRgtDt; //회원 등록일자
//
//    @Column
//    private Long facilityClCd; //이용권 코드
//
//    @Column
//    private String facilityNm; // 이용권 이름
//
//    @Column
//    private int facilityPrice; // 이용권 가격
//
//    @Column
//    private String facilityTerm; //이용 기간










    public static Member createMember(MemberFormDto memberFormDto){
        Member member = new Member();
        member.setMemNm(memberFormDto.getMemNm());
        member.setMemBirthDt(memberFormDto.getMemBirthDt());
        member.setMemCno(memberFormDto.getMemCno());
        member.setMemAdr(memberFormDto.getMemAdr());
        member.setMemEmlAdr(memberFormDto.getMemEmlAdr());
        return member;
    }

    public void updateMember(MemberFormDto memberFormDto){
        this.memNm = memberFormDto.getMemNm();
        this.memBirthDt = memberFormDto.getMemBirthDt();
        this.memCno = memberFormDto.getMemCno();
        this.memAdr = memberFormDto.getMemAdr();
        this.memEmlAdr = memberFormDto.getMemEmlAdr();

    }



}

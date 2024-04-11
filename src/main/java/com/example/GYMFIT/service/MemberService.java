package com.example.GYMFIT.service;


import com.example.GYMFIT.dto.MainMemberDto;
import com.example.GYMFIT.dto.MemberFormDto;
import com.example.GYMFIT.dto.MemberSearchDto;
import com.example.GYMFIT.entity.Member;
import com.example.GYMFIT.repository.FacilityRepository;
import com.example.GYMFIT.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final FacilityRepository facilityRepository;
    private final FacilityService facilityService;

    public MemberService(MemberRepository memberRepository, FacilityRepository facilityRepository, FacilityService facilityService) {
        this.memberRepository = memberRepository;
        this.facilityRepository = facilityRepository;
        this.facilityService = facilityService;
    }

    public List<Member> index(){ return memberRepository.findAll();}

    public Member show(Long memId){
        return memberRepository.findById(memId).orElse(null);
    }

    public Long saveMember(MemberFormDto memberFormDto) throws Exception{
        Member member = memberFormDto.createMember();
        memberRepository.save(member);
        return member.getMemId();
    }

    @Transactional(readOnly = true)
    public MemberFormDto getMemberDtoList(Long memId){
        //회원 정보 처리
        Member member = memberRepository.findById(memId).orElseThrow(EntityNotFoundException::new);
        //Entity --> Dto
        MemberFormDto memberFormDto = MemberFormDto.of(member);
        return memberFormDto;
    }
    public Long updateMember(MemberFormDto memberFormDto) throws Exception{
        Member member = memberRepository.findById(memberFormDto.getMemId())
                .orElseThrow(EntityNotFoundException::new);
        member.updateMember(memberFormDto);
        return member.getMemId();
    }

    public Long inputFacToMem(MemberFormDto memberFormDto)throws Exception{
        Member member = memberRepository.findById(memberFormDto.getMemId()).orElseThrow();
        member.setFacilityId(memberFormDto.getFacility());
        //멤버의 facilityId 정보를 set 하고 save를 호출한다
        memberRepository.save(member);
        return member.getMemId();
    }



    @Transactional(readOnly = true) // 회원정보 특정조회
    public Page<Member> getAdminMemberPage(MemberSearchDto memberSearchDto,
                                           Pageable pageable){
        return memberRepository.getAdminMemberPage(memberSearchDto, pageable);
    }

    @Transactional(readOnly = true)  // 메인에서 전체 회원정보 조회하기
    public Page<MainMemberDto> getMainMemberPage(MemberSearchDto memberSearchDto,
                                                 Pageable pageable){
        return memberRepository.getMainMemberPage(memberSearchDto, pageable);
    }




}

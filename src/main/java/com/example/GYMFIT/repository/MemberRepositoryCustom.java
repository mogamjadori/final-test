package com.example.GYMFIT.repository;

import com.example.GYMFIT.dto.MainMemberDto;
import com.example.GYMFIT.dto.MemberSearchDto;
import com.example.GYMFIT.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {

    Page<Member> getAdminMemberPage(MemberSearchDto memberSearchDto,
                                    Pageable pageable);

    Page<MainMemberDto> getMainMemberPage(MemberSearchDto memberSearchDto,
                                          Pageable pageable);
}

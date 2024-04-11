package com.example.GYMFIT.controller;


import com.example.GYMFIT.dto.FacilityFormDto;
import com.example.GYMFIT.dto.FacilitySearchDto;
import com.example.GYMFIT.dto.MemberFormDto;
import com.example.GYMFIT.dto.MemberSearchDto;
import com.example.GYMFIT.entity.Facility;
import com.example.GYMFIT.entity.Member;
import com.example.GYMFIT.service.FacilityService;
import com.example.GYMFIT.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;


import java.util.Optional;


@Controller //이건 Controller
@RequestMapping("/members") //MemberController 의 주소
@RequiredArgsConstructor//필요한 생성자는 알아서 만들어라
public class MemberController {


   // @Autowired//memberservice 불러오기
    private final MemberService memberService;
    private final FacilityService facilityService;

    @GetMapping("/admin/new") //새로운 회원정보 추가하기
    public String memberForm( Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "/member/memberForm";
    }


    @PostMapping("/admin/new") //새로운 회원정보 추가하기
    public String memberNew( @Valid MemberFormDto memberFormDto,
                                BindingResult bindingResult, Model model){ //bindingresult는 memberForm에서 th:if="${#fields.hasErrors절
        if(bindingResult.hasErrors()){
            System.out.println("bindingResult.hasErrors()발생");
            return "/member/memberForm";
        }
        try{
            memberService.saveMember(memberFormDto);
            return "redirect:/members/members";
        }catch (Exception e){
            return "/member/memberForm";
        }
    }

    @GetMapping(value = "/admin/member/{memId}")  //내용 수정을 위해서 한명씩 조회하기
    public String memDto(@PathVariable("memId") Long memId,
                         FacilitySearchDto facilitySearchDto,
                         @PathVariable("page") Optional<Integer> page,Model model) {
        try {
            //member
            MemberFormDto memberFormDto = memberService.getMemberDtoList(memId);
            model.addAttribute("memberFormDto", memberFormDto);

            //facility
            Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
            Page<Facility> facilities = facilityService.getAdminFacilityPage(facilitySearchDto, pageable);
            model.addAttribute("facilities", facilities);
            model.addAttribute("facilitySearchDto", facilitySearchDto);
            model.addAttribute("maxPage",5);

            return "member/memberFormEdit";
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "해당 회원 없음");
            model.addAttribute("memberFormDto", new MemberFormDto());
            return "member/memberForm";
        }
    }

    @PostMapping("/admin/member/{memId}")  //내용 수정을 위해서 한명씩 조회하고 수정한 내용 post 하기
    public String updateMember(@Valid MemberFormDto memberFormDto,
                               BindingResult bindingResult,
                               Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("errorMessage","바인딩 오류");
            return "/member/memberForm";
        }
        try{
            memberService.updateMember(memberFormDto);
            return"redirect:/members/members";
        }catch (Exception e){
            model.addAttribute("errorMessage","Exception 발생");
            return "/member/memberForm";
        }

    }


    @PostMapping("/admin/member/{memId}/input/{facilityId}")  //내용 수정을 위해서 한명씩 조회하고 이용권 등록 post 하기
    public String inputFacToMem(@Valid MemberFormDto memberFormDto,
                               BindingResult bindingResult,
                               Model model,
                                @PathVariable("facilityId") String facilityId
                                ){
        if(bindingResult.hasErrors()){
            model.addAttribute("errorMessage","바인딩 오류");
            return "member/memberForm";
        }
        try{
            Facility tmpFac = new Facility();
            tmpFac.setFacilityId(Long.parseLong(facilityId));
            memberFormDto.setFacility(tmpFac);
            memberService.inputFacToMem(memberFormDto);
            return "redirect:/members/members";
        }catch (Exception e){
            model.addAttribute("errorMessage","Exception 발생");
            return "member/memberForm";
        }

    }


    @GetMapping(value = {"/members","/members/{memId}"}) //등록된 이용권정보 전체조회 페이지
    public String memManage(MemberSearchDto memberSearchDto,
                            @PathVariable("page")Optional<Integer>page,
                            Model model){

        //page 당 출력할 페이지수 조절을 이곳입니다!!                              //pageSize 에서 갯수조절!
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Member> members = memberService.getAdminMemberPage(memberSearchDto, pageable);

        model.addAttribute("members", members);
        model.addAttribute("memberSearchDto", memberSearchDto);
        model.addAttribute("maxPage",5);
        return"member/memberMng";
    }

    @GetMapping(value = "/member/{memId}")  //회원정보 상세 페이지
    public String memDtoRes(Model model,
                            @PathVariable("memId") Long memId){
        MemberFormDto memberFormDto = memberService.getMemberDtoList(memId);
        model.addAttribute("member", memberFormDto);
        return"member/memberDtoRes";
    }


//    @GetMapping(value = {"/"})
//    public String memMain(MemberSearchDto memberSearchDto,
//                            @PathVariable("page")Optional<Integer>page,
//                            Model model){
//
// //page 당 출력할 페이지수 조절을 이곳입니다!!                                 pageSize 에서 갯수조절!
//        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
//        Page<MainMemberDto> members = memberService.getMainMemberPage(memberSearchDto, pageable);
//
//        model.addAttribute("members", members);
//        model.addAttribute("memberSearchDto", memberSearchDto);
//        model.addAttribute("maxPage",5);
//        return"main";
//    }


}

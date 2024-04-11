package com.example.GYMFIT.controller;

import com.example.GYMFIT.dto.FacilityFormDto;
import com.example.GYMFIT.dto.FacilitySearchDto;
import com.example.GYMFIT.entity.Facility;
import com.example.GYMFIT.repository.FacilityRepository;
import com.example.GYMFIT.service.FacilityService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/facility")

public class FacilityController {

    private final FacilityRepository facilityRepository;
    private final FacilityService facilityService;

    public FacilityController(FacilityRepository facilityRepository, FacilityService facilityService) {
        this.facilityRepository = facilityRepository;
        this.facilityService = facilityService;
    }

    @GetMapping("/new")
    public String facilityForm(Model model) {
        model.addAttribute("facilityFormDto", new FacilityFormDto());
        return "/member/facilityForm";
    }

    @PostMapping("/new")
    public String memberNew(@Valid FacilityFormDto facilityFormDto,
                            BindingResult bindingResult, Model model) { //bindingresult는 memberForm에서 th:if="${#fields.hasErrors절
        if (bindingResult.hasErrors()) {
            System.out.println("bindingResult.hasErrors()발생");
            return "/member/facilityForm";
        }
        try {
            facilityService.saveFacility(facilityFormDto);
            return "redirect:/facility/facilities";
        } catch (Exception e) {
            return "/member/facilityForm";
        }
    }

    @GetMapping(value = "/admin/{facilityId}")  //내용 수정을 위해서 한명씩 조회하기
    public String facDto(@PathVariable("facilityId") Long facilityId, Model model) {
        try {
            FacilityFormDto facilityFormDto = facilityService.getFacilityDtoList(facilityId);
            model.addAttribute("facilityFormDto", facilityFormDto);
            return "/member/facilityForm";
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "해당 이용권 없음");
            model.addAttribute("facilityFormDto", new FacilityFormDto());
            return "/member/facilityForm";
        }
    }

    @PostMapping("/admin/{facilityId}")  //내용 수정을 위해서 한개씩 조회하고 수정한 내용 post 하기
    public String updateFacility(@Valid FacilityFormDto facilityFormDto,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "바인딩 오류");
            return "/member/facilityForm";
        }
        try {
            facilityService.updateFacility(facilityFormDto);
            return "redirect:/facility/facilities";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Exception 발생");
            return "/member/facilityForm";
        }

    }

    @GetMapping(value = {"/facilities", "/facilities/{facilityId}"}) //등록된 이용권 전체조회 페이지
    public String facManage(FacilitySearchDto facilitySearchDto,
                            @PathVariable("page") Optional<Integer> page,
                            Model model) {
        //page 당 출력할 페이지수 조절을 이곳입니다!!                              //pageSize 에서 갯수조절!
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Facility> facilities = facilityService.getAdminFacilityPage(facilitySearchDto, pageable);

        model.addAttribute("facilities", facilities);
        model.addAttribute("facilitySearchDto", facilitySearchDto);
        model.addAttribute("maxPage", 5);
        return "member/facilityMng";
    }

    @GetMapping(value = "/facility/{facilityId}")  //이용권 정보 상세 페이지
    public String facDtoRes(Model model,
                            @PathVariable("facilityId") Long facilityId) {
        FacilityFormDto facilityFormDto = facilityService.getFacilityDtoList(facilityId);
        model.addAttribute("facility", facilityFormDto);
        return "member/facilityDtoRes";
    }

    @PostMapping("/delete")
    public String facilityDelete(@Valid FacilityFormDto facilityFormDto) throws Exception {
        facilityService.deleteFacility(facilityFormDto);
        return "redirect:/facility/facilities";
    }

}

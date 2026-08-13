package com.personal.personalservice.controller;

import com.personal.personalservice.model.request.PostPersonalInquiryRequest;
import com.personal.personalservice.model.response.PostPersonalInquiryResponse;
import com.personal.personalservice.service.PostPersonalInquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class PersonalController {

    private final PostPersonalInquiryService postPersonalInquiryService;

    @PostMapping("/personal/v1/inquiry")
    public PostPersonalInquiryResponse personalInquiry(@RequestBody PostPersonalInquiryRequest request) {
        log.info("Inquiry data");
        return postPersonalInquiryService.execute(request);
    }
}

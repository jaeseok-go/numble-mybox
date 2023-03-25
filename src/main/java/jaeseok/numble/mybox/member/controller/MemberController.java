package jaeseok.numble.mybox.member.controller;

import jaeseok.numble.mybox.common.response.MyBoxResponse;
import jaeseok.numble.mybox.member.dto.LoginDto;
import jaeseok.numble.mybox.member.dto.MemberSignUpDto;
import jaeseok.numble.mybox.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/v1/members")
    public ResponseEntity signUp(@RequestBody MemberSignUpDto memberSignUpDto) {
        MyBoxResponse myBoxResponse = new MyBoxResponse(memberService.signUp(memberSignUpDto));
        return ResponseEntity.ok(myBoxResponse);
    }

    @PostMapping("/v1/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto) {
        MyBoxResponse myBoxResponse = new MyBoxResponse(memberService.login(loginDto));
        return ResponseEntity.ok(myBoxResponse);
    }
}

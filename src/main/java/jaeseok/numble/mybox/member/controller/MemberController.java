package jaeseok.numble.mybox.member.controller;

import jaeseok.numble.mybox.common.response.MyBoxResponse;
import jaeseok.numble.mybox.member.dto.LoginParam;
import jaeseok.numble.mybox.member.dto.SignUpParam;
import jaeseok.numble.mybox.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/v1/members")
    public ResponseEntity signUp(@RequestBody SignUpParam signUpParam) {
        MyBoxResponse myBoxResponse = new MyBoxResponse(memberService.signUp(signUpParam));
        return ResponseEntity.ok(myBoxResponse);
    }

    @PostMapping("/v1/login")
    public ResponseEntity login(@RequestBody LoginParam loginParam) {
        MyBoxResponse myBoxResponse = new MyBoxResponse(memberService.login(loginParam));
        return ResponseEntity.ok(myBoxResponse);
    }

    @GetMapping("/v1/member")
    public ResponseEntity retrieveMember() {
        MyBoxResponse myBoxResponse = new MyBoxResponse((memberService.retrieveMember()));
        return ResponseEntity.ok(myBoxResponse);
    }
}

package fouriting.flockproject.controller;

import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.dto.MemberLogInDto;
import fouriting.flockproject.domain.dto.MemberResponseDto;
import fouriting.flockproject.domain.dto.MemberSignUpDto;
import fouriting.flockproject.service.MemberService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "Test Hello",
               description = "Test Hello description")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK!!"),
            @ApiResponse(code = 400, message = "BAD REQUEST!!"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @GetMapping
    public String helloWorld(){
        return "Hello World!";
    }

    @Operation(summary = "로그인", description = "로그인 Post Controller 입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK!!"),
            @ApiResponse(code = 400, message = "BAD REQUEST!!"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @PostMapping("/login")
    public ResponseEntity<MemberResponseDto> login(@RequestBody MemberLogInDto memberLogInDto){
        return new ResponseEntity<>(memberService.logIn(memberLogInDto), HttpStatus.OK);
    }

    @Operation(summary = "회원가입", description = "회원가입 Post Controller 입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK!!"),
            @ApiResponse(code = 400, message = "BAD REQUEST!!"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberSignUpDto memberSignUpDto){
        return new ResponseEntity<>(memberService.signUp(memberSignUpDto), HttpStatus.OK);
    }
}
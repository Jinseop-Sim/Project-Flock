package fouriting.flockproject.controller;

import fouriting.flockproject.domain.dto.request.auth.MemberLogInDto;
import fouriting.flockproject.domain.dto.response.MemberResponseDto;
import fouriting.flockproject.domain.dto.request.auth.MemberSignUpDto;
import fouriting.flockproject.service.AuthService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "로그인", description = "로그인 Post Controller 입니다." +
    "\n ### 요청 변수 : loginId, passwd")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK!!"),
            @ApiResponse(code = 400, message = "BAD REQUEST!!"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid MemberLogInDto memberLogInDto, HttpSession httpSession){
        return new ResponseEntity<>(authService.login(memberLogInDto, httpSession), HttpStatus.OK);
    }

    @Operation(summary = "회원가입", description = "회원가입 Post Controller 입니다." +
    "\n ### 요청 변수 : loginId, passwd, nickname")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK!!"),
            @ApiResponse(code = 400, message = "BAD REQUEST!!"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody @Valid MemberSignUpDto memberSignUpDto){
        return new ResponseEntity<>(authService.signUp(memberSignUpDto), HttpStatus.OK);
    }
}

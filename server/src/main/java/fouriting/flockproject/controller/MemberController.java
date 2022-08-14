package fouriting.flockproject.controller;

import fouriting.flockproject.domain.dto.request.WebtoonRequestDto;
import fouriting.flockproject.domain.dto.response.MyPageResponseDto;
import fouriting.flockproject.domain.dto.response.WebtoonSearchDto;
import fouriting.flockproject.service.MemberService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello World!";
    }

    @Operation(summary = "마이페이지", description = "마이페이지 요청 Controller 입니다." +
    "\n ### 권한이 없을 시(로그인X, 토큰 만료) 응답으로 401 Unauthorized가 반환됩니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK!!"),
            @ApiResponse(code = 400, message = "BAD REQUEST!!"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @GetMapping("/mypage")
    public ResponseEntity<MyPageResponseDto> myPage(HttpServletRequest request){
        return new ResponseEntity<>(memberService.showMyPage(), HttpStatus.OK);
    }

    @Operation(summary = "검색 기능", description = "검색 기능 요청 Controller입니다."+
    "\n ### 요청 변수 : JSON형태로 String Webtoon Title.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK!!"),
            @ApiResponse(code = 400, message = "BAD REQUEST!!"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @PostMapping("/search")
    public ResponseEntity<WebtoonSearchDto> searchWebtoon(@RequestBody WebtoonRequestDto webtoonRequestDto){
        return new ResponseEntity<>(memberService.searchWebtoon(webtoonRequestDto.getName()), HttpStatus.OK);
    }
}
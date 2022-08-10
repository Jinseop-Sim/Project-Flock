package fouriting.flockproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyPageController {

    @Operation(summary = "마이페이지", description = "마이페이지 JSON")
    @GetMapping("/mypage")
    public String myPage(){
        return "It's my page!";
    }
}

package fouriting.flockproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webtoons")
public class WebtoonController {

    @Operation(summary = "일상 / 개그", description = "일상 / 개그 장르 목록입니다.")
    @GetMapping("/gag")
    public String gagPage(){
        return "일상 / 개그 페이지입니다.";
    }

    @Operation(summary = "판타지 / SF", description = "판타지 / SF 장르 목록입니다.")
    @GetMapping("/fantasy")
    public String fantasyPage(){
        return "판타지 / SF 페이지입니다.";
    }

    @Operation(summary = "스포츠", description = "스포츠 장르 목록입니다.")
    @GetMapping("/sports")
    public String sportsPage(){
        return "스포츠 페이지입니다.";
    }

    @Operation(summary = "공포 / 스릴러", description = "공포 / 스릴러 장르 목록입니다.")
    @GetMapping("/horror")
    public String horrorPage(){
        return "공포 / 스릴러 페이지입니다.";
    }

    @Operation(summary = "순정", description = "순정 장르 목록입니다.")
    @GetMapping("/love")
    public String lovePage(){
        return "순정 페이지입니다.";
    }

    @Operation(summary = "무협 / 사극", description = "무협 / 사극 장르 목록입니다.")
    @GetMapping("/oriental")
    public String orientalPage(){
        return "스포츠 페이지입니다.";
    }
}

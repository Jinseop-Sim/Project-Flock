package fouriting.flockproject.controller;

import fouriting.flockproject.domain.Genre;
import fouriting.flockproject.domain.dto.request.CommentRequestDto;
import fouriting.flockproject.domain.dto.response.CommentResponseDto;
import fouriting.flockproject.domain.dto.response.infoClass.WebtoonInfo;
import fouriting.flockproject.domain.dto.response.WebtoonSearchDto;
import fouriting.flockproject.service.WebtoonService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/webtoons")
public class WebtoonController {
    private final WebtoonService webtoonService;

    @Operation(summary = "댓글 등록", description = "개그 장르 댓글 등록 Controller 입니다." +
            "\n ### 요청 변수" +
            "\n 댓글 내용(String contents)" +
            "\n ### 이 기능은 로그인을 해야만 사용 가능합니다.")
    @PostMapping("/{webtoonId}/comment")
    public ResponseEntity<CommentResponseDto> postComment(@PathVariable Long webtoonId,
                                                          @RequestBody CommentRequestDto commentRequestDto){
        return new ResponseEntity<>(webtoonService.addComment(webtoonId, commentRequestDto), HttpStatus.OK);
    }

    @Operation(summary = "웹툰 목록 조회", description = "웹툰 목록 조회 컨트롤러입니다."+
            "\n ### 요청 변수 " +
            "\n QueryParameter로 장르를 넘겨주시면 됩니다." +
            "\n ex) domain.com/webtoons?genre=개그")
    @GetMapping()
    public ResponseEntity<WebtoonSearchDto> webtoonList(@RequestParam Genre genre){
        return new ResponseEntity<>(webtoonService.showWebtoonList(genre), HttpStatus.OK);
    }

    @Operation(summary = "웹툰 세부 내용", description = "웹툰 목록 조회 컨트롤러입니다."+
            "\n ### 요청 변수 " +
            "\n Webtoon의 ID 값을 경로에 넘겨주시면 됩니다." +
            "\n ex) domain.com/webtoons/1")
    @GetMapping("/{webtoonId}")
    public ResponseEntity<WebtoonInfo> webtoonDetail(@PathVariable Long webtoonId){
        return new ResponseEntity<>(webtoonService.showWebtoonDetail(webtoonId), HttpStatus.OK);
    }
}

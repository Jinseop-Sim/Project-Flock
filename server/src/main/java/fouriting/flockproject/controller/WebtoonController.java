package fouriting.flockproject.controller;

import fouriting.flockproject.domain.dto.request.webtoon.WebtoonRequestDto;
import fouriting.flockproject.domain.dto.response.StarResponseDto;
import fouriting.flockproject.domain.dto.response.WebtoonListDto;
import fouriting.flockproject.domain.enumClass.Genre;
import fouriting.flockproject.domain.dto.request.AddStarRequestDto;
import fouriting.flockproject.domain.dto.request.comment.CommentRequestDto;
import fouriting.flockproject.domain.dto.response.infoClass.WebtoonDetailResponseDto;
import fouriting.flockproject.service.WebtoonService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public ResponseEntity<String> postComment(@PathVariable Long webtoonId,
                                              @RequestBody CommentRequestDto commentRequestDto,
                                              HttpServletRequest request){
        return new ResponseEntity<>(webtoonService.addComment(webtoonId, commentRequestDto, request), HttpStatus.OK);
    }

    @PostMapping("/{webtoonId}/comment/{commentId}")
    public ResponseEntity<String> postChildComment(@PathVariable Long webtoonId, @PathVariable Long commentId,
                                                   @RequestBody CommentRequestDto commentRequestDto,
                                                   HttpServletRequest request){
        return new ResponseEntity<>(webtoonService.addChildComment(webtoonId, commentId, commentRequestDto, request), HttpStatus.OK);
    }

    @Operation(summary = "웹툰 목록 조회", description = "웹툰 목록 조회 컨트롤러입니다."+
            "\n ### 요청 변수 " +
            "\n QueryParameter로 장르를 넘겨주시면 됩니다." +
            "\n ex) domain.com/webtoons?genre=개그")
    @GetMapping
    public ResponseEntity<List<WebtoonListDto>> showWebtoonList(@RequestParam Genre genre){
        return new ResponseEntity<>(webtoonService.showWebtoonList(genre), HttpStatus.OK);
    }

    @Operation(summary = "웹툰 세부 내용", description = "웹툰 목록 조회 컨트롤러입니다."+
            "\n ### 요청 변수 " +
            "\n Webtoon의 ID 값을 경로에 넘겨주시면 됩니다." +
            "\n ex) domain.com/webtoons/1")
    @GetMapping("/{webtoonId}")
    public ResponseEntity<WebtoonDetailResponseDto> webtoonDetail(@PathVariable Long webtoonId, HttpServletRequest request){
        return new ResponseEntity<>(webtoonService.showWebtoonDetail(webtoonId, request), HttpStatus.OK);
    }

    @Operation(summary = "웹툰 별점 등록", description = "웹툰 별점 등록 컨트롤러입니다."+
            "\n ### 요청 변수 " +
            "\n Webtoon의 ID 값을 경로에 넘기고, 별점 값이 JSON 형태로 넘어와야 합니다." +
            "\n ex) domain.com/webtoons/1")
    @PostMapping("/{webtoonId}/star")
    public ResponseEntity<StarResponseDto> postStarToWebtoon(@PathVariable Long webtoonId,
                                                             @RequestBody AddStarRequestDto addStarRequestDto,
                                                             HttpServletRequest request){
        return new ResponseEntity<>(webtoonService.addStarToWebtoon(webtoonId, addStarRequestDto, request), HttpStatus.OK);
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
    public ResponseEntity<List<WebtoonListDto>> searchWebtoon(@RequestBody WebtoonRequestDto webtoonRequestDto){
        return new ResponseEntity<>(webtoonService.searchWebtoon(webtoonRequestDto), HttpStatus.OK);
    }
}

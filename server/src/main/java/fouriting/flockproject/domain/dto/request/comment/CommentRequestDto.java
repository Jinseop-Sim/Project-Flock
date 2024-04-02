package fouriting.flockproject.domain.dto.request.comment;

import fouriting.flockproject.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    private String contents;
}
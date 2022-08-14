package fouriting.flockproject.domain.dto.response;

import fouriting.flockproject.domain.dto.response.infoClass.WebtoonInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebtoonSearchDto {
    private List<WebtoonInfo> webtoonInfoList;
}

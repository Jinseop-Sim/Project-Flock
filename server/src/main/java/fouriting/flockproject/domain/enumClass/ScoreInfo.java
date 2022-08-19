package fouriting.flockproject.domain.enumClass;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
public class ScoreInfo {
    private Integer scoreOne;
    private Integer scoreFive;
}

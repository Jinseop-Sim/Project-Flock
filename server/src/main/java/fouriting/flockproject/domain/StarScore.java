package fouriting.flockproject.domain;

import javax.persistence.Embeddable;
@Embeddable
public class StarScore {
    private Long scoreOne;
    private Long scoreFive;
    private Long scoreSum;
    private Double scoreAvg;
}
package ee.jakopuu.decathlon.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String spordiala;   // nt "100m", "kaugushüpe"
    private double tulemus;     // nt 10.5 või 7.50
    private int punktid;        // back-end arvutab ise

    @ManyToOne
    @JoinColumn(name = "athlete_id")
    private Athlete athlete;

    public Result(String spordiala, double tulemus, int punktid, Athlete athlete) {
        this.spordiala = spordiala;
        this.tulemus = tulemus;
        this.punktid = punktid;
        this.athlete = athlete;
    }
}
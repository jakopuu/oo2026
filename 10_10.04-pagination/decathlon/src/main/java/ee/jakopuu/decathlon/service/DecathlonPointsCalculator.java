package ee.jakopuu.decathlon.service;

import org.springframework.stereotype.Component;

@Component
public class DecathlonPointsCalculator {

    public int calculate(String spordiala, double tulemus) {
        return switch (spordiala.toLowerCase()) {
            case "100m"          -> raja(25.4347, 18.0,   1.81, tulemus);
            case "pikkushüpe",
                 "kaugushüpe"    -> valja(0.14354, 220.0, 1.40, tulemus * 100);
            case "kuulitõuge",
                 "kuul"          -> heide(51.39,   1.5,   1.05, tulemus);
            case "kõrgushüpe"    -> valja(0.8465,  75.0,  1.42, tulemus * 100);
            case "400m"          -> raja(1.53775, 82.0,   1.81, tulemus);
            case "110m tõkked",
                 "110m"          -> raja(5.74352, 28.5,   1.92, tulemus);
            case "kettaheide",
                 "ketas"         -> heide(12.91,   4.0,   1.10, tulemus);
            case "teivashüpe",
                 "teivas"        -> valja(0.2797,  100.0, 1.35, tulemus * 100);
            case "odavise",
                 "oda"           -> heide(10.14,   7.0,   1.08, tulemus);
            case "1500m"         -> raja(0.03768, 480.0,  1.85, tulemus);
            default -> throw new RuntimeException(
                    "Tundmatu spordiala: " + spordiala +
                            ". Kasuta: 100m, kaugushüpe, kuul, kõrgushüpe, 400m, " +
                            "110m tõkked, ketas, teivas, oda, 1500m"
            );
        };
    }

    // Rajaalad (100m, 400m, 110m tõkked, 1500m) — väiksem tulemus = rohkem punkte
    private int raja(double A, double B, double C, double T) {
        if (T >= B) return 0;
        return (int) (A * Math.pow(B - T, C));
    }

    // Väljaalad (kaugus, kõrgus, teivas) — suurem tulemus = rohkem punkte
    private int valja(double A, double B, double C, double M) {
        if (M <= B) return 0;
        return (int) (A * Math.pow(M - B, C));
    }

    // Heited (kuul, ketas, oda) — suurem tulemus = rohkem punkte
    private int heide(double A, double B, double C, double M) {
        if (M <= B) return 0;
        return (int) (A * Math.pow(M - B, C));
    }
}
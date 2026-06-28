package ee.jakopuu.alamklassid;

public class Triangle extends Polygon {

    private final double a;
    private final double b;
    private final double c;
    private final double korgus; // kõrgus a-le, pindala arvutamiseks

    public Triangle(double a, double b, double c, double korgus) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.korgus = korgus;
    }

    @Override
    public double pindala() {
        return (a * korgus) / 2;
    }

    @Override
    public double umbermoot() {
        return a + b + c;
    }

    @Override
    public int kylgedeArv() {
        return 3;
    }
}

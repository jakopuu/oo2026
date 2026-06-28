package ee.jakopuu.alamklassid;

public class Rectangle extends Polygon {

    protected final double laius;
    protected final double korgus;

    public Rectangle(double laius, double korgus) {
        this.laius = laius;
        this.korgus = korgus;
    }

    @Override
    public double pindala() {
        return laius * korgus;
    }

    @Override
    public double umbermoot() {
        return 2 * (laius + korgus);
    }

    @Override
    public int kylgedeArv() {
        return 4;
    }
}

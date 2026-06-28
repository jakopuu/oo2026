package ee.jakopuu.alamklassid;

public class Circle extends Shape {

    private final double raadius;

    public Circle(double raadius) {
        this.raadius = raadius;
    }

    @Override
    public double pindala() {
        return Math.PI * raadius * raadius;
    }

    @Override
    public double umbermoot() {
        return 2 * Math.PI * raadius;
    }
}

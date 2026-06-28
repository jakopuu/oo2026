package ee.jakopuu.alamklassid;

public abstract class Shape {

    public abstract double pindala();

    public abstract double umbermoot();

    @Override
    public String toString() {
        return String.format("%s: pindala=%.2f, ümbermõõt=%.2f",
                this.getClass().getSimpleName(), pindala(), umbermoot());
    }
}

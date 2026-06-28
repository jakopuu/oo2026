package ee.jakopuu.alamklassid;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Triangle(5, 4, 3, 4));
        shapes.add(new Rectangle(6, 3));
        shapes.add(new Square(4));
        shapes.add(new Circle(2.5));

        System.out.println("Kõik kujundid (näitab, et iga alamklass oskab enda pindala/ümbermõõtu arvutada):");
        for (Shape shape : shapes) {
            System.out.println(shape);
        }

        System.out.println();
        System.out.println("Hulknurgad eraldi (Polygon alamklassid oskavad ka külgede arvu öelda):");
        for (Shape shape : shapes) {
            if (shape instanceof Polygon polygon) {
                System.out.println(shape.getClass().getSimpleName() + " - külgi: " + polygon.kylgedeArv());
            }
        }

        double kokkuPindala = shapes.stream().mapToDouble(Shape::pindala).sum();
        System.out.printf("%nKõikide kujundite pindalade summa: %.2f%n", kokkuPindala);
    }
}

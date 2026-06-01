import java.util.Scanner;

public class CircleOperations {

    static double calculateArea(double r) {
        return Math.PI * r * r;
    }

    static double calculateCircumference(double r) {
        return 2 * Math.PI * r;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter radius: ");
        double radius = sc.nextDouble();

        System.out.printf("Area = %.2f%n", calculateArea(radius));
        System.out.printf("Circumference = %.2f%n", calculateCircumference(radius));

        sc.close();
    }
}

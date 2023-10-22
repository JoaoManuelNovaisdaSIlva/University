public class Main {
    public static void main(String[] args) {
        Poly p = new PolyAsMap();
        p.addMonomio(0,5);
        p.addMonomio(5, -10.00);
        p.addMonomio(2, -4.0);
        System.out.println(p.toString());
    }
}
package number.game;

public class Main {
    public static void main(String[] args) {

        ScoreCalculator scoreCalculator = new ScoreCalculator(21,100,90);

        System.out.println("91- " + scoreCalculator.calculateTotalPoints(21,91));
        System.out.println("80- " +scoreCalculator.calculateTotalPoints(21,80));
        System.out.println("70- " +scoreCalculator.calculateTotalPoints(21,70));
        System.out.println("60- " +scoreCalculator.calculateTotalPoints(21,60));
        System.out.println("50- " +scoreCalculator.calculateTotalPoints(21,50));
        System.out.println("40- " +scoreCalculator.calculateTotalPoints(21,40));
        System.out.println("30- " +scoreCalculator.calculateTotalPoints(21,30));
        System.out.println("20- " +scoreCalculator.calculateTotalPoints(21,20));
        System.out.println("10- " +scoreCalculator.calculateTotalPoints(21,10));

    }
}
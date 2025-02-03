package number.game;

public class ScoreCalculator {

    private final int target;
    private final int maxPointsForProximity;
    private final int maxTimeSeconds;

    public ScoreCalculator(int target, int maxPointsForProximity, int maxTimeSeconds) {
        this.target = target;
        this.maxPointsForProximity = maxPointsForProximity;
        this.maxTimeSeconds = maxTimeSeconds;
    }

    public int calculatePointsForProximity(int result) {
        int proximityPoints = maxPointsForProximity - Math.abs(result - target);

        return proximityPoints;
    }

    public int calculatePointsForTimeSpent(int timeSpentOnSolution) {
        if (timeSpentOnSolution > maxTimeSeconds) {
            return 0;
        }
        return (int) ((maxTimeSeconds - timeSpentOnSolution) * (double) maxPointsForProximity / maxTimeSeconds);
    }

    public int calculateTotalPoints(int result, int timeSpentOnSolution) {
        int proximityPoints = calculatePointsForProximity(result);
        int timePoints = calculatePointsForTimeSpent(timeSpentOnSolution);

        return proximityPoints + timePoints;
    }
}

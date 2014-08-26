package gear;

public class Gear {

    private Stats stats;

    public Gear() {
    }

    public void addStats(Stats stats) {
        this.stats = Stats.combinedStats(this.stats, stats);
    }
}

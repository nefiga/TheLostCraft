package gear;

public class Stats {

    public final int str, agi, itl;

    public Stats(int str, int agi, int itl) {
        this.str = str;
        this.agi = agi;
        this.itl = itl;
    }

    public static Stats combinedStats(Stats s1, Stats s2) {
        return new Stats(s1.str + s2.str, s1.agi + s2.agi, s1.itl + s2.itl);
    }
}

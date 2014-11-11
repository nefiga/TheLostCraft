package gear;

public class Stats {

    public static final int STR = 0, AGI = 1, INT = 2, HARVEST_RANGE = 3;

    private static int statCount = 4;

    private int[] statsList = new int[statCount];

    public int getStr() {
        return statsList[STR];
    }

    public int getAgi() {
        return statsList[AGI];
    }

    public int getIntl() {
        return statsList[INT];
    }

    public int getHarvestRange() {
        return statsList[HARVEST_RANGE];
    }

    public int getStat(int position) {
        return statsList[position];
    }

    public void setStr(int str) {
        statsList[STR] = str;
    }

    public void setAgi(int agi) {
        statsList[AGI] = agi;
    }

    public void setIntl(int intl) {
        statsList[INT] = intl;
    }

    public void setHarvestRange(int harvestRange) {
        statsList[HARVEST_RANGE] = harvestRange;
    }

    public void setStat(int position, int stat) {
        statsList[position] = stat;
    }

    public static Stats combinedStats(Stats s1, Stats s2) {
        Stats stat = new Stats();
        for (int i = 0; i < statCount; i++) {
            stat.setStat(i, s1.getStat(i) + s2.getStat(i));
        }
        return stat;
    }
}

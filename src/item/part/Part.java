package item.part;

import item.Item;
import gear.Stats;

public class Part extends Item{

    public final Stats stats;

    public Part(String image, Stats stats) {
        super(image);
        this.stats = stats;
    }

    public Stats getStats() {
        return stats;
    }
}

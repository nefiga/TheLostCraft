package gear.tool;

import game.graphics.ImageManager;
import item.part.Bracket;
import item.part.LongHandle;
import item.part.PickHead;

public class PickAxe extends Tool{

    LongHandle handle;
    PickHead head;
    Bracket bracket;

    public PickAxe(String name, LongHandle handle, PickHead head, Bracket bracket) {
        super(name);

        this.handle = handle;
        addStats(handle.getStats());
        this.head = head;
        addStats(head.getStats());
        this.bracket = bracket;
        addStats(bracket.getStats());

        // Combining the texture of the items  that make this pick axe and setting the image
        image = ImageManager.combineImages(handle.getImage(), head.getImage(), bracket.getImage());
        setTexture(handle.getName() + head.getName() + bracket.getName(), image);
    }
    
    public int getStrength() {
        return stats.getStr();
    }
}

package gear.tool;

import gear.Gear;
import item.part.Bracket;
import item.part.LongHandle;
import item.part.PickHead;

public class PickAxe extends Gear{

    LongHandle handle;
    PickHead head;
    Bracket bracket;

    public PickAxe(LongHandle handle, PickHead head, Bracket bracket) {
        this.handle = handle;
        addStats(handle.getStats());
        this.head = head;
        addStats(head.getStats());
        this.bracket = bracket;
        addStats(bracket.getStats());
    }
}

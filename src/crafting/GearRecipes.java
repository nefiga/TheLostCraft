package crafting;

import gear.tool.PickAxe;
import item.part.Bracket;
import item.part.LongHandle;
import item.part.PickHead;

public class GearRecipes {

    public static PickAxe craftPickAxe(LongHandle handle, PickHead head, Bracket bracket) {
        return new PickAxe(handle,  head, bracket);
    }
}

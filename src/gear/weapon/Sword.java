package gear.weapon;

import game.graphics.ImageManager;
import gear.Gear;
import item.part.Blade;
import item.part.Guard;
import item.part.LongHandle;

public class Sword extends Weapon{

    Blade blade;
    Guard guard;
    LongHandle handle;

    public Sword(String name, Blade blade, Guard guard, LongHandle handle) {
        super(name);

        this.blade = blade;
        addStats(blade.getStats());
        this.guard = guard;
        addStats(guard.getStats());
        this.handle = handle;
        addStats(handle.getStats());

        // Combining the Texture from the items that make up this sword then setting the texture
        image = ImageManager.combineImages(handle.getImage(), blade.getImage(), guard.getImage());
        setTexture(blade.getName() + guard.getName() + handle.getName(), image);
    }
}

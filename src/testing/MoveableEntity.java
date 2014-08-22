package testing;

import entity.LivingEntity;

public class MoveableEntity extends LivingEntity{

    public MoveableEntity(int positionX, int positionY, int textureX, int textureY, int width, int height) {
        super(positionX, positionY, textureX, textureY, width, height);
        setMoveable(true);
    }
}

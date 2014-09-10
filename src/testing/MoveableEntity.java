package testing;

import entity.LivingEntity;

public class MoveableEntity extends LivingEntity{

    public MoveableEntity(int positionX, int positionY) {
        super(positionX, positionY);
        setMoveable(true);
    }
}

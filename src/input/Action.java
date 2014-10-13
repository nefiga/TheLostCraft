package input;

public class Action {

    private String name;
    private int key;

    private boolean holding, pressed;

    public Action(String name, int key) {
        this.name = name;
        this.key = key;
    }

    public void press() {
        if (!holding) {
            pressed = true;
            holding = true;
        }
    }

    public void release() {
        holding = false;
    }

    public boolean isHolding() {
        return holding;
    }

    public boolean isPressed() {
        if (pressed){
            pressed = false;
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public int getKey() {
        return key;
    }
}

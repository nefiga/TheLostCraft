package menu;


public class Result {

    public static final int BACK = -1;

    private String[] strings;

    private String string;

    private int selection;

    private int state;

    private int previousState;

    public String[] getStrings() {
        return strings;
    }

    public String getString() {
        return string;
    }

    public int getSelection() {
        return selection;
    }

    public int getState() {
        return state;
    }

    public int getPreviousState() {
        return previousState;
    }

    public void setStringArray(String[] strings) {
        this.strings = strings;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void setReturnString(String string) {
        this.string = string;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setPreviousState(int state) {
        this.previousState = state;
    }
}

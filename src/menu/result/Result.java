package menu.result;


public class Result {

    private String[] strings;

    private String string;

    private int selection;

    public String[] getStrings() {
        return strings;
    }

    public String getString() {
        return string;
    }

    public int getSelection() {
        return selection;
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
}

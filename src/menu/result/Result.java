package menu.result;

import java.util.List;

public class Result {

    public List<String> strings;

    String string;

    int selection;

    public List<String> getStrings() {
        return strings;
    }

    public String getReturnString() {
        return string;
    }

    public int getSelection() {
        return selection;
    }

    public void setStringList(List<String> strings) {
        this.strings = strings;
    }

    public void setReturnString(String string) {
        this.string = string;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }
}

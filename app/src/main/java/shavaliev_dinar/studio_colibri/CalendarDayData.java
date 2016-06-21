package shavaliev_dinar.studio_colibri;

import java.util.HashMap;
import java.util.Map;

public class CalendarDayData {

    private String data;
    private boolean isBusy;

    public CalendarDayData(String data, boolean isBusy) {
        this.data = data;
        this.isBusy = isBusy;
    }

    public CalendarDayData() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> res = new HashMap<>();
        res.put("date", data);
        res.put("isBusy", isBusy);
        return res;
    }
}

package rs.projekat.enrg.energymeters.custom;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * ******************************
 * Created by milan on 8/7/2016.
 * ******************************
 */
public class MyValueFormatter implements ValueFormatter {

    public MyValueFormatter() {

    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return String.format("%.3f", value);
    }

}

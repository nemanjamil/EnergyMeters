package rs.projekat.enrg.energymeters.custom;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.formatter.FormattedStringCache;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import rs.projekat.enrg.energymeters.common.Constants;
import rs.projekat.enrg.energymeters.model.GraphicsIp;
import rs.projekat.enrg.energymeters.model.PodaciGrafik;

/**
 * ******************************
 * Created by milan on 8/6/2016.
 * ******************************
 */
public class MyAxisValueFormatter implements AxisValueFormatter {

    private FormattedStringCache.PrimFloat mFormattedStringCache;
    private GraphicsIp graficiTip;
    private SimpleDateFormat sdf;
    private ArrayList<String> labels = new ArrayList<>();

    public MyAxisValueFormatter(GraphicsIp graficiTip) {
        this.graficiTip = graficiTip;
        sdf = new SimpleDateFormat(Constants.DATE_FORMAT_JSON);

        for (PodaciGrafik pg : graficiTip.getData()) {
            try {
                // Pack "from date" in specific format
                sdf.applyPattern(Constants.DATE_FORMAT_JSON);
                Date d1 = sdf.parse(pg.getDateTimeFrom());
                sdf.applyPattern(Constants.DATE_FORMAT_CHART_LABEL);
                String l1 = sdf.format(d1);

                // Pack "from date" in specific format
                sdf.applyPattern(Constants.DATE_FORMAT_JSON);
                Date d2 = sdf.parse(pg.getDateTimeTo());
                sdf.applyPattern(Constants.DATE_FORMAT_CHART_LABEL);
                String l2 = sdf.format(d2);

                // Create final label
                String label = l1 + " - " + l2;
                // Add label to Axis
                labels.add(label);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // Prepare xAxis values
        //return labels.get((int) value);
        int index = ((int) value);
        int novaVar = graficiTip.getData().get(index).getMeasurementId();
        String s = String.valueOf(novaVar);
        // TODO do we need xAxis labels at all
//            if ((int)value - 1 <  graficiTip.getData().size())
//                s = graficiTip.getData().get((int)value - 1).getTypeChar();
        return s;
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
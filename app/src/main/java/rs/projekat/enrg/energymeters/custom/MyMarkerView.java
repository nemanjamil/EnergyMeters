package rs.projekat.enrg.energymeters.custom;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import rs.projekat.enrg.energymeters.R;
import rs.projekat.enrg.energymeters.common.Constants;
import rs.projekat.enrg.energymeters.model.GraphicsIp;
import rs.projekat.enrg.energymeters.model.PodaciGrafik;

/**
 * ******************************
 * Created by milan on 8/6/2016.
 * ******************************
 */
public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    private SimpleDateFormat sdf;
    private ArrayList<String> labels = new ArrayList<>();

    public MyMarkerView(Context context, int layoutResource, GraphicsIp graficiTip) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);
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

                String l3 = "Con: " + String.valueOf(pg.getConsumption());
                // Create final label
                String label = "Od: " + l1 + "\n" + "Do: " + l2 + "\n" + l3;
                // Add label to Axis
                labels.add(label);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        int index = (int)e.getX();
        tvContent.setText(labels.get(index));
    }

    @Override
    public int getXOffset(float xpos) {
        // this will center the marker-view horizontally
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        // this will cause the marker-view to be above the selected value
        return -getHeight();
    }
}
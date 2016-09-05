package rs.projekat.enrg.energymeters;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import rs.projekat.enrg.energymeters.common.Colors;
import rs.projekat.enrg.energymeters.common.Constants;
import rs.projekat.enrg.energymeters.common.EndPoints;
import rs.projekat.enrg.energymeters.custom.MyAxisValueFormatter;
import rs.projekat.enrg.energymeters.custom.MyMarkerView;
import rs.projekat.enrg.energymeters.custom.MyValueFormatter;
import rs.projekat.enrg.energymeters.dialogs.ProgressDialogCustom;
import rs.projekat.enrg.energymeters.model.GraphicsIp;
import rs.projekat.enrg.energymeters.model.PodaciGrafik;
import rs.projekat.enrg.energymeters.network.PullWebContent;
import rs.projekat.enrg.energymeters.network.VolleySingleton;
import rs.projekat.enrg.energymeters.network.WebRequestCallbackInterface;

public class Grafici extends AppCompatActivity {

    protected Typeface mTfRegular;
    protected Typeface mTfLight;
    private BarChart mChart;
    private ProgressDialogCustom _progressDialogCustom;
    private VolleySingleton _VolleySingleton;

    private GraphicsIp _graphicsIp; // definisali smo globalnu promenljivu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafici);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.action_bar_title_short);

        // Get text view reference
        final TextView tv1 = (TextView) findViewById(R.id.textView1);
        // Get BarChart reference
        mChart = (BarChart) findViewById(R.id.chart1);

        // Pull the data from intent
        Intent intent = getIntent();
        Integer pozicija = intent.getIntExtra("pozicijaMoja", -1);
        String idSenzora = intent.getStringExtra("idSenzora");
        // TODO this one not used, delete it
        String ipSenzora = intent.getStringExtra("ipSenzora");

        tv1.setTextSize(36);

        //tv1.setText(ipSenzora);
        //tv1.setText(pozicija.toString() + " idSenzora: " + idSenzora + " ipSenzora: " + ipSenzora);

        // Show progress dialog as we are going to pull the data from the server
        _progressDialogCustom = new ProgressDialogCustom(this); // da pre ucitavanja pokrene dialog
        _progressDialogCustom.showDialog("Učitavam Podatke");

        // Get volley instance. Volley manages http requests
        _VolleySingleton = VolleySingleton.getsInstance(this);

        // Create URL
        String urlSaParametrima = String.format(EndPoints.URLPODACIPOSENZORU, idSenzora, ipSenzora);

        // Create Object to pull the data from the server, and fill data model with the data
        final PullWebContent<GraphicsIp> webcontent = new PullWebContent<GraphicsIp>(GraphicsIp.class, urlSaParametrima, _VolleySingleton);

        webcontent.setCallbackListener(new WebRequestCallbackInterface<GraphicsIp>() {

            @Override
            public void webRequestSuccess(boolean success, GraphicsIp graficiTip) {
                // Web request finished
                // Hide the progress dialog
                _progressDialogCustom.hideDialog();

                if (success) {
                    // The request was successful
                    // show the newest data
                    int data_size = graficiTip.getData().size();

                    // Show the IP address of the newest measurement in the barchart
                    tv1.setText(graficiTip.getData().get(data_size-1).getIpAddress());

                    // Set BarChart data
                    setData(graficiTip.getData(), data_size - Constants.WINDOW_SIZE, data_size);
                    setupLabels(graficiTip);
                    setupMarker(graficiTip);

                } else {
                    // ako nema nista
                    Toast.makeText(Grafici.this, "Nema podataka u JSON", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void webRequestError(String error) {

            }
        });


        webcontent.pullList();

        //
        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");

        mChart = (BarChart) findViewById(R.id.chart1);
        // no description text
        mChart.setDescription("");
        mChart.setNoDataText(getString(R.string.chart_no_data_text));
        mChart.setNoDataTextDescription(getString(R.string.chart_no_data_text_desc));

    }

    // Sets the mChart data
    private void setData(List<PodaciGrafik> data, Integer from, Integer to) {

        // Prepare data
        ArrayList<BarEntry> valueSet = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            // Create Bar entry
            BarEntry be = new BarEntry(i, data.get(i).getConsumption().floatValue());
            // Add Bar entry to data set
            valueSet.add(be);

            if (data.get(i).getTypeChar().equals(Constants.NORMAL)) {
                colors.add(Colors.COLOR_NORMAL);
            } else
                colors.add(Colors.COLOR_RESTORE);
        }

        BarDataSet dataSet = new BarDataSet(valueSet, " ");


        dataSet.setColors(colors);
        BarData barData = new BarData(dataSet);
        // A single bar is wide 80% of max bar width
        barData.setBarWidth(0.80f);
        barData.setValueTextSize(9f);

        barData.setValueFormatter(new MyValueFormatter());

        mChart.setData(barData);

        // limit the number of visible entries
        mChart.setVisibleXRangeMaximum(9);
        // move to the latest entry
        mChart.moveViewToX(data.size());

        mChart.setDescription(getString(R.string.chart_description));
        mChart.setDescriptionTextSize(14f);
        int descr_width = Utils.calcTextWidth(mChart.getPaint(BarChart.PAINT_DESCRIPTION), getString(R.string.chart_description));
        mChart.setDescriptionPosition(mChart.getWidth()/2f + descr_width/2, mChart.getHeight()*0.1f);



        mChart.animateXY(500, 2000);
        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);
        mChart.setDragDecelerationFrictionCoef(0.9f);

        //mChart.setViewPortOffsets(50f,10f,10f,50f);
        mChart.invalidate();
    }

    private void setupLabels(GraphicsIp graficiTip) {
        // Setup chart
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        float max_index = graficiTip.getData().size();
        xAxis.setAxisMinValue(-0.5f);
        xAxis.setAxisMaxValue(max_index - 0.5f);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(270);

        // puts measurement id as label
        xAxis.setValueFormatter(new MyAxisValueFormatter(graficiTip));

        // Determine maximum value for Y axis
        float max_y = 0.0f;
        for (int i = 0; i < graficiTip.getData().size(); i++) {
            if (max_y < graficiTip.getData().get(i).getConsumption().floatValue())
                max_y = graficiTip.getData().get(i).getConsumption().floatValue();
        }

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setAxisMinValue(0.0f);
        leftAxis.setAxisMaxValue(1.4f * max_y);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setAxisMinValue(0.0f);
        rightAxis.setAxisMaxValue(1.4f * max_y);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        l.setXEntrySpace(40f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        List<Integer> legendColors = new ArrayList<>();
        legendColors.add(Colors.COLOR_NORMAL);
        legendColors.add(Colors.COLOR_RESTORE);
        l.setComputedColors(legendColors);

        List<String> legendLabels = new ArrayList<>();
        legendLabels.add(getString(R.string.normal));
        legendLabels.add(getString(R.string.restore));
        l.setComputedLabels(legendLabels);
    }

    private void setupMarker(GraphicsIp graficiTip) {
        // Setup marker
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view, graficiTip);
        // set the marker to the chart
        mChart.setMarkerView(mv);
        // enable touch gestures
        // mChart.setTouchEnabled(true);
        mChart.setDrawMarkerViews(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }

        }
        return true;
    }
}

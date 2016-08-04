package rs.projekat.enrg.energymeters;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.formatter.FormattedStringCache;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import rs.projekat.enrg.energymeters.adapters.SensorListAdapter;
import rs.projekat.enrg.energymeters.common.EndPoints;
import rs.projekat.enrg.energymeters.dialogs.ProgressDialogCustom;
import rs.projekat.enrg.energymeters.model.GraphicsIp;
import rs.projekat.enrg.energymeters.model.ListaSenzora;
import rs.projekat.enrg.energymeters.network.PullWebContent;
import rs.projekat.enrg.energymeters.network.VolleySingleton;
import rs.projekat.enrg.energymeters.network.WebRequestCallbackInterface;

public class Grafici extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private LineChart mChart;
    private SeekBar mSeekBarX;
    private TextView tvX;
    protected Typeface mTfRegular;
    protected Typeface mTfLight;
    private ProgressDialogCustom _progressDialogCustom;
    private VolleySingleton _VolleySingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafici);

        final TextView tv1 = (TextView) findViewById(R.id.textView1);
        Button bt1 = (Button) findViewById(R.id.button1);


        Intent intent = getIntent();
        Integer pozicija = intent.getIntExtra("pozicijaMoja", -1);
        String idSenzora = intent.getStringExtra("idSenzora");
        String ipSenzora = intent.getStringExtra("ipSenzora");

        tv1.setTextSize(40);
        //tv1.setText(pozicija.toString() + " idSenzora: " + idSenzora + " ipSenzora: " + ipSenzora);
        bt1.setOnClickListener(this);  // to nam ide na onClick Call Back Metod

        /*
        * Prikupljanje podataka sa neta*/
        _progressDialogCustom = new ProgressDialogCustom(this); // da pre ucitavanja pokrene dialog
        _progressDialogCustom.showDialog("Ucitavam Podatke");

        _VolleySingleton = VolleySingleton.getsInstance(this);
        String urlSaParametrima = String.format(EndPoints.URLPODACIPOSENZORU,idSenzora,ipSenzora);

        final PullWebContent<GraphicsIp> webcontent = new PullWebContent<GraphicsIp>(GraphicsIp.class, urlSaParametrima, _VolleySingleton);

        webcontent.setCallbackListener(new WebRequestCallbackInterface<GraphicsIp>(){

            @Override
            public void webRequestSuccess(boolean success, GraphicsIp graficiTip) {
                _progressDialogCustom.hideDialog();

               if (success) {
                    // ako ima podataka
                    Toast.makeText(Grafici.this, "Podaci o senzoru " + graficiTip.getTag(), Toast.LENGTH_LONG).show();


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

        /* NEMANJA TESTIRANJE */
       /* String url = "http://direktnoizbaste.rs/parametri.php?action=lab011Out&IdSmetersIdchar=4ed77bc2ec2a240ae53f8fe4fc74551a90255759b7538287640fbac2b3752b60";
        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Result handling
                //System.out.println(response.substring(0, 100));
                tv1.setText(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                System.out.println("Something went wrong!");
                error.printStackTrace();

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);*/
        /*KRAJ NEMANJA TESTIRANJE*/




        // odavde mi ide GRAFIKA
        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");

        tvX = (TextView) findViewById(R.id.tvXMax);
        mSeekBarX = (SeekBar) findViewById(R.id.seekBar1);
        mSeekBarX.setProgress(100);
        tvX.setText("100");

        mSeekBarX.setOnSeekBarChangeListener(this);

        mChart = (LineChart) findViewById(R.id.chart1);

        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setViewPortOffsets(0f, 0f, 0f, 0f);

        // add data
        setData(100, 30);
        mChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        l.setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(60000L); // one minute in millis
        xAxis.setValueFormatter(new AxisValueFormatter() {

            private FormattedStringCache.Generic<Long, Date> mFormattedStringCache = new FormattedStringCache.Generic<>(new SimpleDateFormat("dd MMM HH:mm"));

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Long v = (long) value;
                return mFormattedStringCache.getFormattedValue(new Date(v), v);
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setTypeface(mTfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setAxisMaxValue(170f);
        leftAxis.setYOffset(-9f);
        leftAxis.setTextColor(Color.rgb(255, 192, 56));

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                finish();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }

    private void setData(int count, float range) {

        long now = System.currentTimeMillis();
        long hourMillis = 3600000L;

        ArrayList<Entry> values = new ArrayList<Entry>();

        float from = now - (count / 2) * hourMillis;
        float to = now + (count / 2) * hourMillis;

        for (float x = from; x < to; x += hourMillis) {

            float y = getRandom(range, 50);
            values.add(new Entry(x, y)); // add one entry per hour
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "DataSet 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);

        // create a data object with the datasets
        LineData data = new LineData(set1);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        mChart.setData(data);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText("" + (mSeekBarX.getProgress()));

        setData(mSeekBarX.getProgress(), 50);

        // redraw
        mChart.invalidate();
    }


}

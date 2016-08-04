package rs.projekat.enrg.energymeters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

public class Grafici extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafici);

        TextView tv1 = (TextView) findViewById(R.id.textView1);
        Button bt1 = (Button) findViewById(R.id.button1);

        Intent intent = getIntent();
        Integer pozicija = intent.getIntExtra("pozicijaMoja", -1);
        String idSenzora = intent.getStringExtra("idSenzora");
        String ipSenzora = intent.getStringExtra("ipSenzora");

        tv1.setTextSize(40);
        tv1.setText(pozicija.toString()+" idSenzora: "+idSenzora+" ipSenzora: "+ipSenzora);
        bt1.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                finish();
        }
    }


}

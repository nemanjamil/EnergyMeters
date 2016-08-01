package rs.projekat.enrg.energymeters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GlavnaAktivnost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // ne dirati
        setContentView(R.layout.activity_glavna_aktivnost);

        Button btn1 = (Button) findViewById(R.id.button1);
        final TextView tv1 = (TextView) findViewById(R.id.tv1);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv1.setText("Nesto Upisi");
            }
        });

    }


}

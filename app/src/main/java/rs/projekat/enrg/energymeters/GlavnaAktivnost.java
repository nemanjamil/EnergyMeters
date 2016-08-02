package rs.projekat.enrg.energymeters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import rs.projekat.enrg.energymeters.common.EndPoints;
import rs.projekat.enrg.energymeters.model.ListaSenzora;
import rs.projekat.enrg.energymeters.network.PullWebContent;
import rs.projekat.enrg.energymeters.network.VolleySingleton;
import rs.projekat.enrg.energymeters.network.WebRequestCallbackInterface;

public class GlavnaAktivnost extends AppCompatActivity {

    private VolleySingleton mVolleySingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // ne dirati
        setContentView(R.layout.activity_glavna_aktivnost);

        Button btn1 = (Button) findViewById(R.id.button1);
        final TextView tv1 = (TextView) findViewById(R.id.tv1);


        // prvo pravimo objekata koji ce nesto da radi
        // napravi nam objekat za voley // a volay sluzi da pravi webRequest
        mVolleySingleton = VolleySingleton.getsInstance(this);

        // prazna gajba piva ListaSenzora  // mi smo predefinisali gajbu kako izgleda
        // sta gadjam sa praznom gajbom. Trazim Lav ili Koka kolu .... i to mi je URLLISTASENZORA
        // mVolleySingleton - je prodavacica koja donosi flajke, ali ne stavlja u gajbu
        // u gajbu trpa flajke PullWebContent

        // stavili smo final da kada smo jedno napravili objekata necemo menjati da je webcontent nesto drugo
        // tj. webcontetn mora uved da bude webcontent = new PullWebContent<ListaS .....
        final PullWebContent<ListaSenzora> webcontent = new PullWebContent<ListaSenzora>(ListaSenzora.class, EndPoints.URLLISTASENZORA, mVolleySingleton);

        // sada odredjumeo sa se radi sa punom gajbom pića
        // i sta se radi ako pukne flasa ili nesto ne radi

        webcontent.setCallbackListener(new WebRequestCallbackInterface<ListaSenzora>() {
            // sta ako je uspesno
            @Override
            public void webRequestSuccess(boolean success, ListaSenzora listaSenzora) {
                if (success) {
                    // ako ima podataka
                    Toast.makeText(GlavnaAktivnost.this,"Sve je ok",Toast.LENGTH_LONG).show();

                } else {
                    // ako nema nista
                    Toast.makeText(GlavnaAktivnost.this,"Nema podataka u JSON",Toast.LENGTH_LONG).show();
                }

            }

            // ako je fail
            @Override
            public void webRequestError(String error) {
                Toast.makeText(GlavnaAktivnost.this,"Nesto je prslo",Toast.LENGTH_LONG).show();
            }
        });





        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv1.setText("Nesto Upisi");
                // Sada iniciramo povlacenje
                webcontent.pullList();

            }
        });



    }


}

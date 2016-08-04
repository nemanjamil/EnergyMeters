package rs.projekat.enrg.energymeters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import rs.projekat.enrg.energymeters.adapters.SensorListAdapter;
import rs.projekat.enrg.energymeters.common.EndPoints;
import rs.projekat.enrg.energymeters.dialogs.ProgressDialogCustom;
import rs.projekat.enrg.energymeters.model.ListaSenzora;
import rs.projekat.enrg.energymeters.network.PullWebContent;
import rs.projekat.enrg.energymeters.network.VolleySingleton;
import rs.projekat.enrg.energymeters.network.WebRequestCallbackInterface;

public class GlavnaAktivnost extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private VolleySingleton mVolleySingleton;
    private ProgressDialogCustom progressDialogCustom; // ovde smo deklarisali objekat. Trebace nam jedan objekat tog tipa,,,,
    private ListView lvSenzor;  // koristicemo ListView, tu cemo cuvati referncu u toj promenljivoj
    private SensorListAdapter adapterZaSenzorListu;

    private ListaSenzora listaSenzoraMoja; // Globalne promeljive


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // ne dirati
        setContentView(R.layout.activity_glavna_aktivnost);

        Button btn1 = (Button) findViewById(R.id.button1);
        // final TextView tv1 = (TextView) findViewById(R.id.tv1);
        lvSenzor = (ListView) findViewById(R.id.lv_senzori); // uzimamo referencu za konkretan id iz aktivitija

        lvSenzor.setOnItemClickListener(this); // sve klikove prosledjuje na THIS

        progressDialogCustom = new ProgressDialogCustom(this);  // ovde smo ga napravili. I definisali smo my gazdu THIS


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

                progressDialogCustom.hideDialog();

                if (success) {
                    // ako ima podataka
                   // Toast.makeText(GlavnaAktivnost.this, "Sve je ok " + listaSenzora.getTag(), Toast.LENGTH_LONG).show();

                    adapterZaSenzorListu = new SensorListAdapter(GlavnaAktivnost.this,listaSenzora.getData());
                    lvSenzor.setAdapter(adapterZaSenzorListu);

                    listaSenzoraMoja = listaSenzora; // smesta podatke koje smo dobili u globalnu promeljivu

                } else {
                    // ako nema nista
                    Toast.makeText(GlavnaAktivnost.this, "Nema podataka u JSON", Toast.LENGTH_SHORT).show();
                }

            }

            // ako je fail
            @Override
            public void webRequestError(String error) {

                progressDialogCustom.hideDialog();
                Toast.makeText(GlavnaAktivnost.this, "Nešto je prslo", Toast.LENGTH_LONG).show();
                //tv1.setText(error);
            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // tv1.setText("Nesto Upisi");
                // Sada iniciramo povlacenje
                progressDialogCustom.showDialog("Uvlacenje Podataka");
                webcontent.pullList();
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pozicija, long l) {

        // Toast.makeText(GlavnaAktivnost.this, "Trenutna pozicija "+pozicija, Toast.LENGTH_LONG).show();

        // sada pravimo intent za pozivanje drugog aktivitija i saljemo podatke
        Intent intent = new Intent(this, Grafici.class); // this nas aktiviti zove Grafici.class
        intent.putExtra("pozicijaMoja",pozicija);
        intent.putExtra("idSenzora",listaSenzoraMoja.getData().get(pozicija).getIdSmetersId());
        intent.putExtra("ipSenzora",listaSenzoraMoja.getData().get(pozicija).getIpAddress());
        startActivity(intent);

    }
}

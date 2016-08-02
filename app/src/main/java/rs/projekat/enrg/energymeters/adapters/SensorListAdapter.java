package rs.projekat.enrg.energymeters.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import rs.projekat.enrg.energymeters.R;
import rs.projekat.enrg.energymeters.model.Senzor;

/**
 * Created by 1 on 8/2/2016.
 */
public class SensorListAdapter extends ArrayAdapter<Senzor> {

    private final Context context;
    private final Senzor[] senzori;

    public SensorListAdapter(Context context, Senzor[] senzori) {
        super(context, R.layout.list_item, senzori);
        this.context = context;
        this.senzori = senzori;
    }


}

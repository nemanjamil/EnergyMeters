package rs.projekat.enrg.energymeters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import rs.projekat.enrg.energymeters.R;
import rs.projekat.enrg.energymeters.model.Senzor;

/**
 * Created by 1 on 8/2/2016.
 */
public class SensorListAdapter extends ArrayAdapter<Senzor> {

    private final Context context; // cuvamo referencu na aktivnost
    private final List<Senzor> senzori; //  ovde cuvamo niz senzora

    public SensorListAdapter(Context context, List<Senzor> senzori) {
        super(context, R.layout.list_item, senzori);
        this.context = context;
        this.senzori = senzori;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);// on na osnovu xml-a pravi objekat
        View rowView = inflater.inflate(R.layout.list_item,parent,false); // i ovde pravi jedan objekat

        TextView tv1 = (TextView) rowView.findViewById(R.id.tv_li1);
        TextView tv2 = (TextView) rowView.findViewById(R.id.tv_li2);

        tv1.setText(senzori.get(position).getIdSmetersId());
        tv2.setText(senzori.get(position).getIpAddress());

        return  rowView;
    }
}

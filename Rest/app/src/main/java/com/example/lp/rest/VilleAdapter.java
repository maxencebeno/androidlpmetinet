package com.example.lp.rest;

/**
 * Created by maxencebeno on 24/01/2016.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class VilleAdapter extends ArrayAdapter<Ville> {

    int resource;
    ArrayList<Ville> items;
    Context context;
    ListView lv;

    //Initialize adapter
    public VilleAdapter(Context context, int resource, ArrayList<Ville> items, ListView lv) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
        this.context = context;
        this.lv = lv;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LinearLayout listView;
        //Get the current alert object
        Ville v = getItem(position);

        //Inflate the view
        if (convertView == null) {
            listView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, listView, true);
        } else {
            listView = (LinearLayout) convertView;
        }
        //Get the text boxes from the listitem.xml file
        TextView nomVille = (TextView) listView.findViewById(R.id.ville);
        TextView cpVille = (TextView) listView.findViewById(R.id.codepostal);
        TextView latitudeVille = (TextView) listView.findViewById(R.id.latitude);
        TextView longitudeVille = (TextView) listView.findViewById(R.id.longitude);
        TextView codeInseeVille = (TextView) listView.findViewById(R.id.code_insee);

        //Assign the appropriate data from our alert object above
        nomVille.setText(v.getNom());
        cpVille.setText(v.getCodePostal());
        latitudeVille.setText(v.getLongitude());
        longitudeVille.setText(v.getLatitude());
        codeInseeVille.setText(v.getCodeinsee());

        final ImageView deleteImg = (ImageView) listView.findViewById(R.id.img);

        deleteImg.setImageResource(R.drawable.delete);
        /*deleteImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                HashMap<String, String> postParams = new HashMap<String, String>();

                Ville ville = getItem(position);

                postParams.put("action", "delete");
                postParams.put("codeinsee", ville.getCodeinsee());
                Log.v("result", ville.getCodeinsee());

                MainActivity.performPostCall("http://10.0.2.2:80", postParams);
            }
        });*/

        deleteImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                HashMap<String, String> postParams = new HashMap<String, String>();
                TextView codeInseeVille = (TextView) listView.findViewById(R.id.code_insee);

                postParams.put("action", "delete");
                postParams.put("codeinsee", (String) codeInseeVille.getText());

                URL url = null;
                try {
                    url = new URL("http://10.0.2.2:80");
                    String method = "POST";
                    new MyAsyncTask(VilleAdapter.this, items, method, postParams).execute(url, lv, method, postParams);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        return listView;
    }

}
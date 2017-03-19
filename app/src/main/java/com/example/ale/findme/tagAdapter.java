package com.example.ale.findme;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static com.example.ale.findme.Ob.removeTag;
import static com.example.ale.findme.Ob.writeList;

/**
 * Created by Ale on 12/02/2017.
 */

public class tagAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;



    public tagAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
        //just return 0 if your list items do not have an Id variable.
    }




    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_listview, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.textView);
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        ImageView delete = (ImageView) view.findViewById(R.id.imageView3);


        delete.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                builder.setMessage("Vuoi eliminare questo tag?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                ArrayList<String> tag=removeTag(list,list.get(position));
                                String tagList="";
                                for(String x : tag )
                                    tagList=tagList+" @"+x+" ";
                                Log.d("AAAA",tagList);
                                Global.list.get(Global.object.getId()).setTags(tagList);
                                try {
                                        writeList(Global.list);
                                } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                }
                                ((showPhoto) context).recreate();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                //Creating dialog box
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return view;
    }
}
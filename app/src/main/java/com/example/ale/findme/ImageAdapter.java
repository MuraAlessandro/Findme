package com.example.ale.findme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    Context context;
    ArrayList<Ob> list=new ArrayList<Ob>();
    LayoutInflater inflter;

    public ImageAdapter(Context applicationContext, ArrayList<Ob> list) {
        this.context = applicationContext;
        this.list = list;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    //restituisce il numero di elementi da mostrare
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
           // imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5, 5, 5, 5);
        } else {
            imageView = (ImageView) convertView;
        }
        Bitmap myBitmap = BitmapFactory.decodeFile(list.get(position).getPhotoPath());
        imageView.setImageBitmap(myBitmap);

      /*  view = inflter.inflate(R.layout.activity_first_page, null);//set layout for displaying items
        ImageView icon = (ImageView) view.findViewById(R.id.icon);//get id for image view
        // icon.setImageResource(flags[i]);
        // Bitmap myBitmap = BitmapFactory.decodeFile(list.get(i).getPhotoPath());
        // icon.setImageBitmap(myBitmap);*/
        return imageView;
    }






}
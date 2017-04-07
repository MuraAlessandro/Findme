package com.example.ale.findme;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ale.findme.Ob;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.example.ale.findme.Ob.objectById;
import static com.example.ale.findme.Ob.printList;
import static com.example.ale.findme.Ob.removeObject;
import static com.example.ale.findme.Ob.writeList;

public class firstPage extends AppCompatActivity {

    ArrayList<Ob> list;
    GridView gridview=null;
    ArrayList<Ob> finalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Controllo la versione delle API e richiedo il permesso a runtime


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        File folder = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)));
        if (!folder.exists())
             folder.mkdir();//se la cartella non esiste viene creata

        File inFile=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"/data.txt");
        TextView nomeView = (TextView) findViewById(R.id.text);
            if(inFile.exists()) {
                Scanner in = null;
                try {
                    in = new Scanner(inFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (in.hasNextLine()) {
                        //attenzione se viene salvato uno invio o uno spazio allora il file viene considerato pieno
                }
                else
                    nomeView.setText("Benvenuto in FindMe, questa applicazione ti permetterà di realizzare foto e inserirle all'interno");

                in.close();
            }
            else
                nomeView.setText("Benvenuto in FindMe, questa applicazione ti permetterà di realizzare foto e inserirle all'interno");

      /*  ArrayList<Ob> list= new ArrayList<Ob>();
        Ob g=new Ob(1,"/sdcard/Android/data/findme/nome.jpg","@mario @pippo");
        list.add(g);
        Ob x=new Ob(2,"/sdcard/Android/data/findme/nomke.jpg","@o @po");
        list.add(x);
        try {
            writeList(list);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        list= new ArrayList<Ob>();

         try {
            list=printList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Global.list=list;


        /*for(Ob z :list)
        {
           // TextView nome = (TextView) findViewById(R.id.text);
            //nomeView.setText(z.getPhotoPath());

        }*/
        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getApplicationContext(),list));



        finalList = list;


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
               /* Toast.makeText(getBaseContext(),
                        finalList.get(position).getPhotoPath(),
                        Toast.LENGTH_SHORT).show();*/
                Intent openPage= new Intent(firstPage.this,showPhoto.class);//definisco l'intenzione
               // openPage.putExtra("objectPath", finalList.get(position).getPhotoPath());
               // openPage.putExtra("objectTags", finalList.get(position).getTags());
               // openPage.putExtra("objectId", finalList.get(position).getId());

                ArrayList<Ob> l = new ArrayList<Ob>();

                try {
                    l=printList();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Global.list=l;
                Global.object=objectById(l,finalList.get(position).getId());
                startActivity(openPage);

            }

        });
        gridview.setLongClickable(false);//in questo modo se si fa un press non viene contato anche il semplice onclick

        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View v, final int position, long id)
            {


                AlertDialog.Builder builder = new AlertDialog.Builder(firstPage.this);
                //Uncomment the below code to Set the message and title from the strings.xml file
                //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage("Vuoi eliminare questo oggetto ?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               // finish();
                                ArrayList<Ob> l=removeObject(finalList,position);

                                try {
                                    writeList(l);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                Global.list=l;
                               /*Intent i = getBaseContext().getPackageManager()
                                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);*/
                               // Intent i=new Intent(firstPage.this,firstPage.class);
                               // i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                firstPage.super.recreate();
                                //gridview.setAdapter(new ImageAdapter(getApplicationContext(),Global.list));


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });

                //Creating dialog box
                AlertDialog alert = builder.create();

                alert.setTitle("Elimina");
                alert.show();

                return true;

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent foto= new Intent(firstPage.this,newPhoto.class);
                startActivity(foto);

            }
        });

    }

    @Override
    public void onBackPressed(){

        AlertDialog.Builder builder = new AlertDialog.Builder(firstPage.this);
        builder.setMessage("Vuoi uscire dall'applicazione?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                         finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            Toast toast= Toast.makeText(getApplicationContext(),"setting",Toast.LENGTH_LONG);
            toast.show();
            return true;
        }
        else
        if (id==R.id.action1)
        {
            Toast toast= Toast.makeText(getApplicationContext(),"List1",Toast.LENGTH_LONG);
            toast.show();
            return true;
        }
        else if (id==R.id.action2)
        {
            //Intent thirdP= new Intent(Prova1.this,pageThree.class);//definisco l'intenzione
            //thirdP.putExtra("valore","List2");// questo lo metto per evitare che l'app crashi xk come viene aperta la 3 pagina mostra un dato recuperato
            //startActivity(thirdP);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
       // ArrayList<Ob> list= new ArrayList<Ob>();

        try {
            list=printList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        gridview.setAdapter(new ImageAdapter(getApplicationContext(),list));
        finalList = list;

    }


}

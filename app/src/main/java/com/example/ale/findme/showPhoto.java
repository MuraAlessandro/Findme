package com.example.ale.findme;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static com.example.ale.findme.Ob.addTagMethod;
import static com.example.ale.findme.Ob.objectById;
import static com.example.ale.findme.Ob.printList;
import static com.example.ale.findme.Ob.printTag;

class Global {public static Ob object;}


public class showPhoto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);

        ArrayList<Ob> l = new ArrayList<Ob>();
        ArrayList<String> tags = new ArrayList<>();
        //Ob object = new Ob();
        //object.setId(getIntent().getExtras().getInt("objectId"));
       // object.setPhotoPath(getIntent().getExtras().getString("objectPath"));
       // object.setTags(getIntent().getExtras().getString("objectTags"));
       // Global.object=object;
        try {
            l=printList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Global.object=objectById(l,getIntent().getExtras().getInt("objectId"));

        Bitmap myBitmap = BitmapFactory.decodeFile(Global.object.getPhotoPath());
        final ImageView myImage = (ImageView) findViewById(R.id.image);
        myImage.setImageBitmap(myBitmap);
        final int x=myImage.getLayoutParams().width;
        final int y=myImage.getLayoutParams().height;

        tags = printTag(Global.object);
        

        ListView myList = (ListView) findViewById(R.id.elenco);
        tagAdapter adapter = new tagAdapter(tags,this);
        myList.setAdapter(adapter);
        final boolean[] flagFullscreen = {false};

        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //  myImage.setScaleType(ImageView.ScaleType.FIT_XY);
                if(flagFullscreen[0] ==false) {
                    myImage.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                    flagFullscreen[0] =true;
                }
                else
                {
                    myImage.setLayoutParams(new RelativeLayout.LayoutParams(x,y));
                    flagFullscreen[0] =false;
                }
            }
        });



    }

    @Override
    public void onBackPressed(){
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_tag) {
            LayoutInflater layoutInflater = LayoutInflater.from(showPhoto.this);
            View promptView = layoutInflater.inflate(R.layout.activity_add_tag, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(showPhoto.this);
            alertDialogBuilder.setView(promptView);

            final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
            // setup a dialog window
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ArrayList<Ob> l = new ArrayList<Ob>();
                            String t = editText.getText().toString().replace(" ", "+-+");//se mantengo lo spazio allora l'editText va in crush


                            if (!t.isEmpty() && !t.equals("+-+"))
                            {
                                try {
                                    l=printList();
                                    addTagMethod(l,Global.object.getId(),t.replace("+-+"," "));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                               // editText.setError("Errore");

                               showPhoto.super.recreate();
                            }
                            else if(t.isEmpty())
                            {
                                Toast toast= Toast.makeText(getApplicationContext(),"Inserisci testo",Toast.LENGTH_LONG);
                                toast.show();
                            }
                            else if(t.equals("+-+"))
                            {
                                Toast toast= Toast.makeText(getApplicationContext(),"Tag non valido",Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    })
                    .setNegativeButton("Annulla",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            // create an alert dialog
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();

            return true;
        }else
        if (id == R.id.action_settings) {
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
}

package com.example.ale.findme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.ale.findme.Ob.writeList;

public class newPhoto extends AppCompatActivity {

    int CAM_REQUEST=11;
    boolean photoFlag=false;
    String path;
    ArrayList<Ob> list= new ArrayList<Ob>();


    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list=Global.list;

        ImageView photo=(ImageView) findViewById(R.id.im);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // intent implicito, i parametri
                Intent photocamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //creo il nome della foto utilizzando la data e l'ora in cui viene fatta

                String timeStamp = (new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())) + ".jpg";
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), timeStamp);
                path = file + "";
                photocamera.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file));
                startActivityForResult(photocamera,CAM_REQUEST);



            }
        });

        final EditText tag=(EditText) findViewById(R.id.editText);

        ImageView deletePath =(ImageView) findViewById(R.id.imageView3);
        deletePath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tag.setText("");
            }
        });




        Button b=(Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tg=tag.getText()+"";

                if(!photoFlag)
                    Toast.makeText(getBaseContext(), "Devi scattare una foto",Toast.LENGTH_LONG).show();
                if(tg.compareTo("")==0)
                    tag.setError("Inserisci tag");

                if(photoFlag && tg.compareTo("")!=0){

                    tag.setError(null);
                    Ob newElement=new Ob();
                    newElement.setPhotoPath(path);
                    newElement.setTags("@"+tg);
                    list.add(newElement);

                    try {
                        writeList(list);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Global.list=list;
                    finish();
                }
            }
        });



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
    protected void onActivityResult(int requestCode, int resultCode, Intent databack) {
        // super.onActivityResult(requestCode, resultCode, databack);
        if(requestCode==CAM_REQUEST && resultCode==RESULT_OK)
        {

            photoFlag=true;
            File imgFile = new  File(path);
            if(imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                //intent per recuperare l'immagine
                //img è un componente imageView presente sul layout
                ImageView img = (ImageView) findViewById(R.id.im);
                img.setImageBitmap(myBitmap);
            }


/*            String timeStamp = (new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())) + ".png";
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), timeStamp);
            Log.d("file", file + "");
            path = file + "";
            FileOutputStream outptOs = null;
            try {
                outptOs = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            immagine.compress(Bitmap.CompressFormat.PNG, 100, outptOs);
*/
          /*  Intent pF= new Intent(pageThree.this,pageFourth.class);//definisco l'intenzione
            startActivity(pF);*/
        }
    }

}

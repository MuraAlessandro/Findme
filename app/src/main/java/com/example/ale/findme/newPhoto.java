package com.example.ale.findme;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class newPhoto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boolean fotoFlag=false;
        boolean tagFlag=false;









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


}

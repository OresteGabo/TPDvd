package com.example.e1702856.tp3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.util.*;

public class ModifierActivity extends Activity {

    private ArrayAdapter<String> listAdapter;

    private TextView a2_titre;
    private EditText a2_person;
    private ListView mainListView;
    private int selectedDureeIndex;
    private int selectedDuree;
    private int selectedReceivedDataIndex;

    private Button a2_ok,a2_annuler;

    private void init(){

        mainListView=(ListView)findViewById(R.id.list);
        a2_ok=(Button)findViewById(R.id.a2_ok);
        a2_annuler=(Button)findViewById(R.id.a2_annuler);
        listAdapter=new ArrayAdapter<String>(this,R.layout.listitem);
        listAdapter.add("5");
        listAdapter.add("10");
        listAdapter.add("15");

        selectedDureeIndex=0;
        selectedDuree=0;



        mainListView.setAdapter(listAdapter);
        a2_person=(EditText)findViewById(R.id.a2_personne);
        a2_titre=(TextView)findViewById(R.id.a2_titre);
    }
    private void btnHandler(){
        a2_ok.setOnClickListener(btnListener);
        a2_annuler.setOnClickListener(btnListener);
    }
    private View.OnClickListener btnListener=new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.a2_ok){
                sendExtras();
            }
            else if(view.getId()==R.id.a2_annuler){

            }
            finish();
        }
    };
    private void sendExtras() {
        Intent intent=new Intent(ModifierActivity.this,MainActivity.class);
        intent.putExtra("NEW_DATA_TITRE",a2_titre.getText());
        intent.putExtra("NEW_DATA_PERSON",a2_person.getText());
        intent.putExtra("NEW_DATA_DUREE",selectedDuree);
        intent.putExtra("NEW_DATA_INDEX",selectedReceivedDataIndex);
        Log.d("LOG_D_SENT", "dATA ARE BEING EXCHANGED FROM SON TO MOTHER");

        setResult(RESULT_OK,intent);
        startActivityForResult(intent,0);
    }
    private void listViewHandler(){
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectedDureeIndex=position;
                selectedDuree=Integer.valueOf(listAdapter.getItem(position));
                Log.d("LOG_D_", String.format("Selected index:index: %d duree: %d",selectedDureeIndex,selectedDuree));
            }
        });
    }
    private void receiveExtras(){
        Intent intent=getIntent();
        Bundle b=intent.getExtras();
        if(b!=null){

            String titre=(String)b.get("DATA_TITRE");
            String person=(String)b.get("DATA_PERSON");
            selectedDuree=(Integer)b.get("DATA_DUREE");
            selectedReceivedDataIndex=(Integer)b.get("DATA_INDEX");
            a2_titre.setText(titre);
            a2_person.setText(person);

            for(int x=0;x<listAdapter.getCount();x++){
                if(Integer.valueOf(listAdapter.getItem(x).toString())==selectedDuree){
                    selectedDureeIndex=x;
                }
            }
            Log.d("LOG_D_INTENT", String.format("Titre: %s,Person: %s,duree: %d index: %d",titre,person,selectedDuree,selectedDureeIndex));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier);
        init();
        receiveExtras();
        btnHandler();
        listViewHandler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modifier, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

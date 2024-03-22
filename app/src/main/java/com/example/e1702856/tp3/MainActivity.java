package com.example.e1702856.tp3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<DVDRentals> dvds;
    private TextView a1_titre,a1_personne,a1_duree;
    private Button a1_modifier,a1_supprimer,a1_next,a1_prec;
    private int selectedDVDIndex;

    private void init()
    {
        selectedDVDIndex=-1;
        dvds=new ArrayList<DVDRentals>();

        a1_titre=(TextView)findViewById(R.id.a1_titre);
        a1_personne=(TextView)findViewById(R.id.a1_personne);
        a1_duree=(TextView)findViewById(R.id.a1_duree);
        a1_modifier=(Button)findViewById(R.id.a1_modifier);
        a1_supprimer=(Button)findViewById(R.id.a1_supprimer);
        a1_next=(Button)findViewById(R.id.a1_next);
        a1_prec=(Button)findViewById(R.id.a1_prec);
        loadData();

    }
    private void loadData(){

        dvds.add(new DVDRentals("titre1","personne1",0));
        dvds.add(new DVDRentals("titre2","personne2",0));
        dvds.add(new DVDRentals("titre3", "personne3", 0));
        dvds.add(new DVDRentals("titre4", "personne4", 0));
        dvds.add(new DVDRentals("titre5", "personne5", 0));
        selectedDVDIndex=0;
        MAJ();
    }
    private void openModifier(){
        Intent intent=new Intent(MainActivity.this,ModifierActivity.class);
        intent.putExtra("DATA_TITRE", dvds.get(selectedDVDIndex).getTitre());
        intent.putExtra("DATA_PERSON", dvds.get(selectedDVDIndex).getPerson());
        intent.putExtra("DATA_DUREE", dvds.get(selectedDVDIndex).getDuree());
        intent.putExtra("DATA_INDEX", selectedDVDIndex);

        setResult(RESULT_OK,intent);
        startActivityForResult(intent, 0);

    }
    private void receiveExtras(){
        Intent intent=getIntent();
        Bundle b=intent.getExtras();
        if(b!=null){
            int newDuree=Integer.valueOf( b.get("NEW_DATA_DUREE").toString());
            String newPerson= b.get("NEW_DATA_PERSON").toString();
            selectedDVDIndex= Integer.valueOf(b.get("NEW_DATA_INDEX").toString());



            dvds.get(selectedDVDIndex).setDuree(newDuree);
            dvds.get(selectedDVDIndex).setPerson(newPerson);
            Log.d("LOG_D_RECEIVED", String.format("Received data %d, %s", newDuree, newPerson));
            MAJ();
        }
    }
    private void MAJ(){
        if(dvds.size()!=0){
            a1_titre.setText(dvds.get(selectedDVDIndex).getTitre());
            a1_personne.setText(dvds.get(selectedDVDIndex).getPerson());
            a1_duree.setText(""+dvds.get(selectedDVDIndex).getDuree());
        }else{
            a1_titre.setText("");
            a1_personne.setText("");
            a1_duree.setText("");
        }
    }

    private void supprimer(){
        if(dvds.size()!=0){
            dvds.remove(selectedDVDIndex);
            if(selectedDVDIndex!=0){
                selectedDVDIndex--;
            }
            MAJ();
        }
    }
    private void next(){
        if(dvds.size()!=0){
            if(selectedDVDIndex==dvds.size()-1){
                selectedDVDIndex=0;
            }else{
                selectedDVDIndex++;
            }
            MAJ();
        }
    }
    private void prec(){
        if(dvds.size()!=0){
            if(selectedDVDIndex==0){
                selectedDVDIndex=dvds.size()-1;
            }else{
                selectedDVDIndex--;
            }
            MAJ();
        }
    }
    private View.OnClickListener btnListener=new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.a1_modifier){
               openModifier();
            }
            else if(view.getId() == R.id.a1_supprimer) {
                supprimer();
            }else if(view.getId() == R.id.a1_next){
                next();
            }else if(view.getId() == R.id.a1_prec){
                prec();
            }
        }
    };
    private void handlers(){
        a1_modifier.setOnClickListener(btnListener);
        a1_supprimer.setOnClickListener(btnListener);
        a1_next.setOnClickListener(btnListener);
        a1_prec.setOnClickListener(btnListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        handlers();
        receiveExtras();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
class DVDRentals{
    private String titre;
    private String person;
    private int duree;

    public DVDRentals( String titre, String person,int duree) {
        this.duree = duree;
        this.person = person;
        this.titre = titre;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}
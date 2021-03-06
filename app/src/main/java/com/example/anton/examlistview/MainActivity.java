package com.example.anton.examlistview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.anton.examlistview.adapter.PersonAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    final Context context = this;
    private ListView listView;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
        String resultJson = null;
        MyAssingTask task = new MyAssingTask();

        task.execute();

        final List<Person> personList = new ArrayList<Person>();

        try {
            resultJson = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        try {
            JSONObject dataJsonObj = new JSONObject(resultJson);

            JSONObject company = dataJsonObj.getJSONObject("company");

            JSONArray employees = company.getJSONArray("employees");
            Log.d("my_log", String.valueOf(employees.length()));
            for (int i = 0; i < employees.length(); i++) {
                JSONObject employee = employees.getJSONObject(i);
                String name = employee.getString("name");
                int phone_number = employee.getInt("phone_number");
                JSONArray skills = employee.getJSONArray("skills");

                List<String> skillList = new ArrayList<String>();

                for (int j = 0; j < skills.length(); j++){
                    String skill = skills.getString(j);
                    skillList.add(skill);
                }

                personList.add(new Person(name, phone_number, skillList));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Collections.sort(personList);

        PersonAdapter adapter = new PersonAdapter(this, personList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.promt, null);

                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);

                mDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView.findViewById(R.id.input_text);

                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if (userInput.getText().length()>1){
                                            Person person = personList.get(position);
                                            person.setName(userInput.getText().toString());
                                            personList.set(position,person);
                                        } else {
                                            Toast toast = Toast.makeText(getApplicationContext(),
                                                    "Имя должно состоять более чем из одного символа", Toast.LENGTH_LONG);
                                            toast.show();
                                        }
                                    }
                                })
                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = mDialogBuilder.create();

                alertDialog.show();

            }
        });

    }

}

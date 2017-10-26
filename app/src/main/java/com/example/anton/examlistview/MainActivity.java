package com.example.anton.examlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
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

        List<Person> personList = new ArrayList<Person>();

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

    }

}

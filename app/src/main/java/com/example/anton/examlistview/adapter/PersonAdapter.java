package com.example.anton.examlistview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.anton.examlistview.Person;
import com.example.anton.examlistview.R;

import java.util.List;

public class PersonAdapter extends BaseAdapter {

    private List<Person> list;
    private LayoutInflater layoutInflater;


    public PersonAdapter(Context context, List<Person> list) {
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = layoutInflater.inflate(R.layout.item_layuot,parent,false);
        }

        Person person = getPerson(position);

        TextView textView = (TextView) view.findViewById(R.id.textView);
        TextView textView2 = (TextView) view.findViewById(R.id.textView2);
        TextView textView3 = (TextView) view.findViewById(R.id.textView3);

        textView.setText(person.getName());
        textView2.setText("тел.: "+String.valueOf(person.getPhone_number()));
        textView3.setText("Навыки.: "+person.getSkills());

        return view;
    }

    private Person getPerson(int position){
        return (Person) getItem(position);
    }
}

package com.kiranaofficial.kirana;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
 
public class MainActivity extends Activity {
 
 private EditText myEditText1, myEditText2;
 MyCustomAdapter dataAdapter = null;
 
 @Override
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
 
  //get reference to the EditText
  myEditText1 = (EditText) findViewById(R.id.editText1);
  //set the onFocusChange listener
  myEditText1.setOnFocusChangeListener(myEditTextFocus);
 
  //get reference to the EditText
  myEditText2 = (EditText) findViewById(R.id.editText2);
  //set the onFocusChange listener
  myEditText2.setOnFocusChangeListener(myEditTextFocus);
 
  //Generate list View from ArrayList
  displayListView();
 
 }
 
 private void displayListView() {
 
  //Array list of countries
  ArrayList<String> countryList = new ArrayList<String>();
  countryList.add("Aruba");
  countryList.add("Anguilla");
  countryList.add("Netherlands Antilles");
  countryList.add("Antigua and Barbuda");
  countryList.add("Bahamas");
  countryList.add("Belize");
  countryList.add("Bermuda");
  countryList.add("Barbados");
  countryList.add("Canada");
  countryList.add("Costa Rica");
  countryList.add("Cuba");
  countryList.add("Cayman Islands");
  countryList.add("aaa");
  countryList.add("www");
  countryList.add("wwde");
  countryList.add("rrr");
  countryList.add("feferf");
  countryList.add("fefef");
  countryList.add("2323");
  countryList.add("grbrb");
  countryList.add("vvr");
  countryList.add("brbb");
  countryList.add("3424");
  countryList.add("gtgtg");
 
  //create an ArrayAdaptar from the String Array
  dataAdapter = new MyCustomAdapter(this, R.layout.my_row, countryList);
  ListView listView = (ListView) findViewById(R.id.listView1);
  // Assign adapter to ListView
  listView.setAdapter(dataAdapter);
 
 }
 
 private class MyCustomAdapter extends ArrayAdapter<String> {
 
  private ArrayList<String> countryList;
 
  public MyCustomAdapter(Context context, int textViewResourceId, 
    ArrayList<String> countryList) {
   super(context, textViewResourceId, countryList);
   this.countryList = new ArrayList<String>();
   this.countryList.addAll(countryList);
  }
 
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
 
   if (convertView == null) {
 
    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    convertView = vi.inflate(R.layout.my_row, null);
 
   } 
 
   //get reference to the Editext inside the view that was inflated to create the row
   EditText myInputText = (EditText) convertView.findViewById(R.id.myInputText);
   //attach the onFocusChange listener to the EditText
   myInputText.setOnFocusChangeListener(new OnFocusChangeListener() {
    public void onFocusChange(View v, boolean gainFocus) {
     //onFocus
     if (gainFocus) {
      //set the row background to a different color
      ((View) v.getParent()).setBackgroundColor(Color.rgb(255, 248, 220));
     } 
     //onBlur
     else {
      //set the row background white
      ((View) v.getParent()).setBackgroundColor(Color.rgb(255, 255, 255));
     }
    }
   });
 
   TextView country = (TextView) convertView.findViewById(R.id.country);
   country.setText(countryList.get(position));
   return convertView;
 
  }
 
 }
 
 private OnFocusChangeListener myEditTextFocus =  new OnFocusChangeListener() {
  public void onFocusChange(View view, boolean gainFocus) {
   //onFocus
   if (gainFocus) {
    //set the text
    ((EditText) view).setText("In focus now");
   } 
   //onBlur
   else {
    //clear the text
    ((EditText) view).setText("");
   }
  };
 };
 
}

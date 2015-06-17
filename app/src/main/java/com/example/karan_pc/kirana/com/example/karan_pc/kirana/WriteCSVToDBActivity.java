package com.example.karan_pc.kirana.com.example.karan_pc.kirana;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.karan_pc.kirana.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karan-PC on 12-06-2015.
 */
public class WriteCSVToDBActivity extends Activity {

    Button btnCSVToDB;
    List<CSVTestObject> csvTestObjects = null;
    CSVTestObject testObject = null;
    UserDB userDB = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_csv_to_db);

        btnCSVToDB = (Button)findViewById(R.id.btnCSVToDB);
        btnCSVToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String csvFile = "/sdcard/CSVs/Beauty.csv";
                BufferedReader br = null;
                String line = "";
                String cvsSplitBy = ",";
                csvTestObjects = new ArrayList<CSVTestObject>();

                try {

                    br = new BufferedReader(new FileReader(csvFile));
                    while ((line = br.readLine()) != null) {
                        String[] oneLine = line.split(cvsSplitBy);
                        // use comma as separator
                        testObject = new CSVTestObject();
                        testObject.setId(Integer.parseInt(oneLine[0]));
                        testObject.setName(oneLine[1]);
                        testObject.setAge(Integer.parseInt(oneLine[2]));
                        testObject.setCost(Double.parseDouble(oneLine[3]));
                        csvTestObjects.add(testObject);
                    }
                    userDB = new UserDB(getApplicationContext());
                    userDB.openDatabase();
                    userDB.createEntry(csvTestObjects);
                    userDB.closeDatabase();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}

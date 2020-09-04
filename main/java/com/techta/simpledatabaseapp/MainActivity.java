package com.techta.simpledatabaseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ItemAdapter adapter;
    private SQLiteDatabase database;
    private EditText nameEditText;
    private TextView txtAmount;
    private int amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ItemDBHelper dbHelper = new ItemDBHelper(this);
        database = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter(this, getAllItems());
        recyclerView.setAdapter(adapter);

        //init
        nameEditText = findViewById(R.id.nameEditText);
        txtAmount = findViewById(R.id.txtAmount);
        final Button increaseBtn = findViewById(R.id.increaseBtn);
        Button decreaseBtn = findViewById(R.id.decreaseBtn);
        Button addBtn = findViewById(R.id.addBtn);

        //onClick listeners
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increase();
            }
        });

        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrease();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemToList();
            }
        });
    }

    private void addItemToList() {

        // if the edit text is empty or if the amount is zero, the item will not be added
       if (nameEditText.getText().toString().trim().length() == 0 || amount == 0) {
           return;
       }

       //getting the name of the item from the edit text
       String name = nameEditText.getText().toString();
       //creating a new ContentValues object in which I will put our data that will be stored in the database
       ContentValues cv = new ContentValues();

       //putting the items into the ContentValues
       cv.put(ItemContract.ItemEntry.COLUMN_NAME, name);
       cv.put(ItemContract.ItemEntry.COLUMN_AMOUNT, amount);

       //inserting the ContentValues data into the database
       database.insert(ItemContract.ItemEntry.TABLE_NAME, null, cv);
       adapter.swapCursor(getAllItems());

       //after insertion the edittext's text gets cleared
       nameEditText.getText().clear();
    }

    private Cursor getAllItems() {
        return database.query(
                ItemContract.ItemEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ItemContract.ItemEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }

    //if the amount is bigger than zero, the amount can be decreased
    private void decrease() {
        if (amount > 0) {
            amount--;
            txtAmount.setText(String.valueOf(amount));
        }

    }

    //just increases the amount
    private void increase() {
        amount++;
        txtAmount.setText(String.valueOf(amount));
    }
}
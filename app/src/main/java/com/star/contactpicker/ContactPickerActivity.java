package com.star.contactpicker;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class ContactPickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_picker);

        final Cursor cursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        String[] from = new String[] {ContactsContract.Contacts.DISPLAY_NAME_PRIMARY};

        int[] to = new int[] {R.id.itemTextView};

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                this, R.layout.listitemlayout, cursor, from, to
        );

        ListView listView = (ListView) findViewById(R.id.contactListView);

        listView.setAdapter(simpleCursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);

                int rowId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

                Uri outURI = ContentUris.withAppendedId(
                        ContactsContract.Contacts.CONTENT_URI, rowId);

                Intent outData = new Intent();

                outData.setData(outURI);

                setResult(Activity.RESULT_OK, outData);

                finish();
            }
        });

    }
}

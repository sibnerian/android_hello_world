package codepath.demos.helloworlddemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HelloWorldActivity extends Activity {
    private static final String TODO_FILE = "todo.txt";
    private static final int EDIT_REQUEST_CODE = 1234;
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello_world);

        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListeners();
	}

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_hello_world, menu);
		return true;
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            String editedValue = data.getStringExtra("editedValue");
            int position = data.getIntExtra("position", 0);
            items.set(position, editedValue);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    private void setupListViewListeners() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int pos, long id) {
                launchEditItemActivity(pos);
            }
        });
    }

    private void launchEditItemActivity(int pos) {
        String initValue = items.get(pos);
        Intent i = new Intent(HelloWorldActivity.this, EditItemActivity.class);
        i.putExtra("position", pos);
        i.putExtra("initialValue", initValue);
        startActivityForResult(i, EDIT_REQUEST_CODE);
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, TODO_FILE);
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
            e.printStackTrace();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, TODO_FILE);
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

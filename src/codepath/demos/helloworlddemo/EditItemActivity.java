package codepath.demos.helloworlddemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        EditText textarea = (EditText) findViewById(R.id.etEditItem);
        textarea.setText(getIntent().getStringExtra("initialValue"));
        textarea.setSelection(textarea.getText().length());
    }

    public void onSubmit(View v) {
        EditText textarea = (EditText) findViewById(R.id.etEditItem);
        String editedValue = textarea.getText().toString();
        Intent data = new Intent();
        data.putExtra("editedValue", editedValue);
        data.putExtra("position", getIntent().getIntExtra("position", 0));
        setResult(RESULT_OK, data);
        this.finish();
    }
}

package pl.agh.edu.hitchhiker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {
    public final static int SAVE_FORM_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, RegisterLocationActivity.class);
        startActivityForResult(intent, SAVE_FORM_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RegisterLocationActivity.FORM_SAVED_CODE:

                break;
            case RegisterLocationActivity.FORM_REJECTED_CODE:
                finish();
                break;
        }
    }
}

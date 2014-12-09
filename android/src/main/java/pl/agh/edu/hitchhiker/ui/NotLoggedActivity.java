package pl.agh.edu.hitchhiker.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import pl.agh.edu.hitchhiker.R;

public class NotLoggedActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_logged);
        getFragmentManager().beginTransaction().add(R.id.container, new RegisterFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        setResult(MainActivity.RESULT_CODE_QUIT);
        super.onBackPressed();
    }

    public void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).addToBackStack(null).commit();
    }
}

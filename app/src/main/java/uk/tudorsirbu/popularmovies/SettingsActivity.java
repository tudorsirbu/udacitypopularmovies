package uk.tudorsirbu.popularmovies;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    public static String TOP_RATED_MOVIES_ENDPOINT = "/movie/top_rated";
    public static String POPULAR_MOVIES_ENDPOINT = "/movie/popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final String[] options = getResources().getStringArray(R.array.sort_options);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = prefs.edit();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, options);
        final Spinner sortBySpinner = (Spinner) findViewById(R.id.sort_by_spinner);
        sortBySpinner.setAdapter(adapter);

        String alreadySelected = prefs.getString("api_endpoint", "/movie/popular");
        if(alreadySelected.equals(TOP_RATED_MOVIES_ENDPOINT)){
            sortBySpinner.setSelection(1);
        } else if(alreadySelected.equals(POPULAR_MOVIES_ENDPOINT)){
            sortBySpinner.setSelection(0);
        }

        sortBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = sortBySpinner.getSelectedItem().toString();
                if(selected.equals(options[0])){
                    editor.putString("api_endpoint", POPULAR_MOVIES_ENDPOINT);
                } else if(selected.equals(options[1])){
                    editor.putString("api_endpoint", TOP_RATED_MOVIES_ENDPOINT);
                }
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }
}

package uk.tudorsirbu.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements MoviesFetcherCallback, AdapterView.OnItemClickListener {

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.movies_grid_view);
        gridView.setOnItemClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        MoviesFetcher fetcher = new MoviesFetcher(this, this);
        fetcher.execute();
    }

    @Override
    public void onSuccess(JSONArray movies) {
        updateGridView(movies);
    }

    @Override
    public void onFail() {
        Toast.makeText(this, getString(R.string.failed_update), Toast.LENGTH_LONG).show();
    }

    private void updateGridView(final JSONArray movies){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MoviesAdapter adapter = new MoviesAdapter(movies, MainActivity.this);
                gridView.setAdapter(adapter);
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        JSONObject movieDetails = (JSONObject) view.getTag();
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        try {
            intent.putExtra("movie_poster", movieDetails.getString("poster_path"));
            intent.putExtra("movie_original_title", movieDetails.getString("original_title"));
            intent.putExtra("overview", movieDetails.getString("overview"));
            intent.putExtra("vote_average", movieDetails.getString("vote_average"));
            intent.putExtra("release_date", movieDetails.getString("release_date"));
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

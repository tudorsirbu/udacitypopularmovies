package uk.tudorsirbu.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {
    private String moviePosterUrl = "http://image.tmdb.org/t/p/w185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        setTitle(intent.getStringExtra("movie_original_title"));

        ImageView moviePoster = (ImageView) findViewById(R.id.movie_poster);

        String imageId = intent.getStringExtra("movie_poster");
        Picasso.with(this).load(moviePosterUrl + imageId).into(moviePoster);

        TextView overview = (TextView) findViewById(R.id.overview);
        overview.setText(intent.getStringExtra("overview"));

        TextView releaseDate = (TextView) findViewById(R.id.released_on);
        releaseDate.setText(intent.getStringExtra("release_date"));

        TextView voteAverage = (TextView) findViewById(R.id.vote_average);
        voteAverage.setText(intent.getStringExtra("vote_average"));
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

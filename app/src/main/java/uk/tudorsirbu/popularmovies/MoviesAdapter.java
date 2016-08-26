package uk.tudorsirbu.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tudorsirbu on 26/08/2016.
 */

public class MoviesAdapter extends BaseAdapter {
    private JSONArray movies;
    private Context mContext;

    public MoviesAdapter(JSONArray movies, Context context) {
        this.movies = movies;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return movies.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return movies.get(i);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridViewItem = inflater.inflate(R.layout.list_item_movie_poster, null);

        String moviePosterUrl = "http://image.tmdb.org/t/p/w185";
        try {
            JSONObject currentMovie = movies.getJSONObject(i);
            moviePosterUrl += currentMovie.getString("poster_path");
            gridViewItem.setTag(currentMovie);
        } catch (JSONException e) {
            e.printStackTrace();
            return  gridViewItem;
        }

        ImageView posterContainer = (ImageView) gridViewItem.findViewById(R.id.movie_poster);
        Picasso.with(mContext).load(moviePosterUrl).into(posterContainer);

        return gridViewItem;
    }
}

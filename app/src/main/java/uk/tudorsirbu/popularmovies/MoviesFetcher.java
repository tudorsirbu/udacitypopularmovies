package uk.tudorsirbu.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import uk.tudorsirbu.popularmovies.MoviesFetcherCallback;

/**
 * Created by tudorsirbu on 26/08/2016.
 */

public class MoviesFetcher extends AsyncTask {
    private static String POSTER_SIZE = "w185";
    private static String API_KEY = "YOUR KEY GOES HERE";

    public static String BASE_URL = "https://api.themoviedb.org/3";

    private String selectedEndPoint;
    private MoviesFetcherCallback callback;

    public MoviesFetcher(MoviesFetcherCallback callback, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.selectedEndPoint = prefs.getString("api_endpoint", "/movie/popular");
        this.callback = callback;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String response = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            URL url = new URL(BASE_URL+selectedEndPoint+ "?api_key=" + API_KEY);

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            response = buffer.toString();
            JSONObject jsonResponse = new JSONObject(response);

            JSONArray movies = jsonResponse.getJSONArray("results");

            callback.onSuccess(movies);
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("App", "Error closing stream", e);
                }
            }
        }

        return null;
    }

}

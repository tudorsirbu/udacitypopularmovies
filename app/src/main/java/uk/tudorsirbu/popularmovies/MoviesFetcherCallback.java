package uk.tudorsirbu.popularmovies;

import org.json.JSONArray;

/**
 * Created by tudorsirbu on 26/08/2016.
 */

public interface MoviesFetcherCallback {

    public void onSuccess(JSONArray movies);
    public void onFail();
}

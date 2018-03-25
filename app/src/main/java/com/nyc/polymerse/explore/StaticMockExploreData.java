package com.nyc.polymerse.explore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shant on 3/24/2018.
 */

public class StaticMockExploreData {

    private static List<ExploreItemModel> exploreItemModelList = new ArrayList<>();

    private final static String json = "{\n" +
            "\t\"suggestions\": [\n" +
            "\t\t{\n" +
            "\t\t\t\"song\": \"DuHast by RammStein\",\n" +
            "\t\t\t\"song_img\": \"http://assets.teamrock.com/image/b46b7ccb-5f68-4cc7-8770-b22c5aa75288?w=800\",\n" +
            "\t\t\t\"song_url\": \"https://www.youtube.com/watch?v=W3q8Od5qJio\",\n" +
            "\t\t\t\"show_movie\": \"Dark\",\n" +
            "\t\t\t\"show_movie_imdb\": \"http://www.imdb.com/title/tt5753856/?ref_=fn_al_tt_1\",\n" +
            "\t\t\t\"show_movie_img\": \"http://www.indiewire.com/wp-content/uploads/2017/09/dark-netflix-jonas-cave.jpg?w=780\",\n" +
            "\t\t\t\"place\": \"Hofbräu Bierhaus NYC\",\n" +
            "\t\t\t\"place_img\": \"https://static1.squarespace.com/static/561d2e21e4b09f4393705cae/57324a2f37013bd42764bef6/57e9709046c3c41f43ad1866/1474916905957/IMG_1236_Bierhaus.jpg?format=1500w\",\n" +
            "\t\t\t\"place_url\": \"http://www.bierhausnyc.com/\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"song\": \"DuHast by RammStein\",\n" +
            "\t\t\t\"song_img\": \"http://assets.teamrock.com/image/b46b7ccb-5f68-4cc7-8770-b22c5aa75288?w=800\",\n" +
            "\t\t\t\"song_url\": \"https://www.youtube.com/watch?v=W3q8Od5qJio\",\n" +
            "\t\t\t\"show_movie\": \"Dark\",\n" +
            "\t\t\t\"show_movie_imdb\": \"http://www.imdb.com/title/tt5753856/?ref_=fn_al_tt_1\",\n" +
            "\t\t\t\"show_movie_img\": \"http://www.indiewire.com/wp-content/uploads/2017/09/dark-netflix-jonas-cave.jpg?w=780\",\n" +
            "\t\t\t\"place\": \"Hofbräu Bierhaus NYC\",\n" +
            "\t\t\t\"place_img\": \"https://static1.squarespace.com/static/561d2e21e4b09f4393705cae/57324a2f37013bd42764bef6/57e9709046c3c41f43ad1866/1474916905957/IMG_1236_Bierhaus.jpg?format=1500w\",\n" +
            "\t\t\t\"place_url\": \"http://www.bierhausnyc.com/\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"song\": \"DuHast by RammStein\",\n" +
            "\t\t\t\"song_img\": \"http://assets.teamrock.com/image/b46b7ccb-5f68-4cc7-8770-b22c5aa75288?w=800\",\n" +
            "\t\t\t\"song_url\": \"https://www.youtube.com/watch?v=W3q8Od5qJio\",\n" +
            "\t\t\t\"show_movie\": \"Dark\",\n" +
            "\t\t\t\"show_movie_imdb\": \"http://www.imdb.com/title/tt5753856/?ref_=fn_al_tt_1\",\n" +
            "\t\t\t\"show_movie_img\": \"http://www.indiewire.com/wp-content/uploads/2017/09/dark-netflix-jonas-cave.jpg?w=780\",\n" +
            "\t\t\t\"place\": \"Hofbräu Bierhaus NYC\",\n" +
            "\t\t\t\"place_img\": \"https://static1.squarespace.com/static/561d2e21e4b09f4393705cae/57324a2f37013bd42764bef6/57e9709046c3c41f43ad1866/1474916905957/IMG_1236_Bierhaus.jpg?format=1500w\",\n" +
            "\t\t\t\"place_url\": \"http://www.bierhausnyc.com/\"\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}";

    public static List<ExploreItemModel> populateList() throws JSONException {
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("suggestions");

        for(int i = 0; i < arr.length(); i++){
            JSONObject currObj = arr.getJSONObject(i);
            String song = currObj.getString("song");
            String songImg = currObj.getString("song_img");
            String songUrl = currObj.getString("song_url");
            String place = currObj.getString("place");
            String placeImg = currObj.getString("place_img");
            String placeUrl = currObj.getString("place_url");
            String showMovie = currObj.getString("show_movie");
            String showMovieImdb = currObj.getString("show_movie_imdb");
            String showImg = currObj.getString("show_movie_img");

            ExploreItemModel model = new ExploreItemModel();
            model.setPlace(place);
            model.setPlace_img(placeImg);
            model.setPlace_url(placeUrl);
            model.setShow_movie(showMovie);
            model.setShow_movie_imdb(showMovieImdb);
            model.setShow_movie_img(showImg);
            model.setSong(song);
            model.setSong_img(songImg);
            model.setSong_url(songUrl);
            exploreItemModelList.add(model);

        }

        return exploreItemModelList;
    }


}

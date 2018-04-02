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
            "\t\t\t\"item_name\": \"Dark\",\n" +
            "\t\t\t\"item_url\": \"http://www.imdb.com/title/tt5753856/\",\n" +
            "\t\t\t\"item_img\": \"http://www.brooklynvegan.com/files/2018/01/dark.jpg\",\n" +
            "\t\t\t\"item_type\": \"film\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"item_name\": \"Du Hast by Rammstein\",\n" +
            "\t\t\t\"item_url\": \"https://www.youtube.com/watch?v=W3q8Od5qJio\",\n" +
            "\t\t\t\"item_img\": \"https://img.discogs.com/nZ2KNNYQVMLAPYi0e4NHWxMkarE=/fit-in/600x610/filters:strip_icc():format(jpeg):mode_rgb():quality(90)/discogs-images/R-6442309-1486649803-4941.jpeg.jpg\",\n" +
            "\t\t\t\"item_type\": \"music\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"item_name\": \"Hofbraus Bierhaus NYC\",\n" +
            "\t\t\t\"item_url\": \"http://www.bierhausnyc.com/\",\n" +
            "\t\t\t\"item_img\": \"https://static1.squarespace.com/static/561d2e21e4b09f4393705cae/t/568e97aa0ab3778fcdf8ec03/1452185519392/IMG_1270.JPG?format=1500w\",\n" +
            "\t\t\t\"item_type\": \"food\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"item_name\": \"Neue Galerie New York\",\n" +
            "\t\t\t\"item_url\": \"http://www.neuegalerie.org/\",\n" +
            "\t\t\t\"item_img\": \"https://d1smv7h0armdzg.cloudfront.net/wp-content/uploads/2012/03/ps-neue1.jpg\",\n" +
            "\t\t\t\"item_type\": \"site\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"item_name\": \"99 Luftballoons by Nena\",\n" +
            "\t\t\t\"item_url\": \"https://www.youtube.com/watch?v=La4Dcd1aUcE\",\n" +
            "\t\t\t\"item_img\": \"https://upload.wikimedia.org/wikipedia/en/thumb/4/40/99_Luftballons_single_cover.jpg/220px-99_Luftballons_single_cover.jpg\",\n" +
            "\t\t\t\"item_type\": \"music\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"item_name\": \"NSU: German History X\",\n" +
            "\t\t\t\"item_url\": \"http://www.imdb.com/title/tt5765988/\",\n" +
            "\t\t\t\"item_img\": \"http://betafilm.com/media/images/proddata/e3/163800.jpg\",\n" +
            "\t\t\t\"item_type\": \"film\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"item_name\": \"Rolf's\",\n" +
            "\t\t\t\"item_url\": \"http://rolfsnyc.com/www.rolfsnyc.com/Welcome.html\",\n" +
            "\t\t\t\"item_img\": \"https://media-cdn.tripadvisor.com/media/photo-s/07/22/88/e3/rolf-s-bar-restaurant.jpg\",\n" +
            "\t\t\t\"item_type\": \"food\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"item_name\": \"Run Lola Run\",\n" +
            "\t\t\t\"item_url\": \"http://www.imdb.com/title/tt0130827/\",\n" +
            "\t\t\t\"item_img\": \"https://s3.drafthouse.com/images/made/run_lola_run_SD10_758_426_81_s_c1.jpg\",\n" +
            "\t\t\t\"item_type\": \"film\"\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}";

    public static List<ExploreItemModel> populateList() throws JSONException {
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("suggestions");

        for(int i = 0; i < arr.length(); i++){
            JSONObject currObj = arr.getJSONObject(i);
            String name = currObj.getString("item_name");
            String url = currObj.getString("item_url");
            String type = currObj.getString("item_type");
            String img = currObj.getString("item_img");


            ExploreItemModel model = new ExploreItemModel();
            model.setItem_name(name);
            model.setItem_img(img);
            model.setItem_url(url);
            model.setItem_type(type);
            exploreItemModelList.add(model);

        }

        return exploreItemModelList;
    }


}

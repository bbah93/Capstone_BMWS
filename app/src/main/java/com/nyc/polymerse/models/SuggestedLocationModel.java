package com.nyc.polymerse.models;

/**
 * Created by Wayne Kellman on 3/31/18.
 */

public class SuggestedLocationModel {

    private String title;
    private String highlightedTitle;
    private String category;
    private String href;
    private String type;
    private String resultType;
    private String vicinity;
    private String highlightedVicinity;
    private float[] position;
    private long distance;

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getHighlightedVicinity() {
        return highlightedVicinity;
    }

    public void setHighlightedVicinity(String highlightedVicinity) {
        this.highlightedVicinity = highlightedVicinity;
    }

    public float[] getPosition() {
        return position;
    }

    public void setPosition(float[] position) {
        this.position = position;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHighlightedTitle() {
        return highlightedTitle;
    }

    public void setHighlightedTitle(String highlightedTitle) {
        this.highlightedTitle = highlightedTitle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    /**
     * title:German restaurant
     highlightedTitle:<b>German</b> restaurant
     category:restaurant
     href:https://places.api.here.com/places/v1/autosuggest/search;context=Zmxvdy1pZD03ZmFlMzM5YS0wMmUyLTU5OGYtODMyNS1lYmNlODQ0NzAxNWZfMTUyMjUwNDk3OTc1NF84Njc2XzE1NzcmcmFuaz0wJmluPTQwLjc0MjkzNyUyQy03My45NDE4ODIlM0JyJTNENzc2OC4w?filters=category%3Agerman%2Cvi%3Aautosuggest&app_id=Afzh08eWQCeNDC5SZo2z&app_code=xvTuMxaPXi1W1mzApsj-Sw
     type:urn:nlp-types:search
     resultType:category
     */
}

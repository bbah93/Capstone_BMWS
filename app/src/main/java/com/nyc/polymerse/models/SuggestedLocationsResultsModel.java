package com.nyc.polymerse.models;

import java.util.List;

/**
 * Created by Wayne Kellman on 3/31/18.
 */

public class SuggestedLocationsResultsModel {
    private List<SuggestedLocationModel> results;

    public List<SuggestedLocationModel> getResults() {
        return results;
    }

    public void setResults(List<SuggestedLocationModel> results) {
        this.results = results;
    }
}

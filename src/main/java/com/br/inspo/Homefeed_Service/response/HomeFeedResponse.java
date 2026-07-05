package com.br.inspo.Homefeed_Service.response;

import com.br.inspo.Homefeed_Service.module.FeedModule;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class HomeFeedResponse {

    @NonNull
    @JsonProperty("feedModules")
    private final List<FeedModule> feedModules;

    //TODO: More data can be added here in json object for feed rendering

    public HomeFeedResponse(List<FeedModule> modules) {
        this.feedModules = modules;
    }
}

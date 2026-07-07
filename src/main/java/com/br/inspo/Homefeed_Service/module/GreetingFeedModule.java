package com.br.inspo.Homefeed_Service.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

/**
 * HomeFeed Module for user greeting.
 */
@Data
public class GreetingFeedModule implements FeedModule {

    @JsonProperty("type")
    private final String type = "greeting";
    @NonNull
    @JsonProperty("text")
    private String text;

}

package com.br.inspo.Homefeed_Service.modules;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

@Data
public class GreetingFeedModule implements FeedModule {

    @JsonProperty("type")
    private final String type = "greeting";
    @NonNull
    @JsonProperty("language")
    private String language;
    @NonNull
    @JsonProperty("text")
    private String text;

}

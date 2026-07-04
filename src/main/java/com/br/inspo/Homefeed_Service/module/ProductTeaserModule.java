package com.br.inspo.Homefeed_Service.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

@Data
public class ProductTeaserModule implements FeedModule {

    @JsonProperty("type")
    private final String type = "product_teaser";
    @NonNull
    @JsonProperty("pictureUrl")
    private String pictureUrl;
    @NonNull
    @JsonProperty("price")
    private double price;
}

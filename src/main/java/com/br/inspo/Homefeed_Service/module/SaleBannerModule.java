package com.br.inspo.Homefeed_Service.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

/**
 * HomeFeed Module for sale banners.
 */
@Data
public class SaleBannerModule implements FeedModule {

    @JsonProperty("type")
    private final String type = "sale_banner";
    @NonNull
    @JsonProperty("headline")
    private String headline;
    @NonNull
    @JsonProperty("ctaLabel")
    private String ctaLabel;
    @NonNull
    @JsonProperty("pictureUrl")
    private String pictureUrl;

}

package com.br.inspo.Homefeed_Service.response;

import com.br.inspo.Homefeed_Service.module.FeedModule;
import lombok.Getter;

import java.util.List;

@Getter
public class HomeFeedResponse {

    private final List<FeedModule> feedModules;

    public HomeFeedResponse(List<FeedModule> modules) {
        this.feedModules = modules;
    }
}

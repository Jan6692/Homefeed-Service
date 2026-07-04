package com.br.inspo.Homefeed_Service.service;

import com.br.inspo.Homefeed_Service.modules.FeedModule;
import com.br.inspo.Homefeed_Service.modules.GreetingFeedModule;
import com.br.inspo.Homefeed_Service.modules.ProductTeaserModule;
import com.br.inspo.Homefeed_Service.modules.SaleBannerModule;
import com.br.inspo.Homefeed_Service.response.HomeFeedResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service which delivers all methods needed to get homefeed related data.
 */
@Service
public class HomeFeedService {

    /**
     * Builds and returns the homefeed for a given user.
     *
     * @param userId id of the user for which the homefeed should be built and returned
     * @return homefeed for given user
     */
    public List<FeedModule> getHomeFeedResponse(String userId) {
        List<FeedModule> modules = new ArrayList<>();
        modules.add(new GreetingFeedModule("Deutsch", "Hallo Tania!"));
        modules.add(new ProductTeaserModule("breuninger.de/products/pictures/shirt", 29.99));
        modules.add(new SaleBannerModule("Deutsch", "SALE!", "Jetzt shoppen", "breuninger.de/ad/pictures/sale"));
        HomeFeedResponse homeFeedResponse = new HomeFeedResponse(modules);
        return homeFeedResponse.getFeedModules();
    }

}

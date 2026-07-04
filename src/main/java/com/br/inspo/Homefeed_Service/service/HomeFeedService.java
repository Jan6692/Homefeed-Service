package com.br.inspo.Homefeed_Service.service;

import com.br.inspo.Homefeed_Service.module.FeedModule;
import com.br.inspo.Homefeed_Service.module.GreetingFeedModule;
import com.br.inspo.Homefeed_Service.module.ProductTeaserModule;
import com.br.inspo.Homefeed_Service.module.SaleBannerModule;
import com.br.inspo.Homefeed_Service.repository.UserRepository;
import com.br.inspo.Homefeed_Service.response.HomeFeedResponse;
import com.br.inspo.Homefeed_Service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service which delivers all methods needed to get homefeed related data.
 */
@Service
public class HomeFeedService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Builds and returns the homefeed for a given user.
     *
     * @param userId id of the user for which the homefeed should be built and returned
     * @return homefeed for given user
     */
    public List<FeedModule> getHomeFeedResponse(String userId) {
        List<FeedModule> modules = new ArrayList<>();
        Optional<User> optionalUser = userRepository.findById(2L);
        modules.add(new GreetingFeedModule("Deutsch", "Hallo Tania!"));
        modules.add(new ProductTeaserModule("breuninger.de/products/pictures/shirt", 29.99));
        modules.add(new SaleBannerModule("Deutsch", "SALE!", "Jetzt shoppen", "breuninger.de/ad/pictures/sale"));
        HomeFeedResponse homeFeedResponse = new HomeFeedResponse(modules);
        return homeFeedResponse.getFeedModules();
    }

}

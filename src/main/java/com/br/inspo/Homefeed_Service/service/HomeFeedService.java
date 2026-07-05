package com.br.inspo.Homefeed_Service.service;

import com.br.inspo.Homefeed_Service.entity.Greeting;
import com.br.inspo.Homefeed_Service.entity.SaleBanner;
import com.br.inspo.Homefeed_Service.exception.LanguageNotFoundException;
import com.br.inspo.Homefeed_Service.exception.UserNotFoundException;
import com.br.inspo.Homefeed_Service.module.FeedModule;
import com.br.inspo.Homefeed_Service.module.GreetingFeedModule;
import com.br.inspo.Homefeed_Service.module.ProductTeaserModule;
import com.br.inspo.Homefeed_Service.module.SaleBannerModule;
import com.br.inspo.Homefeed_Service.repository.GreetingRepository;
import com.br.inspo.Homefeed_Service.repository.ProductRepository;
import com.br.inspo.Homefeed_Service.repository.SaleBannerRepository;
import com.br.inspo.Homefeed_Service.repository.UserRepository;
import com.br.inspo.Homefeed_Service.response.HomeFeedResponse;
import com.br.inspo.Homefeed_Service.entity.User;
import com.br.inspo.Homefeed_Service.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
    private GreetingRepository greetingRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SaleBannerRepository saleBannerRepository;

    Logger logger = LoggerFactory.getLogger(HomeFeedService.class);


    /**
     * Builds and returns the homefeed for a given user.
     *
     * @param userId id of the user for which the homefeed should be built and returned
     * @return homefeed for given user
     */
    public HomeFeedResponse getHomeFeedResponse(Long userId) throws UserNotFoundException, LanguageNotFoundException {
        List<FeedModule> modules = new ArrayList<>();
        User user = retrieveUser(userId);
        modules.add(buildGreetingFeedModule(user));

        // find sale banner for user language in database



        // product

        modules.add(new ProductTeaserModule("breuninger.de/products/pictures/shirt", 29.99));
        modules.add(new SaleBannerModule( "SALE!", "Jetzt shoppen", "breuninger.de/ad/pictures/sale"));
        HomeFeedResponse homeFeedResponse = new HomeFeedResponse(modules);
        return homeFeedResponse;
    }

    /**
     * Retrieves the user entity from the database for the given user id.
     *
     * @param userId id for which the user should be loaded from database
     * @return User entity for given user id
     * @throws UserNotFoundException
     */
    private User retrieveUser(Long userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            String errorMessage = String.format(Constants.USER_NOT_FOUND_MESSAGE, userId);
            logger.error(errorMessage);
            throw new UserNotFoundException(errorMessage);
        }
        logger.info("User retrieved successfully for userId: {}", userId);
        return optionalUser.get();
    }

    /**
     * Finds the correct greeting for the given user and builds a GreetingFeedModule for it.
     *
     * @param user user for which the GreetingFeedModule shall be built
     * @return GreetingFeedModule for given user
     * @throws LanguageNotFoundException
     */
    private GreetingFeedModule buildGreetingFeedModule(User user) throws LanguageNotFoundException {
        // find greeting for user language in database & build GreetingFeedModule
        List<Greeting> greetings = greetingRepository.findByLanguage(user.getLanguage());
        if (greetings.isEmpty()) {
            String errorMessage = String.format(Constants.USER_LANGUAGE_NOT_FOUND_FOR_MODULE, user.getLanguage(), "Greeting");
            logger.error(errorMessage);
            throw new LanguageNotFoundException(errorMessage);
        }
        // TODO: greetings could be added based on time or other conditions - more complexity/personilization
        //  could be added here
        Greeting greeting = greetings.getFirst();
        GreetingFeedModule greetingFeedModule = new GreetingFeedModule(
                String.format(greeting.getText(), user.getName())
        );
        logger.info("GreetingFeedModule built successfully for user with id: {}", user.getId());
        return greetingFeedModule;
    }


}

package com.br.inspo.Homefeed_Service.service;

import com.br.inspo.Homefeed_Service.entity.Product;
import com.br.inspo.Homefeed_Service.entity.User;
import com.br.inspo.Homefeed_Service.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * This service retrieves the predicted preferences for given users e.g. for personalized product advertisement.
 */
@Service
public class PreferencePredictionService {

    @Autowired
    ProductRepository productRepository;

    Logger logger = LoggerFactory.getLogger(PreferencePredictionService.class);
    Random random = new Random();

    /**
     * Gets the preferred product for a product advertisement for the given user.
     *
     * @param user user for which a product shall be found
     * @return product for advertisement
     */
    public Product getProductPreference(User user) {
        //TODO: Here an external API or some other mechanism can be used to retrieve the personalized product ad for
        // the given user - for this sample we just use a random product
        Long productId = random.nextLong(1L, 6L);
        logger.info("Retrieved product with id {} as user preference for user with id {}.", productId, user.getId());
        return productRepository.getReferenceById(productId);
    }

}

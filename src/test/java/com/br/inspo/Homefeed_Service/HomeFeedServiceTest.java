package com.br.inspo.Homefeed_Service;

import com.br.inspo.Homefeed_Service.entity.Greeting;
import com.br.inspo.Homefeed_Service.entity.Product;
import com.br.inspo.Homefeed_Service.entity.SaleBanner;
import com.br.inspo.Homefeed_Service.entity.User;
import com.br.inspo.Homefeed_Service.exception.LanguageNotFoundException;
import com.br.inspo.Homefeed_Service.exception.UserNotFoundException;
import com.br.inspo.Homefeed_Service.module.FeedModule;
import com.br.inspo.Homefeed_Service.module.GreetingFeedModule;
import com.br.inspo.Homefeed_Service.module.ProductTeaserModule;
import com.br.inspo.Homefeed_Service.module.SaleBannerModule;
import com.br.inspo.Homefeed_Service.repository.GreetingRepository;
import com.br.inspo.Homefeed_Service.repository.SaleBannerRepository;
import com.br.inspo.Homefeed_Service.repository.UserRepository;
import com.br.inspo.Homefeed_Service.response.HomeFeedResponse;
import com.br.inspo.Homefeed_Service.service.HomeFeedService;
import com.br.inspo.Homefeed_Service.service.PreferencePredictionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class HomeFeedServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private SaleBannerRepository saleBannerRepository;
    @Mock
    private GreetingRepository greetingRepository;
    @Mock
    private PreferencePredictionService preferencePredictionService;

    @InjectMocks
    private HomeFeedService homeFeedService;

    private User sampleUser;
    private User sampleUserRu;
    private Greeting sampleGreeting;
    List<Greeting> sampleGreetingList;
    private SaleBanner sampleSaleBanner;
    List<SaleBanner> sampleSaleBannerList;
    private Product sampleProduct;

    @BeforeAll
    public static void setup() {
        MockitoAnnotations.openMocks(HomeFeedServiceTest.class);
    }

    @BeforeEach
    public void init() {
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setName("John");
        sampleUser.setLastName("Doe");
        sampleUser.setLanguage("en");

        sampleUserRu = new User();
        sampleUserRu.setId(3L);
        sampleUserRu.setName("Wladimir");
        sampleUserRu.setLastName("Iwanov");
        sampleUserRu.setLanguage("ru");

        sampleGreeting = new Greeting();
        sampleGreeting.setId(1L);
        sampleGreeting.setLanguage("en");
        sampleGreeting.setText("Good Morning %s!");
        sampleGreetingList = new ArrayList<>();
        sampleGreetingList.add(sampleGreeting);

        sampleSaleBanner = new SaleBanner();
        sampleSaleBanner.setId(1L);
        sampleSaleBanner.setLanguage("en");
        sampleSaleBanner.setHeadline("Summer Sale - Up to 50% Off");
        sampleSaleBanner.setCtaLabel("Shop Now");
        sampleSaleBanner.setPictureUrl("https://example.com/banners/en-summer.jpg");
        sampleSaleBannerList = new ArrayList<>();
        sampleSaleBannerList.add(sampleSaleBanner);

        sampleProduct = new Product();
        sampleProduct.setId(1L);
        sampleProduct.setPictureUrl("https://example.com/products/sneakers.jpg");
        sampleProduct.setPrice(59.99);
    }

    /**
     * Test for successful user retrieval.
     *
     * @throws UserNotFoundException
     */
    @Test
    public void testRetrieveUser() throws UserNotFoundException {
        Mockito
                .when(this.userRepository.findById(1L))
                .thenReturn(Optional.of(sampleUser));
        User retrievedUser = homeFeedService.retrieveUser(1L);
        Assertions.assertEquals(1L, retrievedUser.getId());
        Assertions.assertEquals("John", retrievedUser.getName());
        Assertions.assertEquals("Doe", retrievedUser.getLastName());
        Assertions.assertEquals("en", retrievedUser.getLanguage());
    }

    /**
     * Test for user retrieval of a non-existing user id which should throw UserNotFoundException.
     */
    @Test
    public void testRetrieveUserNotAvailable() {
        Mockito
                .when(this.userRepository.findById(2L))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> homeFeedService.retrieveUser(2L));
    }

    /**
     * Test for successful GreetingFeedModule retrieval.
     *
     * @throws LanguageNotFoundException
     */
    @Test
    public void testBuildGreetingFeedModule() throws LanguageNotFoundException {
        Mockito
                .when(this.greetingRepository.findByLanguage("en"))
                .thenReturn(sampleGreetingList);
        GreetingFeedModule greetingFeedModule = homeFeedService.buildGreetingFeedModule(sampleUser);
        Assertions.assertEquals("Good Morning John!", greetingFeedModule.getText());
        Assertions.assertEquals("greeting", greetingFeedModule.getType());
    }

    /**
     * Test for GreetingFeedModule retrieval for a user with a language that is not available. Should throw
     * LanguageNotFoundException.
     */
    @Test
    public void testBuildGreetingFeedModuleLanguageNotAvailable() {
        Mockito
                .when(this.greetingRepository.findByLanguage("ru"))
                .thenReturn(new ArrayList<>());
        Assertions.assertThrows(LanguageNotFoundException.class, () -> homeFeedService.buildGreetingFeedModule(sampleUserRu));
    }

    /**
     * Test for successful SaleBannerModule retrieval.
     *
     * @throws LanguageNotFoundException
     */
    @Test
    public void testBuildSaleBannerModule() throws LanguageNotFoundException {
        Mockito
                .when(this.saleBannerRepository.findByLanguage("en"))
                .thenReturn(sampleSaleBannerList);
        SaleBannerModule saleBannerModule = homeFeedService.buildSaleBannerModule(sampleUser);
        Assertions.assertEquals("Summer Sale - Up to 50% Off", saleBannerModule.getHeadline());
        Assertions.assertEquals("Shop Now", saleBannerModule.getCtaLabel());
        Assertions.assertEquals("https://example.com/banners/en-summer.jpg", saleBannerModule.getPictureUrl());
        Assertions.assertEquals("sale_banner", saleBannerModule.getType());
    }

    /**
     * Test for SaleBannerModule retrieval for a user with a language that is not available. Should throw
     * LanguageNotFoundException.
     */
    @Test
    public void testBuildSaleBannerModuleLanguageNotAvailable() {
        Mockito
                .when(this.saleBannerRepository.findByLanguage("ru"))
                .thenReturn(new ArrayList<>());
        Assertions.assertThrows(LanguageNotFoundException.class, () -> homeFeedService.buildSaleBannerModule(sampleUserRu));
    }

    /**
     * Test for successful ProductTeaserModule retrieval.
     *
     * @throws LanguageNotFoundException
     */
    @Test
    public void testBuildProductTeaserModule() throws LanguageNotFoundException {
        Mockito
                .when(this.preferencePredictionService.getProductPreference(sampleUser))
                .thenReturn(sampleProduct);
        ProductTeaserModule productTeaserModule = homeFeedService.buildProductTeaserModule(sampleUser);
        Assertions.assertEquals("https://example.com/products/sneakers.jpg", productTeaserModule.getPictureUrl());
        Assertions.assertEquals(59.99, productTeaserModule.getPrice());
        Assertions.assertEquals("product_teaser", productTeaserModule.getType());
    }

    /**
     * Test for successful HomeFeedModule retrieval.
     *
     * @throws UserNotFoundException
     * @throws LanguageNotFoundException
     */
    @Test
    public void testGetHomeFeedResponse() throws UserNotFoundException, LanguageNotFoundException {
        Mockito
                .when(this.userRepository.findById(1L))
                .thenReturn(Optional.of(sampleUser));
        Mockito
                .when(this.greetingRepository.findByLanguage("en"))
                .thenReturn(sampleGreetingList);
        Mockito
                .when(this.saleBannerRepository.findByLanguage("en"))
                .thenReturn(sampleSaleBannerList);
        Mockito
                .when(this.preferencePredictionService.getProductPreference(sampleUser))
                .thenReturn(sampleProduct);

        HomeFeedResponse homeFeedResponse = homeFeedService.getHomeFeedResponse(1L);

        List<FeedModule> modules = homeFeedResponse.getFeedModules();
        Assertions.assertEquals(3, modules.size());
        Assertions.assertInstanceOf(GreetingFeedModule.class, modules.get(0));
        Assertions.assertInstanceOf(SaleBannerModule.class, modules.get(1));
        Assertions.assertInstanceOf(ProductTeaserModule.class, modules.get(2));
        Assertions.assertEquals("Good Morning John!", ((GreetingFeedModule) modules.get(0)).getText());
        // TODO: More detailed assertions can be added here to check each module element in detail
    }

    /**
     * Test for HomeFeedModule retrieval with user not available. UserNotFoundException should be thrown.
     */
    @Test
    public void testGetHomeFeedResponseUserNotFound() {
        Mockito
                .when(this.userRepository.findById(99L))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> homeFeedService.getHomeFeedResponse(99L));
    }

    /**
     * Test for HomeFeedModule retrieval with user language not available. LanguageNotFoundException should be thrown.
     */
    @Test
    public void testGetHomeFeedResponseLanguageNotFound() {
        Mockito
                .when(this.userRepository.findById(3L))
                .thenReturn(Optional.of(sampleUserRu));
        Mockito
                .when(this.greetingRepository.findByLanguage("ru"))
                .thenReturn(new ArrayList<>());
        Assertions.assertThrows(LanguageNotFoundException.class, () -> homeFeedService.getHomeFeedResponse(3L));
    }
}

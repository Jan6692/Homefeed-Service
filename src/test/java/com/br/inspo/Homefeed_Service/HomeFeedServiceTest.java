package com.br.inspo.Homefeed_Service;

import com.br.inspo.Homefeed_Service.entity.Greeting;
import com.br.inspo.Homefeed_Service.entity.User;
import com.br.inspo.Homefeed_Service.exception.LanguageNotFoundException;
import com.br.inspo.Homefeed_Service.exception.UserNotFoundException;
import com.br.inspo.Homefeed_Service.module.GreetingFeedModule;
import com.br.inspo.Homefeed_Service.repository.GreetingRepository;
import com.br.inspo.Homefeed_Service.repository.SaleBannerRepository;
import com.br.inspo.Homefeed_Service.repository.UserRepository;
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
    }

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

    @Test
    public void testRetrieveUserNotAvailable() throws UserNotFoundException {
        Mockito
                .when(this.userRepository.findById(2L))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> homeFeedService.retrieveUser(2L));
    }

    @Test
    public void testBuildGreetingFeedModule() throws LanguageNotFoundException {
        Mockito
                .when(this.greetingRepository.findByLanguage("en"))
                .thenReturn(sampleGreetingList);
        GreetingFeedModule greetingFeedModule = homeFeedService.buildGreetingFeedModule(sampleUser);
        Assertions.assertEquals("Good Morning John!", greetingFeedModule.getText());
        Assertions.assertEquals("greeting", greetingFeedModule.getType());
    }

    @Test
    public void testBuildGreetingFeedModuleLanguageNotAvailable() throws LanguageNotFoundException {
        Mockito
                .when(this.greetingRepository.findByLanguage("ru"))
                .thenReturn(new ArrayList<>());
        Assertions.assertThrows(LanguageNotFoundException.class, () -> homeFeedService.buildGreetingFeedModule(sampleUserRu));
    }

    //TODO: Add similar tests for other methods of HomeFeedService
}

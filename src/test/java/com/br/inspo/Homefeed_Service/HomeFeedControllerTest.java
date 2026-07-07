package com.br.inspo.Homefeed_Service;

import com.br.inspo.Homefeed_Service.controller.HomeFeedController;
import com.br.inspo.Homefeed_Service.exception.LanguageNotFoundException;
import com.br.inspo.Homefeed_Service.exception.UserNotFoundException;
import com.br.inspo.Homefeed_Service.module.FeedModule;
import com.br.inspo.Homefeed_Service.module.GreetingFeedModule;
import com.br.inspo.Homefeed_Service.module.ProductTeaserModule;
import com.br.inspo.Homefeed_Service.module.SaleBannerModule;
import com.br.inspo.Homefeed_Service.response.HomeFeedResponse;
import com.br.inspo.Homefeed_Service.service.HomeFeedService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = HomeFeedController.class)
public class HomeFeedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HomeFeedService homeFeedService;

    /**
     * Test successful HomeFeedModule retrieval.
     *
     * @throws Exception
     */
    @Test
    public void testGetHomeFeedReturnsOkWithModules() throws Exception {
        List<FeedModule> modules = new ArrayList<>();
        modules.add(new GreetingFeedModule("Hello John!"));
        modules.add(new SaleBannerModule("Summer Sale", "Shop Now", "https://example.com/banner.jpg"));
        modules.add(new ProductTeaserModule("https://example.com/product.jpg", 59.99));
        HomeFeedResponse response = new HomeFeedResponse(modules);

        when(homeFeedService.getHomeFeedResponse(1L)).thenReturn(response);

        mockMvc.perform(get("/endpoints/homefeed").param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.feedModules").isArray())
                .andExpect(jsonPath("$.feedModules.length()").value(3))
                .andExpect(jsonPath("$.feedModules[0].type").value("greeting"))
                .andExpect(jsonPath("$.feedModules[0].text").value("Hello John!"))
                .andExpect(jsonPath("$.feedModules[1].type").value("sale_banner"))
                .andExpect(jsonPath("$.feedModules[1].headline").value("Summer Sale"))
                .andExpect(jsonPath("$.feedModules[1].ctaLabel").value("Shop Now"))
                .andExpect(jsonPath("$.feedModules[1].pictureUrl").value("https://example.com/banner.jpg"))
                .andExpect(jsonPath("$.feedModules[2].type").value("product_teaser"))
                .andExpect(jsonPath("$.feedModules[2].pictureUrl").value("https://example.com/product.jpg"))
                .andExpect(jsonPath("$.feedModules[2].price").value(59.99));
    }

    /**
     * Test HomeFeedModule retrieval with missing user id.
     *
     * @throws Exception
     */
    @Test
    public void testGetHomeFeedMissingUserIdReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/endpoints/homefeed"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test HomeFeedModule retrieval with wrong format user id.
     *
     * @throws Exception
     */
    @Test
    public void testGetHomeFeedInvalidUserIdTypeReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/endpoints/homefeed").param("userId", "not-a-number"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test HomeFeedModule retrieval with non-existing user id.
     *
     * @throws Exception
     */
    @Test
    public void testGetHomeFeedUserNotFoundReturnsBadRequestWithMessage() throws Exception {
        when(homeFeedService.getHomeFeedResponse(anyLong()))
                .thenThrow(new UserNotFoundException("User with id '42' was not found"));

        mockMvc.perform(get("/endpoints/homefeed").param("userId", "42"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User with id '42' was not found"));
    }

    /**
     * Test HomeFeedModule retrieval with missing user language for module Greeting.
     *
     * @throws Exception
     */
    @Test
    public void testGetHomeFeedLanguageNotFoundReturnsBadRequestWithMessage() throws Exception {
        when(homeFeedService.getHomeFeedResponse(anyLong()))
                .thenThrow(new LanguageNotFoundException("User language 'ru' was not found for module 'Greeting'"));

        mockMvc.perform(get("/endpoints/homefeed").param("userId", "3"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User language 'ru' was not found for module 'Greeting'"));
    }
}
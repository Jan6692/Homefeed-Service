package com.br.inspo.Homefeed_Service.controller;

import com.br.inspo.Homefeed_Service.exception.LanguageNotFoundException;
import com.br.inspo.Homefeed_Service.exception.UserNotFoundException;
import com.br.inspo.Homefeed_Service.response.HomeFeedResponse;
import com.br.inspo.Homefeed_Service.service.HomeFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Homefeed REST Controller class that provides REST interfaces connected with homefeed.
 */
@RestController
@RequestMapping("/endpoints/homefeed")
public class HomeFeedController {

    @Autowired
    private HomeFeedService homeFeedService;

    /**
     * Returns the homefeed for a specific user given by userId via parameters.
     *
     * @param userId userId of the user for which the homefeed shall be returned
     * @return homefeed for the given user
     */
    @GetMapping
    public ResponseEntity<HomeFeedResponse> getHomeFeed(@RequestParam(name = "userId", required = true) Long userId) throws UserNotFoundException, LanguageNotFoundException {
        return ResponseEntity.ok(homeFeedService.getHomeFeedResponse(userId));
    }

    //TODO: endpoints for Homefeed Modules can be added here if there are use cases in which UI only needs single
    // modules e.g. user wants new product suggestion - also other Homefeed related endpoints may be possible here

}

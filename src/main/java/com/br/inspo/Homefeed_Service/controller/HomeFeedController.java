package com.br.inspo.Homefeed_Service.controller;

import com.br.inspo.Homefeed_Service.module.FeedModule;
import com.br.inspo.Homefeed_Service.service.HomeFeedService;
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

    private final HomeFeedService homeFeedService;

    public HomeFeedController(HomeFeedService homeFeedService) {
        this.homeFeedService = homeFeedService;
    }

    /**
     * Returns the homefeed for a specific user given by userId via parameters.
     *
     * @param userId userId of the user for which the homefeed shall be returned
     * @return homefeed for the given user
     */
    @GetMapping
    public ResponseEntity<List<FeedModule>> getHomeFeed(@RequestParam(name = "userId", required = true) String userId){
        return ResponseEntity.ok(homeFeedService.getHomeFeedResponse(userId));
    }

}

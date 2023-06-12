package org.edupoll.controller.api;

import org.edupoll.model.dto.response.FollowResponseData;
import org.edupoll.security.support.Account;
import org.edupoll.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/api/follow")
public class FollowAPIController {

	@Autowired
	FollowService followService;

	@PostMapping
	public FollowResponseData addFollowHandle(@AuthenticationPrincipal Account account, String target) {
		FollowResponseData fsd = followService.createFollow(account.getUsername(), target);

		return fsd;
	}
	
	@DeleteMapping
	public FollowResponseData deleteFollowHandle(@AuthenticationPrincipal Account account, String target) {
		FollowResponseData fsd = followService.deleteFollow(account.getUsername(), target);

		return fsd;
	}

}

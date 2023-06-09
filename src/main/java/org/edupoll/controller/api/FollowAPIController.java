package org.edupoll.controller.api;

import java.util.List;

import org.edupoll.model.dto.response.AttedanceJoinResponseData;
import org.edupoll.model.dto.response.FollowResponseData;
import org.edupoll.model.entity.Moim;
import org.edupoll.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
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
	public FollowResponseData addFollowHandle(@SessionAttribute(name = "logonId") String owner, String target) {
		FollowResponseData fsd = followService.createFollow(owner, target);

		return fsd;
	}
	
	@DeleteMapping
	public FollowResponseData deleteFollowHandle(@SessionAttribute(name = "logonId") String owner, String target) {
		FollowResponseData fsd = followService.deleteFollow(owner, target);

		return fsd;
	}

}

package org.edupoll.service;

import java.util.Date;
import java.util.List;

import org.edupoll.model.dto.response.FollowResponseData;
import org.edupoll.model.entity.Follow;
import org.edupoll.repository.FollowRepository;
import org.edupoll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class FollowService {

	@Autowired
	FollowRepository followRepository;

	@Autowired
	UserRepository userRepository;

	// 누군가 누구를 팔로우하고자 할 때 그걸 처리할 서비스 method
	@Transactional
	public FollowResponseData createFollow(String ownerId, String targetId) {
		if (followRepository.existsByOwnerIdIsAndTargetIdIs(ownerId, targetId)) {
			// 실패를 알리는 DTO 리턴 > 바로 ResponseBody 로 나갈꺼니 객체로 리턴하는게 좋음.
			return new FollowResponseData(false);
		} else {
			Follow follow = new Follow();
			follow.setOwner(userRepository.findById(ownerId).get()); // 100 프로 있다고 가정
			follow.setTarget(userRepository.findById(targetId).get()); // 잠재적인 오류의 원인 가능성 o
			follow.setCreated(new Date());

			followRepository.save(follow);

			return new FollowResponseData(true);
		}
	}

	// 누군가 누구를 언팔로우 하고자 할 때 그걸 처리할 서비스 method
	@Transactional
	public FollowResponseData deleteFollow(String ownerId, String targetId) {
		followRepository.deleteByOwnerIdIsAndTargetIdIs(ownerId, targetId);

		// 실패를 알리는 DTO 리턴
		return new FollowResponseData(true);

		// 성공을 알리는 DTO 리턴
	}
	
}

package org.edupoll.service;

import java.util.List;
import java.util.Optional;

import org.edupoll.model.dto.request.LoginRequestData;
import org.edupoll.model.dto.request.UserDTO;
import org.edupoll.model.entity.Attendance;
import org.edupoll.model.entity.Avatar;
import org.edupoll.model.entity.Moim;
import org.edupoll.model.entity.User;
import org.edupoll.model.entity.UserDetail;
import org.edupoll.repository.AttendanceRepository;
import org.edupoll.repository.AvatarRepository;
import org.edupoll.repository.MoimRepository;
import org.edupoll.repository.ReplyRepository;
import org.edupoll.repository.UserDetailRepository;
import org.edupoll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserDetailRepository userDetailRepository;

	@Autowired
	AttendanceRepository attendanceRepository;

	@Autowired
	ReplyRepository replyRepository;

	@Autowired
	MoimRepository moimRepository;
	
	@Autowired
	AvatarRepository avatarRepository;

	@Transactional
	public boolean deleteSpecificUser(String userId) {
		// userId 에 해당하는 데이터를 userRepository 에서 지우고
		if (!userRepository.findById(userId).isPresent()) {
			return false;
		}
		User found = userRepository.findById(userId).get();

		attendanceRepository.deleteByUserId(found.getId());

		List<Moim> moims = moimRepository.findByManagerId(userId);
		for (Moim m : moims) {
			attendanceRepository.deleteByMoimId(m.getId());
			replyRepository.deleteByMoimId(m.getId());
		}

		moimRepository.deleteByManagerId(userId);

		UserDetail userDetail = found.getUserDetail();
		// 멀 먼저 지워? UserDetail ?? User ??

		userRepository.delete(found);
		userDetailRepository.delete(userDetail);
		return true;
	}

	// 회원상세정보를 수정/변경 처리할 서비스 메서드
	public boolean modifySpecificUserDetail(String userId, UserDetail detail) {
		// 1. 특정 유저가 존재하는지 확인
		if (userRepository.findById(userId).isEmpty())
			return false;
		// 2. UserDetail 저장하고
		User foundUser = userRepository.findById(userId).get();
		if (foundUser.getUserDetail() != null) {
			detail.setIdx(foundUser.getUserDetail().getIdx());
		}
		System.out.println("아바타 정보 : " + detail.getAvatar());
		UserDetail saved = userDetailRepository.save(detail);
		// 3. 특정 유저의 detail_idx 에 방금 저장하며 부여받은 id 값을 설정해서 update
		foundUser.setUserDetail(saved);
		userRepository.save(foundUser);
		return true;
	}

	// 회원 가입을 처리할 서비스 메서드
	public boolean createNewUser(UserDTO userDTO) {
		String id = userDTO.getId();
		if (userRepository.findById(id).isPresent()) {
			return false;
		} else {
			UserDTO u = new UserDTO(id, userDTO.getPassword(), userDTO.getNick());
			User user = new User(id, u.getPassword(), u.getNick());
			userRepository.save(user);
			
			return true;
		}

	}

	// 로그인 처리를 하기 위해 사용할 서비스 메서드
	public boolean isValidUser(LoginRequestData data) { // id, pass
		Optional<User> optional = userRepository.findById(data.getLoginId());
		if (optional.isPresent()) {
			String savedPass = optional.get().getPass();
			// System.out.println(optional.get().getUserDetail());

			return savedPass.equals(data.getLoginPass());
		}
		return false;
	}

	public UserDetail findSpecificSavedDetail(String logonId) {

		return userRepository.findById(logonId).get().getUserDetail();

//		UserDetail userDetail = userRepository.findById(logonId).get().getUserDetail();
//		if (userDetail == null) {
//			return null;
//		}
//		return userDetailRepository.findById(userDetail.getIdx()).orElse(null);

	}

	public User findSpecificUserById(String targetId) {
		return userRepository.findById(targetId).orElse(null);
	}

	public List<Attendance> findAttendingMoims(String targetId) {
		User found = userRepository.findById(targetId).get();
		List<Attendance> attendances = attendanceRepository.findByUserId(found.getId());

		return attendances;
	}

	public List<Avatar> loadAvatars() {
		
		return avatarRepository.findAll();
	}
}

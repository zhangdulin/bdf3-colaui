package com.bstek.cola.security.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.security.cache.SecurityCacheEvict;
import com.bstek.bdf3.security.orm.RoleGrantedAuthority;
import com.bstek.bdf3.security.orm.User;
import com.bstek.cola.security.service.UserService;

/** 
* 
* @author bob.yang
* @since 2017年12月15日
*
*/
@Service("cola.userService")
public class UserServiceImpl implements UserService {

	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Override
	public Page<User> load(Pageable pageable, String searchKey) {		
		return JpaUtil
			.linq(User.class)
			.addIf(searchKey)
				.or()
					.like("username", "%" + searchKey + "%")
					.like("nickname", "%" + searchKey + "%")
				.end()
			.endIf()
			.paging(pageable);
	}

	@Override
	public void remove(String username) {
		JpaUtil.lind(User.class).equal("username", username).delete();
	}

	@Override
	@Transactional
	public void add(User user) throws Exception {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		JpaUtil.persist(user);
	}

	@Override
	public void modify(User user) throws Exception {
		JpaUtil.merge(user);
	}
	
	@Override
	public void resetPassword(User user) {
		user.setPassword(passwordEncoder.encode("123456"));
		JpaUtil.merge(user);
	}

	@Override
	public void changePassword(String username, String newPassword) {

	}

	@Override
	public boolean validatePassword(String username, String password) {

		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isExist(String username) {
		boolean result = JpaUtil.linq(User.class).equal("username", username).exists();
		return result;
	}

	@Override
	@SecurityCacheEvict
	public String addRoleGrantedAuthority(RoleGrantedAuthority roleGrantedAuthority) {
		roleGrantedAuthority.setId(UUID.randomUUID().toString());
		JpaUtil.persist(roleGrantedAuthority);
		return roleGrantedAuthority.getId();
		
	}

	@Override
	@SecurityCacheEvict
	public void removeRoleGrantedAuthority(String id) {
		RoleGrantedAuthority roleGrantedAuthority = JpaUtil.getOne(RoleGrantedAuthority.class, id);
		JpaUtil.remove(roleGrantedAuthority);
	}

	@Override
	public void modifyNickname(String username, String nickname) {
		
	}

	
}

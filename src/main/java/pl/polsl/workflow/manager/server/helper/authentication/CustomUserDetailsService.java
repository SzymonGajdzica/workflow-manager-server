package pl.polsl.workflow.manager.server.helper.authentication;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.model.User;
import pl.polsl.workflow.manager.server.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if(!optionalUser.isPresent() || !optionalUser.get().getEnabled())
			throw new UsernameNotFoundException("User not found with username: " + username);
		User user = optionalUser.get();
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
		);
	}

}
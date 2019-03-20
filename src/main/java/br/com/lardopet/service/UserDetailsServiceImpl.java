package br.com.lardopet.service;


import br.com.lardopet.domain.User;
import br.com.lardopet.repository.UserRepository;
import br.com.lardopet.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return new UserSS(user.getId(), user.getEmail(), user.getPassword(), "ROLE_ADMIN");
        }
        throw new UsernameNotFoundException(email);
    }
}
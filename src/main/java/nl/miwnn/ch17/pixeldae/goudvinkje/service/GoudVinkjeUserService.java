package nl.miwnn.ch17.pixeldae.goudvinkje.service;

import nl.miwnn.ch17.pixeldae.goudvinkje.model.GoudVinkjeUser;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.GoudVinkjeUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Simon van der Kooij
 */

@Service
public class GoudVinkjeUserService implements UserDetailsService {

    private final GoudVinkjeUserRepository goudVinkjeUserRepository;
    private final PasswordEncoder passwordEncoder;

    public GoudVinkjeUserService(GoudVinkjeUserRepository goudVinkjeUserRepository, PasswordEncoder passwordEncoder) {
        this.goudVinkjeUserRepository = goudVinkjeUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return goudVinkjeUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found."));
    }

    public void saveUser(GoudVinkjeUser goudVinkjeUser) {
        goudVinkjeUser.setPassword(passwordEncoder.encode(goudVinkjeUser.getPassword()));
        goudVinkjeUserRepository.save(goudVinkjeUser);
    }

}

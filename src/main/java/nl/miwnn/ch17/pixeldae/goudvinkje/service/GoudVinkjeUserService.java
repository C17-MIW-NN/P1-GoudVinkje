package nl.miwnn.ch17.pixeldae.goudvinkje.service;

import nl.miwnn.ch17.pixeldae.goudvinkje.dto.GoudVinkjeUserDTO;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.GoudVinkjeUser;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.GoudVinkjeUserRepository;
import nl.miwnn.ch17.pixeldae.goudvinkje.service.mappers.GoudVinkjeUserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public boolean usernameInUse(String username) {
        return goudVinkjeUserRepository.existsByUsername(username);
    }

    public boolean hasNotSameID(Long userID, String username) {
        Optional<GoudVinkjeUser> optionalGoudVinkjeUser = goudVinkjeUserRepository.findByUsername(username);

        if (optionalGoudVinkjeUser.isPresent()) {
            return !Objects.equals(userID, optionalGoudVinkjeUser.get().getUserID());
        }

        return false;
    }

    public void deleteUserByID(Long userID) {
        goudVinkjeUserRepository.deleteById(userID);
    }

    public GoudVinkjeUserDTO editUser(String username) {
        Optional<GoudVinkjeUser> optionalGoudVinkjeUser = goudVinkjeUserRepository.findByUsername(username);

        if (optionalGoudVinkjeUser.isPresent()) {
            return GoudVinkjeUserMapper.toDTO(optionalGoudVinkjeUser.get());
        }

        return null;
    }

    public void saveUser(GoudVinkjeUser goudVinkjeUser) {
        goudVinkjeUser.setPassword(passwordEncoder.encode(goudVinkjeUser.getPassword()));
        goudVinkjeUserRepository.save(goudVinkjeUser);
    }

    public void save(GoudVinkjeUserDTO goudVinkjeUserDTO) {
        saveUser(GoudVinkjeUserMapper.fromDTO(goudVinkjeUserDTO));
    }

    public List<GoudVinkjeUser> findAll() {
        return goudVinkjeUserRepository.findAll();
    }
}

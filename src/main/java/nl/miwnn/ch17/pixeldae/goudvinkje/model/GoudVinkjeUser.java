package nl.miwnn.ch17.pixeldae.goudvinkje.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author Simon van der Kooij
 * All data and logic around users of this app
 */

@Entity
@Getter @Setter @NoArgsConstructor
public class GoudVinkjeUser implements UserDetails {

    @Id
    @GeneratedValue
    private Long userID;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(nullable = false)
    private String role; // bv. "ROLE_USER" of "ROLE_ADMIN"

    @OneToMany
    @JoinColumn(name = "userID")
    private List<Recipe> recipes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    //methods
    public int countRecipes() {
        return this.recipes.size();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

package com.sansyro.sgpspring.entity;

import com.sansyro.sgpspring.constants.FunctionalityEnum;
import com.sansyro.sgpspring.entity.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.CollectionTable;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static java.util.Objects.isNull;


@DiscriminatorValue("user")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Entity
@Table(name="user")
public class User implements UserDetails {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    @NotNull
    private String name;
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    //senhas padrão 123456
    private String password;

    @NotNull
    @Column(name="user_hash_code")
    private String userHashCode;
    private String token;
    @NotNull
    @Column(name="start_view")
    private String startView;

    @ElementCollection(targetClass = FunctionalityEnum.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="functionality_user", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "functionality")
    private Set<FunctionalityEnum> functionalities;

    public static User mapper(UserDTO dto) {
        if(isNull(dto)) return null;
        return User.builder()
                .id(dto.getId())
                .startView(dto.getStartView())
                .name(dto.getName())
                .email(dto.getEmail())
                .functionalities(dto.getFunctionalities())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> auths = new ArrayList<>();
        if(isNull(functionalities)) {
            return new ArrayList<>();
        }
        for (FunctionalityEnum functionality: functionalities) {
            auths.add(new SimpleGrantedAuthority(functionality.name()));
        }
        return auths;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}

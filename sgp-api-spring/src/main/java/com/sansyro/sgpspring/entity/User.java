package com.sansyro.sgpspring.entity;

import com.sansyro.sgpspring.constants.FunctionalityEnum;
import com.sansyro.sgpspring.entity.dto.UserResponse;
import com.sansyro.sgpspring.util.GeralUtil;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;


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

    @Enumerated(EnumType.STRING)
    private FunctionalityEnum init;

    @ElementCollection(targetClass = FunctionalityEnum.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="functionality_user", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "functionality")
    private Set<FunctionalityEnum> functionalities;

    public UsernamePasswordAuthenticationToken mapperAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password, getAuthorities());
    }

    public UserResponse mapperDTP() {
        return UserResponse.builder().id(id).startView(startView).name(name).email(email).functionalities(functionalities).build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> auths = new ArrayList<>();
        if(functionalities == null) {
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

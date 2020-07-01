package ua.lviv.frost.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.lviv.frost.entity.AppUser;
import ua.lviv.frost.repository.AppUserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private AppUserRepository appUserRepository;

    @Autowired
    public CustomUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) {
        AppUser user = appUserRepository.findByEmail(login)
                .orElseThrow(() ->
                        new RuntimeException("User not found [login: " + login + "]")
                );

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserByIdToUserDetails(Integer id) {
        return UserPrincipal.create(loadUserById(id));
    }


    @Transactional
    public AppUser loadUserById(Integer id) {
        return appUserRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found [id: " + id + "]")
        );
    }
}

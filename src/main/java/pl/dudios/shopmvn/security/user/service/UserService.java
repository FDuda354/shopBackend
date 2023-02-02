package pl.dudios.shopmvn.security.user.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dudios.shopmvn.security.user.model.AppUser;
import pl.dudios.shopmvn.security.user.model.AppUserDetails;
import pl.dudios.shopmvn.security.user.repository.UserRepo;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //System.out.println("====================================");
      //  System.out.println("userId = " + userId);
      //  AppUser appUser = userRepo.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        AppUser appUser = userRepo.findById(Long.parseLong(username)).orElseThrow(() -> new IllegalArgumentException("User not found"));
        AppUserDetails userDetails = new AppUserDetails(
                appUser.getUsername(),
                appUser.getPassword(),
                appUser.getAuthorities().stream().map(userRole -> (GrantedAuthority) userRole::name).toList());

        userDetails.setId(appUser.getId());
        return userDetails;
    }


}

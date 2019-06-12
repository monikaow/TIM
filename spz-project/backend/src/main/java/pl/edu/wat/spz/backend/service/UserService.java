package pl.edu.wat.spz.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.spz.backend.beans.MessageResolver;
import pl.edu.wat.spz.backend.dto.UserDto;
import pl.edu.wat.spz.backend.exceptions.UserAlreadyExistException;
import pl.edu.wat.spz.backend.repository.RoleRepository;
import pl.edu.wat.spz.backend.repository.UserRepository;
import pl.edu.wat.spz.domain.entity.Patient;
import pl.edu.wat.spz.domain.entity.Privilege;
import pl.edu.wat.spz.domain.entity.Role;
import pl.edu.wat.spz.domain.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    public static final String ROLE_DOCTOR = "ROLE_DOCTOR";

    public static final String ROLE_PATIENT = "ROLE_PATIENT";

    private PasswordEncoder passwordEncoder;

    private UserRepository repository;

    private RoleRepository roleRepository;

    private MessageResolver messageResolver;

    public UserService(PasswordEncoder passwordEncoder, UserRepository repository, RoleRepository roleRepository, MessageResolver messageResolver) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.messageResolver = messageResolver;
    }

    public boolean isEmailAlreadyExist(String email) {
        return repository.existsUserByEmail(email);
    }

    private User registerNewUserAccount(final User user, UserDto accountDto, final String role) {
        if (isEmailAlreadyExist(accountDto.getEmail())) throw new UserAlreadyExistException(messageResolver);
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setAcceptTerms(accountDto.isAcceptTerms());
        user.setRoles(Arrays.asList(roleRepository.findByName(role)));

        return repository.save(user);
    }

    public void setAuth(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Transactional
    public User registerNewPatient(UserDto user) {
        return registerNewUserAccount(new Patient(), user, ROLE_PATIENT);
    }
}

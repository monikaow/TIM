package pl.edu.wat.spz.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pl.edu.wat.spz.domain.entity.User;

@NoRepositoryBean
public interface UserBaseRepository<T extends User> extends JpaRepository<T, Long> {

    T findByEmail(String email);

    boolean existsUserByEmail(String email);
}

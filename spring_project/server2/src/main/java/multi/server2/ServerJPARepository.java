package multi.server2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("serverJPARepository")
public interface ServerJPARepository extends JpaRepository<UserEntity, Integer>, ServerRepository {
}

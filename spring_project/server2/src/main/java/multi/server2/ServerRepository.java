package multi.server2;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServerRepository {
    UserEntity save(UserEntity userEntity);

    @Query(value = "SELECT * FROM user_entity ORDER BY cur_time ASC limit 10", nativeQuery = true)
    List<UserEntity> findAllByOrderByTimestampAsc();
}

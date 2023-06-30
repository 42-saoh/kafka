package multi.server2;

import java.util.List;

public interface ServerRepository {
    UserEntity save(UserEntity userEntity);
    List<UserEntity> findAllByOrderByTimestampAsc();
}

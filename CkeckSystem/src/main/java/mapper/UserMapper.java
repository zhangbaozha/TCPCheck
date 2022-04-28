package mapper;

import org.apache.ibatis.annotations.Param;
import pojo.User;

import java.util.List;

public interface UserMapper {
    List<User> selectAll();
    List<User> find(@Param("username") String username,@Param("password") String password);
    List<String> findNotYet();
}

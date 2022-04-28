package mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Admin;

import java.util.List;

public interface AdminMapper {
    List<Admin> find(@Param("username") String username, @Param("password") String password);
}

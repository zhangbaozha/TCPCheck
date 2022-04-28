package mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Check;

import java.util.List;

public interface CheckMapper {
    void addCheckIn(Check check);
    void CheckOut(@Param("username")String username,@Param("check_out_time")String check_out_time);
    List<Check> selectAll();
    List<Check> selectAlready();
    List<Check> selectByName(@Param("username")String username);
}

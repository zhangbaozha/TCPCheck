package utils;

import mapper.CheckMapper;
import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.Check;

import java.util.List;

public class CheckUtils {
    public static void CheckIn(Check check){
        SqlSession sqlSession = MybatisUtils.getSession();
        CheckMapper checkMapper = sqlSession.getMapper(CheckMapper.class);
        checkMapper.addCheckIn(check);
        sqlSession.commit();
    }
    public static void checkOut(String username,String check_out_name){
        SqlSession sqlSession = MybatisUtils.getSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        CheckMapper checkMapper = sqlSession.getMapper(CheckMapper.class);
        checkMapper.CheckOut(username,check_out_name);
        sqlSession.commit();
    }
    public static List<Check> selectAll(){
        SqlSession sqlSession = MybatisUtils.getSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        CheckMapper checkMapper = sqlSession.getMapper(CheckMapper.class);
        List<Check> checks = checkMapper.selectAll();
        return checks;
    }
    public static List<Check> selectAlready(){
        SqlSession sqlSession = MybatisUtils.getSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        CheckMapper checkMapper = sqlSession.getMapper(CheckMapper.class);
        List<Check> checks = checkMapper.selectAlready();
        return checks;
    }
    public static List<String> selectNotYet(){
        SqlSession sqlSession = MybatisUtils.getSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<String> users = userMapper.findNotYet();
        return users;
    }
    public static List<Check> selectByName(String username){
        SqlSession sqlSession = MybatisUtils.getSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        CheckMapper checkMapper = sqlSession.getMapper(CheckMapper.class);
        List<Check> checks = checkMapper.selectByName(username);
        return checks;
    }

}

package utils;

import mapper.AdminMapper;
import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import pojo.Admin;
import pojo.User;

import java.util.List;

public class LoginUtil {
    public static boolean userlogin(String username,String password){
        SqlSession sqlSession = MybatisUtils.getSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.find(username,password);
        if(users.isEmpty()){
            return false;
        }
        else return true;
    }


    public static boolean adminlogin(String username,String password){
        SqlSession sqlSession = MybatisUtils.getSession();
        AdminMapper adminMapper = sqlSession.getMapper(AdminMapper.class);
        List<Admin> admins = adminMapper.find(username,password);
        if(admins.isEmpty()){
            return false;
        }
        else return true;
    }
}

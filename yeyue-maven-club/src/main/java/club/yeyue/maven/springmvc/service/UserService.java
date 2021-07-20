package club.yeyue.maven.springmvc.service;

import club.yeyue.maven.mysql.mybatis.demo.entity.MvcUserDemo;
import club.yeyue.maven.mysql.mybatis.demo.mapper.MvcUserDemoMapper;
import club.yeyue.maven.springmvc.model.ApiResponse;
import club.yeyue.maven.springmvc.model.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fred
 * @date 2021-07-20 09:32
 */
@Service
public class UserService {

    @Resource
    MvcUserDemoMapper mapper;

    public ApiResponse<Void> create(UserVO vo) {
        MvcUserDemo demo = new MvcUserDemo();
        demo.setAge(vo.getAge());
        demo.setUsername(vo.getUsername());
        demo.setGender(vo.getGender());
        mapper.insert(demo);
        return ApiResponse.ok();
    }
}

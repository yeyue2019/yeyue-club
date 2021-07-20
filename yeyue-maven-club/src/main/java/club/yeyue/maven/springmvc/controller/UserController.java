package club.yeyue.maven.springmvc.controller;

import club.yeyue.maven.springmvc.model.ApiResponse;
import club.yeyue.maven.springmvc.model.UserVO;
import club.yeyue.maven.springmvc.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户管理
 *
 * @author fred
 * @date 2021-07-15 17:48
 */
@RestController // = @ResponseBody + @Controller
@RequestMapping(value = "/yeyue/user")
public class UserController {

    @Resource
    UserService service;

    @PostMapping(value = "/create")
    public ApiResponse<Void> create(@RequestBody UserVO vo) {
        return service.create(vo);
    }

}

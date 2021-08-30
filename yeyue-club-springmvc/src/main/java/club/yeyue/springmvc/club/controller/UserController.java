package club.yeyue.springmvc.club.controller;

import club.yeyue.springmvc.club.domain.UserDomain;
import club.yeyue.springmvc.club.exception.ServiceException;
import club.yeyue.springmvc.club.exception.ServiceExceptionEnum;
import club.yeyue.springmvc.club.model.CommonPageResult;
import club.yeyue.springmvc.club.model.CommonPageable;
import club.yeyue.springmvc.club.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author fred
 * @date 2021-08-27 17:43
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    UserService service;

    @PostMapping("insert")
    public Long insert(@RequestBody UserDomain domain) {
        return service.insert(domain);
    }

    @GetMapping("/{id}")
    public UserDomain get(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @PutMapping("modify")
    public void modify(@RequestBody UserDomain domain) {
        service.modify(domain);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

    @GetMapping("select")
    public CommonPageResult<UserDomain> select(CommonPageable pageable) {
        return service.select(pageable);
    }

    @GetMapping("getById")
    public CommonPageResult<UserDomain> getById() {
        throw new NullPointerException("我空指针了");
    }

    @GetMapping("getByIdV2")
    public CommonPageResult<UserDomain> getByIdV2() {
        throw new ServiceException(ServiceExceptionEnum.SYS_ERROR);
    }
}

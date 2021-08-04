package club.yeyue.baomidou.club.service;

import club.yeyue.baomidou.club.constant.Constant;
import club.yeyue.baomidou.club.entity.OrderEntity;
import club.yeyue.baomidou.club.entity.UserEntity;
import club.yeyue.baomidou.club.mapper.OrderMapper;
import club.yeyue.baomidou.club.mapper.UserMapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author fred
 * @date 2021-08-04 16:07
 */
@Service
public class OrderService {

    @Resource
    UserMapper userMapper;
    @Resource
    OrderMapper orderMapper;

    // 获取Aop代理对象
    private OrderService self() {
        return (OrderService) AopContext.currentProxy();
    }

    // 没有开启事务,正常跨数据源调用
    public void method01() {
        // 查询订单
        OrderEntity order = orderMapper.selectById(1);
        System.out.println(order);
        // 查询用户
        UserEntity user = userMapper.selectById(1);
        System.out.println(user);
    }

    // 测试失败的原因: Spring开启事务的时候会获取数据源(Primary)并且获取Connection:两个Mapper使用同一个数据源
    @Transactional
    public void method02() {
        // 查询订单
        OrderEntity order = orderMapper.selectById(1);
        System.out.println(order);
        // 查询用户
        UserEntity user = userMapper.selectById(1);
        System.out.println(user);
    }

    // 原因同2,尽量是两个事务,但是获取的数据源都死活Primary
    public void method03() {
        // 查询订单
        self().method031();
        // 查询用户
        self().method032();
    }

    @Transactional
    public void method031() {
        OrderEntity order = orderMapper.selectById(1);
        System.out.println(order);
    }

    @Transactional
    public void method032() {
        UserEntity user = userMapper.selectById(1);
        System.out.println(user);
    }

    // 在 Spring 事务机制中，在一个事务执行完成后，会将事务信息和当前线程解绑
    // @DS 注解 获取对应的数据源
    public void method04() {
        // 查询订单
        self().method041();
        // 查询用户
        self().method042();
    }

    @Transactional
    @DS(Constant.DATASOURCE_TWO)
    public void method041() {
        OrderEntity order = orderMapper.selectById(1);
        System.out.println(order);
    }

    @Transactional
    @DS(Constant.DATASOURCE_ONE)
    public void method042() {
        UserEntity user = userMapper.selectById(1);
        System.out.println(user);
    }

    // 同一个事务中如何切换数据源: 造成的问题:分布式事务的一致性
    @Transactional
    @DS(Constant.DATASOURCE_TWO)
    public void method05() {
        // 查询订单
        OrderEntity order = orderMapper.selectById(1);
        System.out.println(order);
        // 查询用户
        self().method052();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @DS(Constant.DATASOURCE_ONE)
    public void method052() {
        UserEntity user = userMapper.selectById(1);
        System.out.println(user);
    }
}

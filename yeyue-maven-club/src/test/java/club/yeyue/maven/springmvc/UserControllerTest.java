package club.yeyue.maven.springmvc;

import club.yeyue.maven.YeyueMavenClubApplication;
import club.yeyue.maven.model.GenderEnum;
import club.yeyue.maven.springmvc.model.UserVO;
import club.yeyue.maven.util.JacksonUtils;
import club.yeyue.maven.util.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;

/**
 * @author fred
 * @date 2021-07-20 09:37
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {YeyueMavenClubApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(SpringBeanUtils.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Resource
    MockMvc mockMvc;

    @Test
    public void createTest() throws Exception {
        UserVO vo = new UserVO().setAge(11).setGender(GenderEnum.男).setUsername("夜月");
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/yeyue/user/create")
                .contentType(MediaType.APPLICATION_JSON).content(JacksonUtils.toJsonString(vo)));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andDo(MockMvcResultHandlers.print());
    }
}

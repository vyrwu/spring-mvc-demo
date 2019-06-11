package com.example.vyrwu.agillicDemoMvn;


import com.example.vyrwu.agillicDemoMvn.Account.Account;
import com.example.vyrwu.agillicDemoMvn.Account.AccountService;
import com.example.vyrwu.agillicDemoMvn.Bookmark.BookmarkService;
import com.example.vyrwu.agillicDemoMvn.JsonRequests.JsonIDRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class AccountControllerTest {


    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Account account_one;

    private Account account_two;


    @Autowired
    private AccountService accountService;

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    // helper method for translating Java Objects into JSON
    private String json(Object o) throws IOException {
        MockHttpOutputMessage mhom = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mhom);

        return mhom.getBodyAsString();
    }

    @Autowired
    public void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.stream(converters).filter(
                httpMessageConverter -> httpMessageConverter instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("JSON message must not be null", mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        bookmarkService.clear();
        accountService.clear();
        account_one = accountService.save(new Account("test_username_01", "test_password_01"));
        account_two = accountService.save(new Account("test_username_02", "test_password_02"));
    }

    @Test // --- GET /users
    public void readAllUsers() {
        try {
            mockMvc.perform(get("/users"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json;charset=UTF-8"))
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].id", is(account_one.getId().intValue())))
                    .andExpect(jsonPath("$[0].username", is(account_one.getUsername())))
                    .andExpect(jsonPath("$[1].id", is(account_two.getId().intValue())))
                    .andExpect(jsonPath("$[1].username", is(account_two.getUsername())));
        } catch (Exception e) {
            System.err.println("readAllUsers() test encountered error! ");
            e.printStackTrace();
        }
    }

    @Test // --- POST /users in:application/json st:{username:[String arg], password:[String arg]}
    public void addUser()  {
        Account account = new Account("test_added_username", "test_added_password");
        try {
            String requestBody = json(account);
            mockMvc.perform(post("/users")
                    .content(requestBody)
                    .contentType("application/json;charset=UTF-8"))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType("application/json;charset=UTF-8"))
                    .andExpect(jsonPath("$.username", is(account.getUsername())));
        } catch (Exception e) {
            System.err.println("addUser() test encountered error");
            e.printStackTrace();
        }
    }

    @Test // --- DELETE /users in:application/json st:{ids:[long[] args}
    public void deleteUserByJsonIDRequest() {
        try {
            long id = account_one.getId();
            String requestBody = "{\"ids\":["+id+"]}";
            mockMvc.perform(delete("/users")
                    .content(requestBody)
                    .contentType("application/json;charset=UTF-8"))
                    .andExpect(status().isNoContent());
            if (accountService.contains(id)) {
                System.err.println("User is still here!");
                throw new Exception();
            }
        } catch (Exception e) {
            System.err.println("deleteUserByJsonIDRequest() test encountered error");
            e.printStackTrace();
        }
    }

    @Test // --- PUT /users in:application/json, st:{username:[String arg], password:[String arg]}
    public void updatePassword() {
        Account account = new Account(account_one.getUsername(), "test_new_password");
        try {
            String requestBody = json(account);
            mockMvc.perform(put("/users")
                    .content(requestBody)
                    .contentType("application/json;charset=UTF-8"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            System.err.println("updatePassword() test encountered error");
            e.printStackTrace();
        }
    }

//    @Test // --- PUT /users in:application/json, st:{username:[String arg], password:[String arg]}
//    public void updateUsernameAndPassword() {
//        Account account = new Account("test_username", "test_password");
//        try {
//            String requestBody = json(account);
//            mockMvc.perform(put("/users")
//            .content(requestBody)
//            .content("application/json;charset=UTF-8"))
//                    .andExpect()
//
//        } catch (Exception e) {
//            System.err.println("updateAccountA() test encountered error");
//            e.printStackTrace();
//        }
//    }

}

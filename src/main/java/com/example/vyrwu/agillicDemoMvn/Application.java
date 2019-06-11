package com.example.vyrwu.agillicDemoMvn;

import com.example.vyrwu.agillicDemoMvn.Account.Account;
import com.example.vyrwu.agillicDemoMvn.Account.AccountService;
import com.example.vyrwu.agillicDemoMvn.Bookmark.Bookmark;
import com.example.vyrwu.agillicDemoMvn.Bookmark.BookmarkService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication // = @Configuration + @EnableAutoConfiguration + @ComponentScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // CommandLineRunner - Interface indicating that a bean should run when contained in SpringApplication.
    // Order with Ordered or @Order
    @Bean
    CommandLineRunner init(AccountService accountService, BookmarkService bookmarkService) {
        return (evt) -> Arrays.asList(
                "jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
                .forEach(
                        a -> {
                            Account account = accountService.save(new Account(a, "password"));
                            bookmarkService.save(new Bookmark(account, "http://bookmark.com/1/" + a, "A description"));
                            bookmarkService.save(new Bookmark(account, "http://bookmark.com/2/" + a, "A description"));
                        }
                );
    }
}

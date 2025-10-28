package oit.is.hogeyama.hogehoge.team8_lec05.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Sample3AuthConfiguration {
  /**
   * 認可処理に関する設定（認証されたユーザがどこにアクセスできるか）
   *
   * @param http
   * @return
   * @throws Exception
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin(login -> login
        .permitAll())
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")) // ログアウト後に / にリダイレクト
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("**").authenticated() // /sample3/以下は認証済みであること
            .requestMatchers("/sample4/**").authenticated() // /sample4/以下は認証済みであること
            .anyRequest().permitAll()) // 上記以外は全員アクセス可能
        .csrf(csrf -> csrf
            .ignoringRequestMatchers("/h2-console/*", "/sample2*/**")) // sample2用にCSRF対策を無効化
        .headers(headers -> headers
            .frameOptions(frameOptions -> frameOptions
                .sameOrigin()));
    return http.build();
  }

  /**
   * 認証処理に関する設定（誰がどのようなロールでログインできるか）
   *
   * @return
   */
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {

    // ユーザ名，パスワード，ロールを指定してbuildする
    // このときパスワードはBCryptでハッシュ化されているため，{bcrypt}とつける
    // ハッシュ化せずに平文でパスワードを指定する場合は{noop}をつける
    // user1/p@ss,user2/p@ss,admin/p@ss

    UserDetails user1 = User.withUsername("user1")
        .password("{bcrypt}$2y$05$/gJasA.2iXShXn1DbIXsVOaB3Pl9e45loZvU5aw1r0HLyoitv.PHy").roles("USER").build();
    UserDetails user2 = User.withUsername("user2")
        .password("{bcrypt}$2y$05$dhQ9hyYTM84zm2KcL9zr1.9C8uxq3jQQs9LBkG2pK.iGURey8Mzia").roles("ADMIN").build();
    UserDetails customer1 = User.withUsername("customer1")
        .password("{bcrypt}$2y$05$PtfYXj.DVWggU9LGQA9CQ.DZEWzYR3t5az90HOPwYa/apFFIpO9O2").roles("CUSTOMER").build();
    UserDetails customer2 = User.withUsername("customer2")
        .password("{bcrypt}$2y$05$VDbQY1E1LwvBU0E9stgKnuzg7kzo548ikfi/NTPDv/nrARzAByybe").roles("CUSTOMER").build();
    UserDetails seller = User.withUsername("seller")
        .password("{bcrypt}$2y$05$N3pJCjMGSuppJyS.7XB3fOOMnmcfRngjRWZ0TDHhf0zOfEp9nILkq").roles("SELLER").build();

    // 生成したユーザをImMemoryUserDetailsManagerに渡す（いくつでも良い）
    return new InMemoryUserDetailsManager(user1, user2,customer1,customer2,seller);
  }

}

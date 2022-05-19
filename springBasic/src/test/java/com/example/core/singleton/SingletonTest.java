package com.example.core.singleton;

import com.example.core.AppConfig;
import com.example.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        //1. 조회: 호출할때마다 객체를 생성
        MemberService memberService = appConfig.memberService();

        //2. 조회: 호출 할때마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        //참조값이 다른것을 확인
        System.out.println("memberService = " + memberService);
        System.out.println("memberService1 = " + memberService1);

        //memberService != memberService1
        assertThat(memberService).isNotSameAs(memberService1);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    public void singletonServiceTest() {
        //private으로 생성자를 막아두었다. 컴파일 오류가 발생한다.
        //new SingletonService();

        //1. 조회: 호출할 때마다 같은 객체를 반환
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        // singletonService1 == singletonService2
        assertThat(singletonService1).isSameAs(singletonService2);

        singletonService1.logic();


    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        //1. 조회: 호출할 때 마다 같은 객체를 반환
        MemberService memberService1 = applicationContext.getBean("memberService",MemberService.class);

        //2. 조회: 호출할 때 마다 같은 객체를 반환
        MemberService memberService2 = applicationContext.getBean("memberService", MemberService.class);

        //참조값이 같은 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);


        //memberService1 == memberServiec2
        assertThat(memberService1).isSameAs(memberService2);

        //싱글톤 방식의 주의점
        // 싱글톤 패턴이든,스프링 같은 싱글톤 컨테이너를 사용하든,갹채 인스턴스를 하나만 생성해서 공유하는
        // 싱글톤 방식은 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 떄문에 싱글톤 객체는 상태를 stateful하게 설계
        // 하면 안된다.
        // 무상태(stateless)로 설계


    }
}

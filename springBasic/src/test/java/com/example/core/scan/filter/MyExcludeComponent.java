package com.example.core.scan.filter;

import java.lang.annotation.*;



//타입이라고 하면 클래스레벨에 붙음
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}



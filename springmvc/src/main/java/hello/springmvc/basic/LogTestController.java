package hello.springmvc.basic;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogTestController {

//    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        //자바 언어특징
        // + 가 들어가면 연산을 진행하고 값을 가지고 있기때문에 필요없는 리소스를 낭비할수 있다.
        // log.trace("trace log= " + name);
        // 로그 사용시 장점
        // 특히 파일로 남길때는 일별,특정 용량에 따라 로그를 분할하는 것도 가능하다.
        // 성능도 system.out보다 좋다. 실무에서는 로그를 사용해야한다.
        log.trace("trace log={}",name);
        log.debug("debug log={}",name);
        log.info(" info log={}" ,name);
        log.warn(" warn log={}",name);
        log.error("error log={}",name);

        return "ok";

    }

}

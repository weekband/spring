package hello.servlet.web.frontController.v5;


import hello.servlet.web.frontController.v3.controller.MemberFromControllerV3;
import hello.servlet.web.frontController.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontController.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontController.v4.controller.MemberFromControllerV4;
import hello.servlet.web.frontController.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontController.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontController.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontController.v5.adapter.ControllerV4HandlerAdapter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdapterConfig {

    private static final AdapterConfig adapter = new AdapterConfig();
    private final Map<String ,Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();
    public static AdapterConfig getAdapter() {
        return adapter;
    }

    private AdapterConfig(){
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    public Map<String, Object> getHandlerMappingMap() {
        return handlerMappingMap;
    }
    public List<MyHandlerAdapter> getHandlerAdapters() {
        return handlerAdapters;
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form",new MemberFromControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members",new MemberListControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save",new MemberSaveControllerV3());

        //v4 추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form",new MemberFromControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members",new MemberListControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save",new MemberSaveControllerV4());

    }
    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

}

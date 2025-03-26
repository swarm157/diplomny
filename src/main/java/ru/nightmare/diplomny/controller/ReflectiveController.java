package ru.nightmare.diplomny.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nightmare.diplomny.service.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/alter")
public class ReflectiveController {
    private final Gson gson = new Gson();
    private final Map<String, Object> objectRegistry = new HashMap<>();

    @Autowired
    TestAnswerRewardService testAnswerRewardService;
    @Autowired
    TestAnswerService testAnswerService;
    @Autowired
    TestQuestionService testQuestionService;
    @Autowired
    TestInstanceRedirectionService testInstanceRedirectionService;
    @Autowired
    UserService userService;
    @Autowired
    TestService testService;
    @Autowired
    TestUserService testUserService;
    @Autowired
    TestResultService testResultService;
    @Autowired
    TestUserAnswerService testUserAnswerService;
    @Autowired
    TestParameterService testParameterService;
    @Autowired
    UserStateService userStateService;
    @Autowired
    UserPointerService userPointerService;




    @PostConstruct
    public void init() {
        // Call the specified function after autowiring
        objectRegistry.put("testAnswerRewardService", testAnswerRewardService);
        objectRegistry.put("testAnswerService", testAnswerService);
        objectRegistry.put("testQuestionService", testQuestionService);
        objectRegistry.put("testInstanceRedirectionService", testInstanceRedirectionService);
        objectRegistry.put("testUserService", testUserService);
        objectRegistry.put("testService", testService);
        objectRegistry.put("testResultService", testResultService);
        objectRegistry.put("testUserAnswerService", testUserAnswerService);
        objectRegistry.put("testParameterService", testParameterService);
        objectRegistry.put("userStateService", userStateService);
        objectRegistry.put("userPointerService", userPointerService);
    }

    @PostMapping("/{objectName}/{methodName}")
    public String invokeMethod(@PathVariable String objectName,
                               @PathVariable String methodName,
                               @RequestBody String jsonParams) {
        Object targetObject = objectRegistry.get(objectName);
        if (targetObject == null) {
            return "Object not found";
        }

        try {
            Method method = findMethod(targetObject.getClass(), methodName);
            if (method == null) {
                return "Method not found";
            }

            Parameter[] parameters = method.getParameters();
            Object[] params = parseParams(jsonParams, parameters);
            Object result = method.invoke(targetObject, params);
            return gson.toJson(result);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private Method findMethod(Class<?> clazz, String methodName) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    private Object[] parseParams(String jsonParams, Parameter[] parameters) {
        Object[] params = new Object[parameters.length];
        Map<String, Object> paramMap = gson.fromJson(jsonParams, Map.class);

        for (int i = 0; i < parameters.length; i++) {
            String paramName = parameters[i].getName(); // Get the parameter name
            Object value = paramMap.get(paramName);
            if (value != null) {
                params[i] = gson.fromJson(gson.toJson(value), parameters[i].getType());
            } else {
                params[i] = null; // Handle null values if necessary
            }
        }
        return params;
    }
}

package com.demo.auth.entity.base;

import com.demo.auth.util.AppUtil;
import com.demo.auth.util.AuthorizeUtil;
import com.demo.auth.util.DateUtil;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Date;

@Log4j2
@Component
public class EntityListener {
    @Autowired
    private AuthorizeUtil authorizeUtil;

    @PrePersist
    public void prePersistFunction(Object object) {
        String user = null;
        String progId = null;
        Date currentDate = DateUtil.getCurrentDate();
        user = authorizeUtil.getUsername();
        if (user == null) {
            user = progId;
        }
        this.assignValueToCommonFields(object, user, progId, currentDate, "CREATE");
    }

    @PreUpdate
    public void preUpdateFunction(Object object) {
        String user = null;
        String progId = null;
        Date currentDate = DateUtil.getCurrentDate();
        user = authorizeUtil.getUsername();
        this.assignValueToCommonFields(object, user, progId, currentDate, "UPDATE");
    }

    @PostRemove
    public void postRemoveFunction(Object object) {
        log.info("==PreRemove===");
    }

    @PreRemove
    public void preRemoveFunction(Object object) {
        log.info("==PreRemove===");
    }

    @SneakyThrows
    private void assignValueToCommonFields(Object arg, String user, String progId, Date currentDate, String status) {

        if (status.equals("CREATE")) {
            BeanUtils.setProperty(arg, "createdBy", user);
            BeanUtils.setProperty(arg, "createdDate", currentDate);
        }else{
            BeanUtils.setProperty(arg, "updatedBy", user);
            BeanUtils.setProperty(arg, "updatedDate", currentDate);
        }

        Class<?> cls = arg.getClass();
        for (Field field : cls.getDeclaredFields()) {
            
            Field strField = ReflectionUtils.findField(cls, field.getName());
            if (strField.getType().equals(String.class)) {

                strField.setAccessible(true);
                Object value = ReflectionUtils.getField(strField, arg);

                if (AppUtil.isNotNull(value) && AppUtil.isEmpty(value.toString())) {
                    ReflectionUtils.makeAccessible(strField); //set null when emptyString
                    ReflectionUtils.setField(strField, arg, null);
                }
            }
        }
    }
}

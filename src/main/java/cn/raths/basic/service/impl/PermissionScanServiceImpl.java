package cn.raths.basic.service.impl;


import cn.raths.basic.annotation.PreAuthorize;
import cn.raths.basic.service.IPermissionScanService;
import cn.raths.basic.utils.Classutil;
import cn.raths.sys.domain.Permission;
import cn.raths.sys.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @Description: 权限初始化类
* @Author: Mr.She
* @Version: 1.0
* @Date:  2022/6/23 22:37  
*/
@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PermissionScanServiceImpl implements IPermissionScanService {


  // 扫描路径包前缀
  private static final String  PKG_PREFIX   = "cn.raths.";
  // 扫描路径包后缀
  private static final String  PKG_SUFFIX   = ".controller";

  @Autowired
  private PermissionMapper permissionMapper;

  /** 
    * @Title: scanPermission
    * @Description: 权限初始化业务方法
    * @Author: Mr.She
    * @Version: 1.0
    * @Date:  2022/6/23 22:37  
    * @Parameters:  
    * @Return void
    */
  @Override
  @Transactional
  public void scanPermission() {
    //获取org.yaosang下面所有的模块目录
    String path = this.getClass().getResource("/").getPath() + "/cn/raths/";
    File file = new File(path);
    File[] files = file.listFiles(new FileFilter() {
      @Override
      public boolean accept(File file) {
        return file.isDirectory();
      }
    });

    //获取org.yaosang.*.web.controller里面所有的类
    Set<Class> clazzes = new HashSet<>();
    for (File fileTmp : files) {
      System.out.println(fileTmp.getName());
      clazzes.addAll(Classutil.getClasses(PKG_PREFIX+fileTmp.getName()+PKG_SUFFIX));
    }

    for (Class clazz : clazzes) {
      Method[] methods = clazz.getMethods();
      if (methods==null || methods.length<1)
        return;
      for (Method method : methods) {
        String uri = getUri(clazz, method);
        try {
          PreAuthorize preAuthorizeAnno = method.getAnnotation(PreAuthorize.class);
          if (preAuthorizeAnno==null)
            continue;
          String name = preAuthorizeAnno.name();
          String sn = preAuthorizeAnno.sn();
          Permission permissionTmp = permissionMapper.loadBySn(sn);
          //如果不存在就添加
          if (permissionTmp == null) {
            Permission permission = new Permission();
            permission.setName(name);
            permission.setSn(sn);
            permission.setUrl(uri);
            permissionMapper.save(permission);
          }else{
            //如果不存在就修改
            permissionTmp.setName(name);
            permissionTmp.setSn(sn);
            permissionTmp.setUrl(uri);
            permissionMapper.update(permissionTmp);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  private String getUri(Class clazz, Method method) {
    String classPath = "";
    Annotation annotation = clazz.getAnnotation(RequestMapping.class);
    if (annotation!=null){
      RequestMapping requestMapping = (RequestMapping) annotation;
      String[] values = requestMapping.value();
      if(values!=null&&values.length>0){
        classPath = values[0];
        if (!"".equals(classPath)&&!classPath.startsWith("/"))
          classPath="/"+classPath;
      }
    }
    GetMapping getMapping =  method.getAnnotation(GetMapping.class);
    String methodPath = "";
    if (getMapping!=null){
      String[] values = getMapping.value();
      if(values!=null&&values.length>0){
        methodPath = values[0];
        if (!"".equals(methodPath)&&!methodPath.startsWith("/"))
          methodPath="/"+methodPath;
      }
    }
    PostMapping postMapping =  method.getAnnotation(PostMapping.class);
    if (postMapping!=null){
      String[] values = postMapping.value();
      if(values!=null&&values.length>0){
        methodPath = values[0];
        if (!"".equals(methodPath)&&!methodPath.startsWith("/"))
          methodPath="/"+methodPath;
      }
    }
    DeleteMapping deleteMapping =  method.getAnnotation(DeleteMapping.class);
    if (deleteMapping!=null){
      String[] values = deleteMapping.value();
      if(values!=null&&values.length>0){
        methodPath = values[0];
        if (!"".equals(methodPath)&&!methodPath.startsWith("/"))
          methodPath="/"+methodPath;
      }
    }
    PutMapping putMapping =  method.getAnnotation(PutMapping.class);
    if (putMapping!=null){
      String[] values = putMapping.value();
      if(values!=null&&values.length>0){
        methodPath = values[0];
        if (!"".equals(methodPath)&&!methodPath.startsWith("/"))
          methodPath="/"+methodPath;
      }

    }
    PatchMapping patchMapping = method.getAnnotation(PatchMapping.class);
    if (patchMapping!=null){
      String[] values = patchMapping.value();
      if(values!=null&&values.length>0){
        methodPath = values[0];
        if (!"".equals(methodPath)&&!methodPath.startsWith("/"))
          methodPath="/"+methodPath;
      }
    }
    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
    if (requestMapping!=null){
      String[] values = requestMapping.value();
      if(values!=null&&values.length>0){
        methodPath = values[0];
        if (!"".equals(methodPath)&&!methodPath.startsWith("/"))
          methodPath="/"+methodPath;
      }
    }
    return classPath+methodPath;
  }

  private String getPermissionSn(String value) {
    String regex = "\\[(.*?)]";
    Pattern p = Pattern.compile("(?<=\\()[^\\)]+");
    Matcher m = p.matcher(value);
    String permissionSn =null;
    if (m.find()){
      permissionSn= m.group(0).substring(1, m.group().length()-1);
    }
    return permissionSn;
  }

}
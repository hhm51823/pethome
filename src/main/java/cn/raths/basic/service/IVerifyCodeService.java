package cn.raths.basic.service;

import org.springframework.stereotype.Service;


public interface IVerifyCodeService {

    String getImageCodeBase64Str(String imageCodeKey);
}

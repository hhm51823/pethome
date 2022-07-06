package cn.raths.user.service;

import cn.raths.basic.dto.EmailRegisterDto;
import cn.raths.basic.dto.RegisterDto;
import cn.raths.user.domain.User;
import cn.raths.basic.service.IBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-06
 */
public interface IUserService extends IBaseService<User> {

    void registerPhone(RegisterDto registerDto);

    void registerEmail(EmailRegisterDto emailRegisterDto);
}

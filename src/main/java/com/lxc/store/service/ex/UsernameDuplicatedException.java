package com.lxc.store.service.ex;

/** 用户名被占用的异常
 * @author xc
 * @date 2022/5/23 20:57
 */
public class UsernameDuplicatedException extends ServiceException {
     //alt + insert ---- override methods...

    public UsernameDuplicatedException() {
        super();
    }

    public UsernameDuplicatedException(String message) {
        super(message);
    }

    public UsernameDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameDuplicatedException(Throwable cause) {
        super(cause);
    }

    protected UsernameDuplicatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package com.kdl.common.framework.exception;

public abstract class FrameworkException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    


    public String getMessage() {
        return super.getMessage();
    }

    /**
     * 获取异常错误码
     * @return
     */
    public abstract Integer getCode(); 
   
    /**
     * 获取异常所在线程对应的请求唯一ID
     * @return
     */
    
	public abstract String reqUid();
	
	
	protected FrameworkException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	protected FrameworkException(String message, Throwable cause) {
		super(message, cause);
	}
	
	protected FrameworkException(String message) {
		super(message);
	}
}

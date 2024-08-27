package com.alibou.library.handler;


    public class BusinessException extends RuntimeException {

        private final BusinessErrorCodes errorCode;

        public BusinessException(BusinessErrorCodes errorCode) {
            super(errorCode.getDescription());
            this.errorCode = errorCode;
        }

        public BusinessErrorCodes getErrorCode() {
            return errorCode;
        }
    }


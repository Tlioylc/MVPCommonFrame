package com.tlioylc.client.bean;

public class ErrorData {
    /**
     * message : 请先支付未完成订单
     * status : 408
     */

    private String message;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * error : {"status":422,"error":"FIELDS_VALIDATION_ERROR","description":"One or more fields raised validation errors.","fields":{"email":"Invalid email address.","password":"Password too short."}}
     */


//    private ErrorBean error;
//
//    public ErrorBean getError() {
//        return error;
//    }
//
//    public void setError(ErrorBean error) {
//        this.error = error;
//    }
//
//    public static class ErrorBean {
//        /**
//         * status : 422
//         * error : FIELDS_VALIDATION_ERROR
//         * description : One or more fields raised validation errors.
//         */
//
//        private int status;
//        private String error;
//        private String description;
//
//        public int getStatus() {
//            return status;
//        }
//
//        public void setStatus(int status) {
//            this.status = status;
//        }
//
//        public String getError() {
//            return error;
//        }
//
//        public void setError(String error) {
//            this.error = error;
//        }
//
//        public String getDescription() {
//            return description;
//        }
//
//        public void setDescription(String description) {
//            this.description = description;
//        }
//    }
}

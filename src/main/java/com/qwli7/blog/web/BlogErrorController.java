package com.qwli7.blog.web;

//@Controller
//public class BlogErrorController extends BasicErrorController {
//
//    private final ErrorAttributes errorAttributes;
//
//
//    public BlogErrorController(ErrorAttributes errorAttributes) {
//        super(errorAttributes, new ErrorProperties());
//        this.errorAttributes = errorAttributes;
//    }
//
//
//    @Override
//    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
//        HttpStatus status = this.getStatus(request);
//        if(status == HttpStatus.NO_CONTENT) {
//            return new ResponseEntity<>(status);
//        }
//        WebRequest webRequest = new ServletWebRequest(request);
//        Map<String, Object> errors = this.errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
//        return new ResponseEntity<>(errors, status);
//    }
//
//    @Override
//    protected HttpStatus getStatus(HttpServletRequest request) {
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        if (statusCode == null) {
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        } else {
//            try {
//                return HttpStatus.valueOf(statusCode);
//            } catch (Exception var4) {
//                return HttpStatus.INTERNAL_SERVER_ERROR;
//            }
//        }
//    }
//
//    @Override
//    public String getErrorPath() {
//        return super.getErrorPath();
//    }
//}

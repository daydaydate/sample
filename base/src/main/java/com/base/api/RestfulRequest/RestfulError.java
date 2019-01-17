package com.base.api.RestfulRequest;

/**
 * Created by Administrator on 2017/5/5.
 */
public class RestfulError {

    /**
     * Message : 出现错误。
     * ExceptionMessage : 帖子已被删除或没有该帖子！
     * ExceptionType : FHT.Core.Exceptions.TopicPost.TopicPostNotFoundException
     * StackTrace :    在 FHT.Core.Queries.QueryService.TopicPostQuery...
     * Code : TopicPostNotFoundException
     * ErrorDetail : 帖子6已被删除或没有该帖子！
     */

    private String Message;
    private String ExceptionMessage;
    private String ExceptionType;
    private String StackTrace;
    private String Code;
    private String ErrorDetail;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getExceptionMessage() {
        return ExceptionMessage;
    }

    public void setExceptionMessage(String ExceptionMessage) {
        this.ExceptionMessage = ExceptionMessage;
    }

    public String getExceptionType() {
        return ExceptionType;
    }

    public void setExceptionType(String ExceptionType) {
        this.ExceptionType = ExceptionType;
    }

    public String getStackTrace() {
        return StackTrace;
    }

    public void setStackTrace(String StackTrace) {
        this.StackTrace = StackTrace;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getErrorDetail() {
        return ErrorDetail;
    }

    public void setErrorDetail(String ErrorDetail) {
        this.ErrorDetail = ErrorDetail;
    }

    @Override
    public String toString() {
        return "RestfulError{" +
                "Message='" + Message + '\'' +
                ", ExceptionMessage='" + ExceptionMessage + '\'' +
                ", ExceptionType='" + ExceptionType + '\'' +
                ", StackTrace='" + StackTrace + '\'' +
                ", Code='" + Code + '\'' +
                ", ErrorDetail='" + ErrorDetail + '\'' +
                '}';
    }
}

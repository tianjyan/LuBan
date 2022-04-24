package org.tianjyan.luban.host.infrastructure.abs;

public interface ILog extends ICore {
    void v(String msg);
    void d(String msg);
    void i(String msg);
    void w(String msg);
    void e(String msg);
    void json(String msg);
    void xml(String msg);
}

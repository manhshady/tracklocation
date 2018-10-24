package vn.softlink.core.util.handler;

/**
 * *******************************************************************
 *
 * @Project: Core
 * @Created: Huy QV 2018/10/09
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
public interface DataReceiver<T> {
    void onDataReceived(T data);
}

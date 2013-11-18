package cn.com.chinabank.app1.service.impl;

import cn.com.chinabank.shared.listener.CallbackListener;
import cn.com.chinabank.shared.service.CallbackService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 11/13/13
 * Time: 5:22 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("callbackService")
public class CallbackServiceImpl implements CallbackService {

    private final Map<String, CallbackListener> listeners = new ConcurrentHashMap<String, CallbackListener>();

    public CallbackServiceImpl() {
       /* Thread t = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    try {
                        for(Map.Entry<String, CallbackListener> entry : listeners.entrySet()){
                            try {
                                entry.getValue().changed(getChanged(entry.getKey()));
                            } catch (Throwable t) {
                                listeners.remove(entry.getKey());
                            }
                        }
                        Thread.sleep(5000); // 定时触发变更通知
                    } catch (Throwable t) { // 防御容错
                        t.printStackTrace();
                    }
                }
            }
        });
        t.setDaemon(true);
        t.start();*/
    }


    public Object addListener(String key, CallbackListener listener)  {
        listeners.put(key, listener);
        listener.changed(getChanged(key)); // 发送变更通知
        if (key.isEmpty())
            throw new RuntimeException("sf");
        return "listener added";
    }

    private String getChanged(String key) {
        return "Changed: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}

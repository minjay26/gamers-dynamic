package org.minjay.gamers.dynamic.client;

import java.util.List;
import java.util.Map;

public interface AccountClient {

    void publishCreateDynamicAction(Long userId);

    List<Map<String,Object>> getFocus(Long userId);
}

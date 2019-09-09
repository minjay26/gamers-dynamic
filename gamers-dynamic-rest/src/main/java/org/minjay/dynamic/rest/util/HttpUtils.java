package org.minjay.dynamic.rest.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils {

    public static final String getIpAddr(final HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String ipString = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getRemoteAddr();
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ipString.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ipString = str;
                break;
            }
        }
        return ipString;
    }
}

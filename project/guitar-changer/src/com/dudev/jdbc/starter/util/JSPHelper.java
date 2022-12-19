package com.dudev.jdbc.starter.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JSPHelper {

    private static final String FORMAT = "/WEB-INF/jsp/%s.jsp";

    public static String getPath(String JSPName) {
        return FORMAT.formatted(JSPName);
    }


}

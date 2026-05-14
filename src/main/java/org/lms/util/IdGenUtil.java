package org.lms.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class IdGenUtil {

    public static String genUserId() {
        String id = genId();

        return "U" + id;
    }

    public static String genLibraryId() {
        String id = genId();

        return "L" + id;
    }

    public static String genGroupId() {
        String id = genId();

        return "G" + id;
    }

    public static String genId() {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);

        return String.valueOf(snowflake.nextId());
    }

}

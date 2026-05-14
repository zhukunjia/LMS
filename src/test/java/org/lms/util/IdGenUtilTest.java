package org.lms.util;

import org.junit.Test;

public class IdGenUtilTest {
    @Test
    public void testGenId(){
        for (int i = 0; i < 2; i++) {
            System.out.println(IdGenUtil.genId());
        }
    }
}

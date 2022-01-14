package org.springframework.samples.petclinic.recoveryroom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class Test6 {
    @Autowired
    RecoveryRoomService rs;

    @Test
    public void test6(){
        validateFindRoomTypeByName();
        validateNotFoundRoomTypeByName();
    }



    public void validateFindRoomTypeByName(){
        String typeName="room";
        RecoveryRoomType roomType=rs.getRecoveryRoomType(typeName);
        assertNotNull(roomType);
        assertEquals(typeName,roomType.getName());
    }

    
    public void validateNotFoundRoomTypeByName(){
        String typeName="This is not a valid roomType name";
        RecoveryRoomType roomType=rs.getRecoveryRoomType(typeName);
        assertNull(roomType);
    }

}

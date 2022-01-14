package org.springframework.samples.petclinic.recoveryroom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class Test7 {

    @Autowired
    RecoveryRoomTypeFormatter formatter;

    @Test
    public void test7(){
        testFormatterIsInjected();
        testFormatterObject2String();
        testFormatterString2Object();
        testFormatterString2ObjectNotFound();
    }


    
    public void testFormatterIsInjected(){
        assertNotNull(formatter);
    }

    
    public void testFormatterObject2String(){
        RecoveryRoomType roomType=new RecoveryRoomType();
        roomType.setName("Prueba");
        String result=formatter.print(roomType, null);
        assertEquals("Prueba",result);
    }

    
    public void testFormatterString2Object(){
        String name="box";
        RecoveryRoomType roomType;
        try {
            roomType = formatter.parse(name, null);
            assertNotNull(roomType);
            assertEquals(roomType.getName(),name);
        } catch (ParseException e) {           
            e.printStackTrace();
            fail(e.getMessage());
        }
        
    }

    
    public void testFormatterString2ObjectNotFound(){
        String name="This is not a room type";
        assertThrows(ParseException.class, () -> formatter.parse(name, null));          
    }
}

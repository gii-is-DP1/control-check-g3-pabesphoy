package org.springframework.samples.petclinic.recoveryroom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class Test8 {
    @Autowired
    RecoveryRoomService rs;


    @Test
    public void testRoomSizeGreaterThan(){
        int expectedRooms[]={2,1,0};
        double sizes[]={1.0,2.0,10.0};
        double size; 
        int expectedNumberOfRooms;
        List<RecoveryRoom> rooms=null;
        for(int i=0;i<sizes.length;i++){
            expectedNumberOfRooms=expectedRooms[i];
            size=sizes[i];
            rooms=rs.getRecoveryRoomsBiggerThan(size);
            assertEquals(expectedNumberOfRooms,rooms.size(),
                "The number of rooms obtained for a size of "+size+
                " was "+rooms.size()+" but I expected it to be "+expectedNumberOfRooms);
    
        }

    }

}

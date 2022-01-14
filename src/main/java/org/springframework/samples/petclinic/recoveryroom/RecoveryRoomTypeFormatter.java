package org.springframework.samples.petclinic.recoveryroom;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class RecoveryRoomTypeFormatter implements Formatter<RecoveryRoomType>{

    @Autowired
    RecoveryRoomService recoveryRoomService;

    @Override
    public String print(RecoveryRoomType object, Locale locale) {
        return object.name;
    }

    @Override
    public RecoveryRoomType parse(String text, Locale locale) throws ParseException {
        RecoveryRoomType res = recoveryRoomService.getRecoveryRoomType(text);
        if(res == null)
            throw new ParseException("Parsing RecoveringRoomType Exception", 0);
        else
            return res;
    }
    
}

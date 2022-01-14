package org.springframework.samples.petclinic.recoveryroom;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class RecoveryRoomTypeFormatter implements Formatter<RecoveryRoomType>{

    @Override
    public String print(RecoveryRoomType object, Locale locale) {
        return null;
    }

    @Override
    public RecoveryRoomType parse(String text, Locale locale) throws ParseException {
        return null;
    }
    
}

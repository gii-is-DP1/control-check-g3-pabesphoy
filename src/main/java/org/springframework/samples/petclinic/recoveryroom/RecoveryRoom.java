package org.springframework.samples.petclinic.recoveryroom;

import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecoveryRoom {
    Integer id;
    String name;
    double size;
    boolean secure;
    @Transient
    RecoveryRoomType roomType;
}

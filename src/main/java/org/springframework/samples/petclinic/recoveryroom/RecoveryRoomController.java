package org.springframework.samples.petclinic.recoveryroom;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RecoveryRoomController {

    private static final String CREATE_OR_UPDATE_RECOVERY_ROOM_VIEW = "recoveryroom/createOrUpdateRecoveryRoomForm";

    @Autowired
    RecoveryRoomService recoveryRoomService;

    @GetMapping("/recoveryroom/create")
    public String recoveryRoomForm(ModelMap model){

        model.addAttribute("recoveryRoom", new RecoveryRoom());
        model.addAttribute("types", recoveryRoomService.getAllRecoveryRoomTypes());
        return CREATE_OR_UPDATE_RECOVERY_ROOM_VIEW;
    }

    @PostMapping("/recoveryroom/create")
    public String recoveryRoomSave(@Valid RecoveryRoom recoveryRoom, BindingResult bindingResult, ModelMap model){

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().stream().forEach(error -> System.err.println(error));
            model.addAttribute("message", "Error creando recovery room");
            return CREATE_OR_UPDATE_RECOVERY_ROOM_VIEW;
        }else{
            recoveryRoomService.save(recoveryRoom);
            return "welcome";
        }
    }
}

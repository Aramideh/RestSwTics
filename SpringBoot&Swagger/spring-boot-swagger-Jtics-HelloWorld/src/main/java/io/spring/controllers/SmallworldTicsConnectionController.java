package io.spring.controllers;


import io.swagger.annotations.ApiOperation;
import ir.gisbox.SWServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/SmallworldTicsConnection")
public class SmallworldTicsConnectionController {

    private static final Logger logger = LogManager.getLogger("SW Services Controller");
    private static final String JsonErrorMessage =  "{\"Result\":\"false\",\"ResultCode\":\"0\",\"ResultMessage\":\"Error in Java Method\"}" ;// 0 means error
    private static final SWServices SW_SERVICES = new SWServices();

    @RequestMapping(value = "/swShakeHand" , method = RequestMethod.GET ,produces = "application/json")
    @ApiOperation(value = "Smallworld Greetings")
    @CrossOrigin(origins = "*")
    public String Greetings(
            @RequestParam(name = "Name", required = true) String Name
            // @RequestBody String MobileAvlData
    ){
        try {

            String result = SW_SERVICES.HelloWorld(String.valueOf( Name ) );
            System.out.println("result"+result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonErrorMessage;
    }

}

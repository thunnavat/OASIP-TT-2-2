package sit.int221.oasipservice.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.int221.oasipservice.services.EmailServiceImpl;
import sit.int221.oasipservice.entities.EmailDetail;

@RestController
@RequestMapping("/api")
public class EmailController {
    private final EmailServiceImpl emailService;

    public EmailController(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendMail")
    public String sendMail(EmailDetail emailDetail) {
        String status = emailService.sendSimpleMail(emailDetail);
        return status;
    }
}

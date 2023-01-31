package pl.dudios.shopmvn.admin.order.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dudios.shopmvn.admin.order.model.AdminOrder;
import pl.dudios.shopmvn.admin.order.model.AdminOrderStatus;
import pl.dudios.shopmvn.common.mail.EmailClientService;


@Service
@AllArgsConstructor
public class AdminOrderEmailMessage {

    private final EmailClientService emailClientService;

    public static String createMessage(AdminOrder order, AdminOrderStatus newStatus) {
        return "Hi " + order.getFirstName() + " " + order.getLastName() + ","
                + "Your order " + order.getId() + " has been " + newStatus.name().toLowerCase() + "."
                + "Greetings";
    }

    public void notifyClient(AdminOrderStatus newStatus, AdminOrder order) {
        if(newStatus == AdminOrderStatus.PROCESSING){
            sendEmail(order.getEmail(), "Change your order status with ID: "+order.getId()+ "to" +newStatus.getValue(), createMessage(order, newStatus));
        }else if(newStatus == AdminOrderStatus.WAITING_FOR_DELIVERY){
            sendEmail(order.getEmail(), "Change your order status with ID: "+order.getId()+ "to" +newStatus.getValue(), createMessage(order, newStatus));
        }else if(newStatus == AdminOrderStatus.COMPLETED){
            sendEmail(order.getEmail(), "Change your order status with ID: "+order.getId()+ "to" +newStatus.getValue(), createMessage(order, newStatus));
        }else if(newStatus == AdminOrderStatus.REFUND){
            sendEmail(order.getEmail(), "Change your order status with ID: "+order.getId()+ "to" +newStatus.getValue(), createMessage(order, newStatus));
        }
    }

    private void sendEmail(String email, String subject, String content) {
        emailClientService.getSender().sendEmail(email, subject, content);
    }
}

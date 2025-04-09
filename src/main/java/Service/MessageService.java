package Service;

import Model.Message;
import DAO.*;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message postMessage(Message message) {
        if ((!message.getMessage_text().isEmpty()) 
        && message.getMessage_text().length() <= 255
        && accountDAO.getAccountById(message.getPosted_by()) != null) {
            return messageDAO.insertMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessage(int id) {
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessage(int id) {
        return messageDAO.deleteMessageById(id);
    }

    public Message updateMessage(int id, String messageText) {
        if ((!messageText.isEmpty()) 
        && messageText.length() <= 255
        && accountDAO.getAccountById(id) != null) {
            return messageDAO.updateMessageById(id, messageText);
        }
        return null;
    }

    public List<Message> getMessagesByAccount(int account_id) {
        return messageDAO.getMessagesByAccountId(account_id);
    }
}

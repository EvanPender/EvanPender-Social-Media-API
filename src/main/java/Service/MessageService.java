package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message postMessage(Message message) {
        return messageDAO.insertMessage(message);
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
        return messageDAO.updateMessageById(id, messageText);
    }

    public List<Message> getMessagesByAccount(int account_id) {
        return messageDAO.getMessagesByAccountId(account_id);
    }
}

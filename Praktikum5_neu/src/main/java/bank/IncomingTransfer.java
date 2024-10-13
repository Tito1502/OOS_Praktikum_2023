package bank;

import bank.exceptions.TransferPaymentAttributeException;

public class IncomingTransfer extends Transfer {

    public IncomingTransfer(String date, double amount, String description, String sender, String recipient)  throws TransferPaymentAttributeException {
        super(date, amount, description, sender, recipient);
    }
}
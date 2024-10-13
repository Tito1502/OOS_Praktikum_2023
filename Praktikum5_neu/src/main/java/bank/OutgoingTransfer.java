package bank;

import bank.exceptions.TransferPaymentAttributeException;

public class OutgoingTransfer extends Transfer {

    public OutgoingTransfer(String date, double amount, String description, String sender, String recipient) throws TransferPaymentAttributeException {
        super(date, amount, description, sender, recipient);
    }

    @Override
    public double calculate() {
        return amount * -1;
    }
}

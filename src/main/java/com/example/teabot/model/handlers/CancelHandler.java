package com.example.teabot.model.handlers;

class CancelHandler implements FinalStateHandler {
    @Override
    public String question() {
        return "Order canceled. Press '/start' if you want create order";
    }
}

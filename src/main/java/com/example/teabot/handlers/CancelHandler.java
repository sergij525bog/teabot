package com.example.teabot.handlers;

class CancelHandler implements FinalStateHandler {
    @Override
    public String question() {
        return "Order canceled. Press '/start' if you want create order";
    }
}

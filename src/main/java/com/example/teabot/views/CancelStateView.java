package com.example.teabot.views;

class CancelStateView implements FinalStateView {

    @Override
    public String question() {
        return "Order canceled. Press '/start' if you want create order";
    }
}

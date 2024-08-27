package com.example.teabot.views;

class WithoutOrderView implements FinalStateView {

    @Override
    public String question() {
        return """
                You skipped order for yourself but you will make tea for your comrades! You are true hero!
                If you want order tea, please press '/start'
                """;
    }
}

package com.lind.interfaces;

import com.lind.spi.Provider;

public class PrinterProvider implements Provider {
    @Override
    public String login() {
        return "PrinterProvider run 1243...";
    }
}

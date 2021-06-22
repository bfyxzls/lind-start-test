package com.lind.interfaces;

import com.lind.spi.ProviderFactory;

public class PrinterProviderFactory implements ProviderFactory<PrinterProvider> {

    @Override
    public PrinterProvider create() {
        return new PrinterProvider();
    }

    @Override
    public String getId() {
        return "PrinterProvider";
    }

}

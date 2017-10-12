package co.com.lafemmeapp.lafemmeapp.mappers;

import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;

/**
 * Created by oscargallon on 5/3/17.
 */

public class ServicePriceTextViewValidatorMapper implements ITextViewValidatorMapper {
    private static final ServicePriceTextViewValidatorMapper ourInstance = new ServicePriceTextViewValidatorMapper();

    public static ServicePriceTextViewValidatorMapper getInstance() {
        return ourInstance;
    }

    private ServicePriceTextViewValidatorMapper() {
    }

    @Override
    public TextChangedMappedEvent apply(TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
        return new TextChangedMappedEvent(textViewTextChangeEvent.view().getId(),
                !textViewTextChangeEvent.text().toString().equals("$0"));
    }
}

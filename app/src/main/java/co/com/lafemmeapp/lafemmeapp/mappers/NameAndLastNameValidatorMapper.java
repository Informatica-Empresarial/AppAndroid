package co.com.lafemmeapp.lafemmeapp.mappers;

import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;

/**
 * Created by oscargallon on 5/6/17.
 */

public class NameAndLastNameValidatorMapper implements ITextViewValidatorMapper {

    private final static NameAndLastNameValidatorMapper instance =
            new NameAndLastNameValidatorMapper();

    private NameAndLastNameValidatorMapper() {

    }

    public static NameAndLastNameValidatorMapper getInstance() {
        return instance;
    }

    @Override
    public TextChangedMappedEvent apply(TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
        return new TextChangedMappedEvent(textViewTextChangeEvent.view()
                .getId(), textViewTextChangeEvent.text().length() > 2);
    }
}

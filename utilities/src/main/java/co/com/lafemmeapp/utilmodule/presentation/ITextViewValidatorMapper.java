package co.com.lafemmeapp.utilmodule.presentation;

import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;
import io.reactivex.functions.Function;


/**
 * Created by oscargallon on 4/20/17.
 * This is an interface to control the behaviour for the TextView validations
 */
public interface ITextViewValidatorMapper extends Function<TextViewTextChangeEvent,
        TextChangedMappedEvent> {

    @Override
    TextChangedMappedEvent apply(TextViewTextChangeEvent textViewTextChangeEvent) throws Exception;
}

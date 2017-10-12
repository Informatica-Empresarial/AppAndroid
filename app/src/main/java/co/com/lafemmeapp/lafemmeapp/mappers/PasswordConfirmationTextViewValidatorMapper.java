package co.com.lafemmeapp.lafemmeapp.mappers;

import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;

/**
 * Created by Stephys on 7/05/17.
 */

public class PasswordConfirmationTextViewValidatorMapper implements ITextViewValidatorMapper {
    private static final PasswordConfirmationTextViewValidatorMapper ourInstance = new PasswordConfirmationTextViewValidatorMapper();

    private static String pwdToCheck;

    public static PasswordConfirmationTextViewValidatorMapper getInstance() {
        return ourInstance;
    }

    private PasswordConfirmationTextViewValidatorMapper() {
    }

    @Override
    public TextChangedMappedEvent apply(TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
        return new TextChangedMappedEvent(textViewTextChangeEvent.view()
                .getId(), verifyPwd(pwdToCheck,textViewTextChangeEvent.text().toString()));
    }

    private Boolean verifyPwd(String pwd1, String pwd2){
        if(pwd1 != null){
            return pwd1.equals(pwd2);
        }

        return false;
    }

    public void setPwdToCheck(String pwdToCheck) {
        PasswordConfirmationTextViewValidatorMapper.pwdToCheck = pwdToCheck;
    }
}

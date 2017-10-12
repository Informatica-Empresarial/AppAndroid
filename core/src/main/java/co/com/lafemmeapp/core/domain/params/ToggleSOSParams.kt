package co.com.lafemmeapp.core.domain.params

import android.content.Context
import co.com.lafemmeapp.core.domain.use_cases.session.GetSessionUseCase

/**
 * Created by oscargallon on 6/5/17.
 */
data class ToggleSOSParams(val mGetSessionUseCase: GetSessionUseCase,
                           val mContext: Context)
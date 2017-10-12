package co.com.lafemmeapp.dataprovider.network.entities

/**
 * Created by stephany.berrio on 5/3/17.
 */

class UserRequest(var dni: String?, var firstName: String?, var lastName: String?,
                  var address: String?, var email: String?, var phoneNumber: String?,
                  var password: String?, var city: String?, accountId: String) {

    var accountId: String? = null

    init {
    }
}

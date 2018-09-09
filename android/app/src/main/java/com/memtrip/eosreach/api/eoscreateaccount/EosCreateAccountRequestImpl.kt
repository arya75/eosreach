package com.memtrip.eosreach.api.eoscreateaccount

import com.memtrip.eosreach.api.Result
import com.squareup.moshi.Moshi
import io.reactivex.Single

import javax.inject.Inject

class EosCreateAccountRequestImpl @Inject constructor(
    private val eosCreateAccountApi: EosCreateAccountApi,
    private val moshi: Moshi
) : EosCreateAccountRequest {

    override fun createAccount(
        purchaseId: String,
        accountName: String,
        publicKey: String
    ): Single<Result<CreateAccountReceipt, EosCreateAccountError>> {
        return eosCreateAccountApi.createAccount(CreateAccountRequest(
            purchaseId,
            accountName,
            publicKey
        )).map { response ->
            if (response.isSuccessful) {
                Result(CreateAccountReceipt(response.body()!!.transactionid))
            } else {
                if (response.errorBody() != null) {
                    try {
                        val createAccountError = moshi.adapter(CreateAccountError::class.java)
                            .fromJson(response.errorBody()!!.string())

                        when(createAccountError!!.error) {
                            "PUBLIC_KEY_INVALID_FORMAT" -> {
                                Result<CreateAccountReceipt, EosCreateAccountError>(EosCreateAccountError.FatalError)
                            }
                            "ACCOUNT_NAME_EXISTS" -> {
                                Result<CreateAccountReceipt, EosCreateAccountError>(EosCreateAccountError.AccountNameExists)
                            }
                            else -> {
                                Result<CreateAccountReceipt, EosCreateAccountError>(EosCreateAccountError.GenericError)
                            }
                        }
                        Result<CreateAccountReceipt, EosCreateAccountError>(EosCreateAccountError.GenericError)
                    } catch (e: Exception) {
                        Result<CreateAccountReceipt, EosCreateAccountError>(EosCreateAccountError.GenericError)
                    }
                } else {
                    Result<CreateAccountReceipt, EosCreateAccountError>(EosCreateAccountError.GenericError)
                }
            }
        }
    }
}
/*
Copyright (C) 2018-present memtrip

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.memtrip.eosreach.app.transfer.confirm

import com.memtrip.eos.http.rpc.model.transaction.response.TransactionCommitted
import com.memtrip.eosreach.api.Result
import com.memtrip.eosreach.api.transfer.TransferError
import com.memtrip.eosreach.api.transfer.TransferRequest
import com.memtrip.eosreach.db.account.GetAccountByName
import com.memtrip.eosreach.wallet.EosKeyManager
import io.reactivex.Single
import javax.inject.Inject

class TransferUseCase @Inject internal constructor(
    private val transferRequest: TransferRequest,
    private val getAccountByName: GetAccountByName,
    private val eosKeyManager: EosKeyManager
) {

    fun transfer(
        fromAccount: String,
        toAccount: String,
        quantity: String,
        memo: String
    ): Single<Result<TransactionCommitted, TransferError>> {

        return getAccountByName.select(fromAccount).flatMap { accountEntity ->
            eosKeyManager.getPrivateKey(accountEntity.publicKey).flatMap { privateKey ->
                transferRequest.transfer(
                    accountEntity.accountName,
                    toAccount,
                    quantity,
                    memo,
                    privateKey)
            }.onErrorReturn {
                Result(TransferError(it.message!!))
            }
        }
    }
}
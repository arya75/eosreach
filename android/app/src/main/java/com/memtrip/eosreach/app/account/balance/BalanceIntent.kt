package com.memtrip.eosreach.app.account.balance

import com.memtrip.eosreach.api.balance.ContractAccountBalance
import com.memtrip.eosreach.api.balance.AccountBalanceList
import com.memtrip.mxandroid.MxViewIntent

sealed class BalanceIntent : MxViewIntent {
    data class Init(val accountBalances: AccountBalanceList) : BalanceIntent()
    object Idle : BalanceIntent()
    object NavigateToCreateAccount : BalanceIntent()
    data class NavigateToActions(val balance: ContractAccountBalance) : BalanceIntent()
}
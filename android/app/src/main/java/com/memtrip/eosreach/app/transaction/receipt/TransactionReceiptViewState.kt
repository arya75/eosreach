package com.memtrip.eosreach.app.transaction.receipt

import com.memtrip.eosreach.api.transfer.ActionReceipt
import com.memtrip.eosreach.app.account.AccountFragmentPagerAdapter
import com.memtrip.mxandroid.MxViewState

data class TransactionReceiptViewState(val view: View) : MxViewState {

    sealed class View {
        object Idle : View()
        data class Populate(val actionReceipt: ActionReceipt) : View()
        data class NavigateToBlockExplorer(val transactionId: String) : View()
        object NavigateToActions : View()
        data class NavigateToAccount(
            val page: AccountFragmentPagerAdapter.Page
        ) : View()
    }
}
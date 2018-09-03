package com.memtrip.eosreach.app.account.actions

import com.memtrip.eosreach.api.actions.AccountActionList
import com.memtrip.eosreach.api.actions.model.AccountAction
import com.memtrip.mxandroid.MxViewState

data class ActionsViewState(val view: View) : MxViewState {

    sealed class View {
        object Idle : View()
        object OnProgress : View()
        data class OnSuccess(val accountActionList: AccountActionList) : View()
        object OnError : View()
        data class NavigateToViewAction(val accountAction: AccountAction) : View()
    }
}
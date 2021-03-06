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
package com.memtrip.eosreach.app.issue.createaccount

import com.android.billingclient.api.SkuDetails
import com.memtrip.mxandroid.MxRenderAction
import com.memtrip.mxandroid.MxViewLayout
import com.memtrip.mxandroid.MxViewRenderer
import javax.inject.Inject

sealed class CreateAccountRenderAction : MxRenderAction {
    object Idle : CreateAccountRenderAction()
    object StartBillingConnection : CreateAccountRenderAction()
    data class OnSkuSuccess(val skuDetails: SkuDetails) : CreateAccountRenderAction()
    data class OnGetSkuError(val message: String) : CreateAccountRenderAction()
    object OnAccountNameValidationPassed : CreateAccountRenderAction()
    object OnCreateAccountLimbo : CreateAccountRenderAction()
    object OnCreateAccountLimboProgress : CreateAccountRenderAction()
    object OnCreateAccountProgress : CreateAccountRenderAction()
    data class OnCreateAccountSuccess(val purchaseToken: String, val privateKey: String) : CreateAccountRenderAction()
    data class OnCreateAccountError(val error: String) : CreateAccountRenderAction()
    object OnImportKeyProgress : CreateAccountRenderAction()
    data class OnImportKeyError(val error: String) : CreateAccountRenderAction()
    object NavigateToAccountList : CreateAccountRenderAction()
}

interface CreateAccountViewLayout : MxViewLayout {
    fun startBillingConnection()
    fun showSkuSuccess(skuDetails: SkuDetails)
    fun showSkuError(message: String)
    fun showAccountNameValidationPassed()
    fun showCreateAccountLimbo()
    fun showCreateAccountLimboProgress()
    fun showCreateAccountProgress()
    fun showAccountCreated(purchaseToken: String, privateKey: String)
    fun showCreateAccountError(error: String)
    fun showImportKeyProgress()
    fun showImportKeyError(error: String)
    fun navigateToAccountList()
}

class CreateAccountViewRenderer @Inject internal constructor() : MxViewRenderer<CreateAccountViewLayout, CreateAccountViewState> {
    override fun layout(layout: CreateAccountViewLayout, state: CreateAccountViewState) = when (state.view) {
        CreateAccountViewState.View.Idle -> {
        }
        CreateAccountViewState.View.StartBillingConnection -> {
            layout.startBillingConnection()
        }
        is CreateAccountViewState.View.OnSkuSuccess -> {
            layout.showSkuSuccess(state.view.skuDetails)
        }
        is CreateAccountViewState.View.OnGetSkuError -> {
            layout.showSkuError(state.view.message)
        }
        is CreateAccountViewState.View.OnAccountNameValidationPassed -> {
            layout.showAccountNameValidationPassed()
        }
        CreateAccountViewState.View.OnCreateAccountProgress -> {
            layout.showCreateAccountProgress()
        }
        is CreateAccountViewState.View.OnCreateAccountLimbo -> {
            layout.showCreateAccountLimbo()
        }
        CreateAccountViewState.View.OnCreateAccountLimboProgress -> {
            layout.showCreateAccountLimboProgress()
        }
        is CreateAccountViewState.View.OnCreateAccountSuccess -> {
            layout.showAccountCreated(state.view.purchaseToken, state.view.privateKey)
        }
        is CreateAccountViewState.View.CreateAccountError -> {
            layout.showCreateAccountError(state.view.error)
        }
        CreateAccountViewState.View.OnImportKeyProgress -> {
            layout.showImportKeyProgress()
        }
        is CreateAccountViewState.View.ImportKeyError -> {
            layout.showImportKeyError(state.view.error)
        }
        CreateAccountViewState.View.NavigateToAccountList -> {
            layout.navigateToAccountList()
        }
    }
}
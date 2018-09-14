package com.memtrip.eosreach.app.account.resources.manage.bandwidth

import com.memtrip.mxandroid.MxViewIntent

sealed class BandwidthFormIntent : MxViewIntent {
    object Init : BandwidthFormIntent()
    object Idle : BandwidthFormIntent()
    data class Commit(
        val netAmount: String,
        val cpuAmount: String,
        val bandwidthCommitType: BandwidthCommitType
    ) : BandwidthFormIntent()
}
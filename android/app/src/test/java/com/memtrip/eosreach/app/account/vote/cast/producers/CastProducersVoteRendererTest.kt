package com.memtrip.eosreach.app.account.vote.cast.producers

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class CastProducersVoteRendererTest : Spek({

    val layout by memoized { mock<CastProducersVoteViewLayout>() }

    given("a CastProducersVoteRenderer") {

        val renderer by memoized { CastProducersVoteViewRenderer() }

        on("ViewState is not initialized") {
            renderer.layout(layout, CastProducersVoteViewState(CastProducersVoteViewState.View.OnProgress))

            it("shows the Progress") {
                verify(layout).showProgress()
            }
        }

        on("ViewState is initialized") {
            renderer.layout(layout, CastProducersVoteViewState(CastProducersVoteViewState.View.OnError))

            it("hides the Progress") {
                verify(layout).showError()
            }
        }
    }
})
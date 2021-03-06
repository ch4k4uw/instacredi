package com.sicredi.presenter.common.uc

import android.content.Intent
import kotlinx.coroutines.flow.Flow

interface ShareEvent {
    suspend operator fun invoke(eventId: String): Flow<Intent>
}
package com.sicredi.domain.credential.infra.service

import android.os.Parcelable
import android.util.Base64
import com.sicredi.domain.credential.infra.extensions.marshall
import com.sicredi.domain.credential.infra.extensions.unmarshall
import kotlinx.parcelize.Parcelize
import java.security.MessageDigest
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PasswordHashing @Inject constructor() {
    companion object {
        private const val INTERACTION_CONT = 0xFFL
        private val creator = PasswordLocal::class.java
            .fields
            .find { it.name == "CREATOR" }
            ?.get(null)
            ?.let { it as? Parcelable.Creator<*> }
            ?: throw RuntimeException("${PasswordLocal::class.java.name}::CREATOR not found")
    }

    private val md by lazy {
        MessageDigest.getInstance("SHA-512")
    }

    internal fun hash(password: String): String {
        val salt = UUID.randomUUID().toString()
        val count = INTERACTION_CONT
        val hashedPassword = hash(salt = salt, password = password, interactions = count)
        return PasswordLocal(salt = salt, interactions = count, password = hashedPassword)
            .marshall()
    }

    private fun hash(salt: String, password: String, interactions: Long): String {
        var hashedPassword = salt + password
        for (i in 0 until interactions) {
            hashedPassword = hash(password = md.digest(hashedPassword.toByteArray()))
        }
        return hashedPassword
    }

    private fun hash(password: ByteArray): String {
        return password.map { Integer.toHexString(0xFF and it.toInt()) }
            .map { if (it.length < 2) "0$it" else it }
            .fold("") { acc, d -> acc + d }
    }

    internal fun compare(password: String, hash: String): Boolean {
        val rawPasswordLocal = hash
            .takeIf { it.isNotEmpty() }
            ?.let { Base64.decode(it, Base64.NO_WRAP) }
            ?: ByteArray(0)

        return creator.unmarshall(data = rawPasswordLocal, defaultValue = PasswordLocal.Empty)
            .let { passwordLocal ->
                if (passwordLocal != PasswordLocal.Empty) {
                    val hashedPassword = hash(
                        salt = passwordLocal.salt,
                        password = password,
                        interactions = passwordLocal.interactions,
                    )
                    hashedPassword == passwordLocal.password
                } else {
                    false
                }
            }
    }

    @Parcelize
    private data class PasswordLocal(
        val salt: String = "",
        val interactions: Long = 0L,
        val password: String = ""
    ) : Parcelable {
        companion object {
            val Empty = PasswordLocal()
        }
    }
}
package com.sicredi.domain.credential.infra.service.stuff

object PasswordHashingFixture {
    const val Password1 = "combat machine detonate everything 123"
    const val Password2 = "combat machine detonate everything 124"
    const val Password3 = "combat Machine detonate everything 124"
    val HashedPassword3 = Pair(
        Password3,
        "JAAAAGYANQA5ADcAYQA5AGIANAAtAGUAMAAxADUALQA0ADAAMgBhAC0AYQBlADMAOAAtAGEAOQBhAGIANgA2ADIAZQAzADkAZgA3AAAAAADoAwAAAAAAAIAAAAA0ADIAOQA1AGYANgBmAGQANwBkADYANQBkADMAYQBhADIAYgA2ADAANwBkAGYAZgBiAGIAZAA1AGMAMAA3ADQAZQAyADEAYgBhAGYAZgAwAGEAYQA2ADEAMABiAGMAYwAwAGIAZQAyAGEAOQA3AGQAZQBhADgANQBhADMAZgBhAGMAMQAxAGUAYQA1ADAAMwAwAGUAZQA3AGYANAAxADYAOAA0AGEANgBiADUAMQAyADcAMgBmADEAZQAyAGYAZgBkAGUAYQA4ADgAMQBjADYAZgA2ADIAMwBjADcAMwA2AGEAYgA1AGIAMAAxADYAMwBiADAAOQBiADkAYQA1ADgAAAAAAA=="
    )
}
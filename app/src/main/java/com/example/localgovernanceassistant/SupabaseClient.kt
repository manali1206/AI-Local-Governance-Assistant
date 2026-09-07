package com.example.localgovernanceassistant

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

val supabaseClient = createSupabaseClient(
    supabaseUrl = "https://qferjbjuivdobqjxqmxi.supabase.co",
    supabaseKey = "sb_publishable__1eaBVBO7Skx2gVpuBrrag_IYGX82Ym"
) {
    install(Auth)
    install(Postgrest)
}
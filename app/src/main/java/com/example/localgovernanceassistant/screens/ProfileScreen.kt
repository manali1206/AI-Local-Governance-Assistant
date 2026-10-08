package com.example.localgovernanceassistant.screens

import android.app.LocaleManager
import android.os.Build
import android.os.LocaleList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.localgovernanceassistant.R
import com.example.localgovernanceassistant.supabaseClient

import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch


@Composable
fun ProfileScreen(
    onLogout: () -> Unit = {}
) {

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    // Localized strings
    val language = stringResource(R.string.language)
    val english = stringResource(R.string.english)
    val marathi = stringResource(R.string.marathi)

    val currentUser = supabaseClient.auth.currentUserOrNull()

    val myProfile = stringResource(R.string.my_profile)
    val profile = stringResource(R.string.profile)

    val nameLabel = stringResource(R.string.name)
    val emailLabel = stringResource(R.string.email)
    val phoneLabel = stringResource(R.string.phone)

    val saveProfile = stringResource(R.string.save_profile)
    val profileInformation = stringResource(R.string.profile_information)

    val myServices = stringResource(R.string.my_services)
    val myGrievances = stringResource(R.string.my_grievances)
    val governmentSchemes = stringResource(R.string.government_schemes)
    val nearbyServices = stringResource(R.string.nearby_services)
    val aiGovernanceAssistant =
        stringResource(R.string.ai_governance_assistant)

    val editProfile = stringResource(R.string.edit_profile)
    val logout = stringResource(R.string.logout)

    val noEmailAvailable =
        stringResource(R.string.no_email_available)

    val notAdded =
        stringResource(R.string.not_added)

    val citizen =
        stringResource(R.string.citizen)


    var name by remember {
        mutableStateOf(citizen)
    }

    var email by remember {
        mutableStateOf(
            currentUser?.email ?: noEmailAvailable
        )
    }

    var phone by remember {
        mutableStateOf(notAdded)
    }

    var editing by remember {
        mutableStateOf(false)
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {

        // -------------------------
        // PROFILE HEADER
        // -------------------------

        item {

            Text(
                text = myProfile,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),

                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Text(
                        text = profile,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text = name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = email,
                        fontSize = 14.sp
                    )
                }
            }
        }


        // -------------------------
        // PROFILE INFORMATION
        // -------------------------

        item {

            if (editing) {

                OutlinedTextField(
                    value = name,

                    onValueChange = {
                        name = it
                    },

                    label = {
                        Text(nameLabel)
                    },

                    modifier =
                        Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )


                OutlinedTextField(
                    value = email,

                    onValueChange = {
                        email = it
                    },

                    label = {
                        Text(emailLabel)
                    },

                    modifier =
                        Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )


                OutlinedTextField(
                    value = phone,

                    onValueChange = {
                        phone = it
                    },

                    label = {
                        Text(phoneLabel)
                    },

                    modifier =
                        Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )


                Button(
                    onClick = {
                        editing = false
                    },

                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Text(saveProfile)
                }

            } else {

                Card(
                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier =
                            Modifier.padding(18.dp)
                    ) {

                        Text(
                            text = profileInformation,
                            fontSize = 19.sp,
                            fontWeight =
                                FontWeight.Bold
                        )

                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )


                        Text(
                            text =
                                "${nameLabel}: $name"
                        )

                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )


                        Text(
                            text =
                                "${emailLabel}: $email"
                        )

                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )


                        Text(
                            text =
                                "${phoneLabel}: $phone"
                        )
                    }
                }


                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )


                Button(
                    onClick = {
                        editing = true
                    },

                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Text(editProfile)
                }
            }
        }


        // -------------------------
        // MY SERVICES
        // -------------------------

        item {

            Card(
                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier =
                        Modifier.padding(18.dp)
                ) {

                    Text(
                        text = myServices,
                        fontSize = 19.sp,
                        fontWeight =
                            FontWeight.Bold
                    )

                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )


                    Text(
                        text = myGrievances
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )


                    Text(
                        text = governmentSchemes
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )


                    Text(
                        text = nearbyServices
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )


                    Text(
                        text =
                            aiGovernanceAssistant
                    )
                }
            }
        }


        // -------------------------
        // LANGUAGE
        // -------------------------

        item {

            Card(
                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier =
                        Modifier.padding(18.dp)
                ) {

                    Text(
                        text = language,
                        fontSize = 19.sp,
                        fontWeight =
                            FontWeight.Bold
                    )

                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )


                    Row(
                        modifier =
                            Modifier.fillMaxWidth(),

                        horizontalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {

                        // English

                        Button(
                            onClick = {

                                if (
                                    Build.VERSION.SDK_INT >=
                                    Build.VERSION_CODES.TIRAMISU
                                ) {

                                    val localeManager =
                                        context.getSystemService(
                                            LocaleManager::class.java
                                        )

                                    localeManager
                                        .applicationLocales =
                                        LocaleList
                                            .forLanguageTags("en")
                                }
                            },

                            modifier =
                                Modifier.weight(1f)
                        ) {

                            Text(english)
                        }


                        // Marathi

                        Button(
                            onClick = {

                                if (
                                    Build.VERSION.SDK_INT >=
                                    Build.VERSION_CODES.TIRAMISU
                                ) {

                                    val localeManager =
                                        context.getSystemService(
                                            LocaleManager::class.java
                                        )

                                    localeManager
                                        .applicationLocales =
                                        LocaleList
                                            .forLanguageTags("mr")
                                }
                            },

                            modifier =
                                Modifier.weight(1f)
                        ) {

                            Text(marathi)
                        }
                    }
                }
            }
        }


        // -------------------------
        // LOGOUT
        // -------------------------

        item {

            Button(
                onClick = {

                    scope.launch {

                        try {

                            supabaseClient
                                .auth
                                .signOut()

                        } finally {

                            onLogout()
                        }
                    }
                },

                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            MaterialTheme
                                .colorScheme
                                .error
                    )
            ) {

                Text(logout)
            }
        }
    }
}
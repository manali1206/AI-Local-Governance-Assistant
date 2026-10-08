package com.example.localgovernanceassistant.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localgovernanceassistant.R
import com.example.localgovernanceassistant.model.Grievance
import com.example.localgovernanceassistant.model.GrievanceStatusHistory
import com.example.localgovernanceassistant.repository.GrievanceRepository
import com.example.localgovernanceassistant.supabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch


@Composable
fun GrievanceStatus(status: String) {

    val pending = stringResource(R.string.pending)
    val inProgress = stringResource(R.string.grievance_in_progress)
    val resolved = stringResource(R.string.resolved)
    val rejected = stringResource(R.string.rejected)

    val statusText = when (status.trim().lowercase()) {
        "pending" -> pending
        "in progress" -> inProgress
        "resolved" -> resolved
        "rejected" -> rejected
        else -> status
    }

    Text(
        text = statusText,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        modifier = Modifier
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp
            )
    )
}


@Composable
fun GrievanceScreen() {

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var message by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var isRefreshing by remember {
        mutableStateOf(false)
    }

    var grievances by remember {
        mutableStateOf<List<Grievance>>(emptyList())
    }

    // --------------------------------------------------
    // TRACKING STATE
    // --------------------------------------------------

    var trackingReferenceId by remember {
        mutableStateOf("")
    }

    var trackedGrievance by remember {
        mutableStateOf<Grievance?>(null)
    }

    var statusHistory by remember {
        mutableStateOf<List<GrievanceStatusHistory>>(emptyList())
    }

    var tracking by remember {
        mutableStateOf(false)
    }

    var trackingError by remember {
        mutableStateOf<String?>(null)
    }

    val scope = rememberCoroutineScope()

    val repository = remember {
        GrievanceRepository()
    }

    // --------------------------------------------------
    // LOCALIZED STRINGS
    // --------------------------------------------------

    val grievancesTitle =
        stringResource(R.string.grievances)

    val submitAndTrackComplaints =
        stringResource(R.string.submit_and_track_complaints)

    val submitAGrievance =
        stringResource(R.string.submit_a_grievance)

    val grievanceTitle =
        stringResource(R.string.grievance_title)

    val describeGrievance =
        stringResource(R.string.describe_your_grievance)

    val submitGrievance =
        stringResource(R.string.submit_grievance)

    val submitting =
        stringResource(R.string.submitting)

    val pleaseEnterTitleDescription =
        stringResource(R.string.please_enter_title_description)

    val pleaseLoginBeforeGrievance =
        stringResource(R.string.please_login_before_grievance)

    val grievanceSubmittedSuccessfully =
        stringResource(R.string.grievance_submitted_successfully)

    val failedToLoadGrievances =
        stringResource(R.string.failed_to_load_grievances)

    val failedToSubmitGrievance =
        stringResource(R.string.failed_to_submit_grievance)

    val myGrievances =
        stringResource(R.string.my_grievances)

    val pleaseLoginToViewGrievances =
        stringResource(R.string.please_login_to_view_grievances)

    val grievancesRefreshed =
        stringResource(R.string.grievances_refreshed)

    val failedToRefreshGrievances =
        stringResource(R.string.failed_to_refresh_grievances)

    val refreshing =
        stringResource(R.string.refreshing)

    val refreshStatus =
        stringResource(R.string.refresh_status)

    val noGrievancesSubmitted =
        stringResource(R.string.no_grievances_submitted)

    val grievanceId =
        stringResource(R.string.grievance_id)

    val submitted =
        stringResource(R.string.submitted)

    val notAvailable =
        stringResource(R.string.not_available)

    // --------------------------------------------------
    // TRACKING STRINGS
    // --------------------------------------------------

    val pleaseEnterGrievanceId =
        stringResource(R.string.please_enter_grievance_id)

    val grievanceNotFound =
        stringResource(R.string.grievance_not_found)

    val failedToTrackGrievance =
        stringResource(R.string.failed_to_track_grievance)

    // --------------------------------------------------
    // TIMELINE STRINGS
    // --------------------------------------------------

    val statusTimeline =
        stringResource(R.string.status_timeline)

    val submittedTimeline =
        stringResource(R.string.submitted)

    val noStatusHistory =
        stringResource(R.string.no_status_history)

    // --------------------------------------------------
    // LOAD USER GRIEVANCES
    // --------------------------------------------------

    LaunchedEffect(Unit) {

        val currentUser =
            supabaseClient.auth.currentUserOrNull()

        if (currentUser != null) {

            try {

                grievances =
                    repository.getUserGrievances(
                        currentUser.id
                    )

            } catch (e: Exception) {

                message =
                    e.message ?: failedToLoadGrievances
            }
        }
    }

    // --------------------------------------------------
    // MAIN SCREEN
    // --------------------------------------------------

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Top
    ) {

        // ==================================================
        // SCREEN HEADER
        // ==================================================

        item {

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = grievancesTitle,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = submitAndTrackComplaints,
                fontSize = 15.sp,
                modifier = Modifier.padding(top = 6.dp)
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }

        // ==================================================
        // TRACK GRIEVANCE
        // ==================================================

        item {

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = stringResource(
                            R.string.track_grievance
                        ),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = stringResource(
                            R.string.track_grievance_by_id
                        ),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    OutlinedTextField(
                        value = trackingReferenceId,
                        onValueChange = {
                            trackingReferenceId = it
                            trackingError = null
                            trackedGrievance = null
                            statusHistory = emptyList()
                        },
                        label = {
                            Text(
                                stringResource(
                                    R.string.enter_grievance_id
                                )
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Button(
                        onClick = {

                            val referenceId =
                                trackingReferenceId.trim()

                            if (referenceId.isEmpty()) {

                                trackingError =
                                    pleaseEnterGrievanceId

                                trackedGrievance = null
                                statusHistory = emptyList()

                                return@Button
                            }

                            val currentUser =
                                supabaseClient.auth.currentUserOrNull()

                            if (currentUser == null) {

                                trackingError =
                                    pleaseLoginToViewGrievances

                                trackedGrievance = null
                                statusHistory = emptyList()

                                return@Button
                            }

                            scope.launch {

                                tracking = true
                                trackingError = null
                                trackedGrievance = null
                                statusHistory = emptyList()

                                try {

                                    val result =
                                        repository.getGrievanceByReferenceId(
                                            userId = currentUser.id,
                                            referenceId = referenceId
                                        )

                                    if (result == null) {

                                        trackingError =
                                            grievanceNotFound

                                    } else {

                                        trackedGrievance =
                                            result

                                        statusHistory =
                                            repository.getGrievanceStatusHistory(
                                                userId = currentUser.id,
                                                referenceId = referenceId
                                            )
                                    }

                                } catch (e: Exception) {

                                    trackingError =
                                        e.message
                                            ?: failedToTrackGrievance

                                } finally {

                                    tracking = false
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !tracking
                    ) {

                        Text(
                            text = if (tracking) {
                                stringResource(
                                    R.string.tracking
                                )
                            } else {
                                stringResource(
                                    R.string.track_status
                                )
                            }
                        )
                    }

                    // --------------------------------------------------
                    // TRACKING ERROR
                    // --------------------------------------------------

                    trackingError?.let { error ->

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    // --------------------------------------------------
                    // TRACKING RESULT
                    // --------------------------------------------------

                    trackedGrievance?.let { grievance ->

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {

                                Text(
                                    text = stringResource(
                                        R.string.grievance_found
                                    ),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(
                                    modifier = Modifier.height(10.dp)
                                )

                                Text(
                                    text =
                                        "$grievanceId: ${
                                            grievance.referenceId
                                                ?: notAvailable
                                        }",
                                    fontSize = 13.sp
                                )

                                Spacer(
                                    modifier = Modifier.height(6.dp)
                                )

                                Text(
                                    text =
                                        "${stringResource(R.string.grievance_title)}: ${grievance.title}",
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(
                                    modifier = Modifier.height(6.dp)
                                )

                                Text(
                                    text = stringResource(
                                        R.string.current_status
                                    )
                                )

                                Spacer(
                                    modifier = Modifier.height(6.dp)
                                )

                                GrievanceStatus(
                                    status = grievance.status
                                )

                                if (grievance.createdAt != null) {

                                    Spacer(
                                        modifier = Modifier.height(8.dp)
                                    )

                                    Text(
                                        text =
                                            "$submitted: ${
                                                formatTimelineDate(
                                                    grievance.createdAt
                                                )
                                            }",
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }

                        // ==================================================
                        // STATUS TIMELINE
                        // ==================================================

                        Spacer(
                            modifier = Modifier.height(18.dp)
                        )

                        Text(
                            text = statusTimeline,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        if (statusHistory.isEmpty()) {

                            Text(
                                text = noStatusHistory,
                                style = MaterialTheme.typography.bodyMedium
                            )

                        } else {

                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                // --------------------------------------------------
                                // INITIAL SUBMISSION
                                // --------------------------------------------------

                                TimelineItem(
                                    oldStatus = null,
                                    newStatus = submittedTimeline,
                                    dateTime = grievance.createdAt ?: "",
                                    isLast = statusHistory.isEmpty(),
                                    isCurrent = statusHistory.isEmpty()
                                )

                                // --------------------------------------------------
                                // STATUS CHANGES
                                // --------------------------------------------------

                                statusHistory.forEachIndexed { index, history ->

                                    TimelineItem(
                                        oldStatus = history.oldStatus,
                                        newStatus = history.newStatus,
                                        dateTime = history.changedAt,
                                        isLast =
                                            index == statusHistory.lastIndex,
                                        isCurrent =
                                            index == statusHistory.lastIndex
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }

        // ==================================================
        // SUBMIT GRIEVANCE
        // ==================================================

        item {

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = submitAGrievance,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    OutlinedTextField(
                        value = title,
                        onValueChange = {
                            title = it
                            message = ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(grievanceTitle)
                        },
                        singleLine = true
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = {
                            description = it
                            message = ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(describeGrievance)
                        },
                        minLines = 4
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Button(
                        onClick = {

                            if (
                                title.isBlank() ||
                                description.isBlank()
                            ) {

                                message =
                                    pleaseEnterTitleDescription

                                return@Button
                            }

                            val currentUser =
                                supabaseClient.auth.currentUserOrNull()

                            if (currentUser == null) {

                                message =
                                    pleaseLoginBeforeGrievance

                                return@Button
                            }

                            scope.launch {

                                isLoading = true
                                message = ""

                                try {

                                    val grievance =
                                        Grievance(
                                            userId = currentUser.id,
                                            title = title.trim(),
                                            description = description.trim()
                                        )

                                    repository.submitGrievance(
                                        grievance
                                    )

                                    title = ""
                                    description = ""

                                    grievances =
                                        repository.getUserGrievances(
                                            currentUser.id
                                        )

                                    message =
                                        grievanceSubmittedSuccessfully

                                } catch (e: Exception) {

                                    message =
                                        e.message
                                            ?: failedToSubmitGrievance

                                } finally {

                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading
                    ) {

                        Text(
                            if (isLoading) {
                                submitting
                            } else {
                                submitGrievance
                            }
                        )
                    }

                    if (message.isNotEmpty()) {

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text(
                            text = message
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )
        }

        // ==================================================
        // MY GRIEVANCES HEADER
        // ==================================================

        item {

            Text(
                text = myGrievances,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Button(
                onClick = {

                    val currentUser =
                        supabaseClient.auth.currentUserOrNull()

                    if (currentUser == null) {

                        message =
                            pleaseLoginToViewGrievances

                        return@Button
                    }

                    scope.launch {

                        isRefreshing = true

                        try {

                            grievances =
                                repository.getUserGrievances(
                                    currentUser.id
                                )

                            message =
                                grievancesRefreshed

                        } catch (e: Exception) {

                            message =
                                e.message
                                    ?: failedToRefreshGrievances

                        } finally {

                            isRefreshing = false
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isRefreshing && !isLoading
            ) {

                Text(
                    if (isRefreshing) {
                        refreshing
                    } else {
                        refreshStatus
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }

        // ==================================================
        // MY GRIEVANCES
        // ==================================================

        if (grievances.isEmpty()) {

            item {

                Text(
                    text = noGrievancesSubmitted,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(
                        vertical = 8.dp
                    )
                )
            }

        } else {

            items(
                items = grievances
            ) { grievance ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text =
                                "$grievanceId: ${
                                    grievance.referenceId
                                        ?: notAvailable
                                }",
                            fontSize = 12.sp
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = grievance.title,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = grievance.description,
                            fontSize = 14.sp
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        GrievanceStatus(
                            status = grievance.status
                        )

                        if (grievance.createdAt != null) {

                            Spacer(
                                modifier = Modifier.height(6.dp)
                            )

                            Text(
                                text =
                                    "$submitted: ${
                                        formatTimelineDate(
                                            grievance.createdAt
                                        )
                                    }",
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // ==================================================
        // BOTTOM SPACING
        // ==================================================

        item {

            Spacer(
                modifier = Modifier.height(24.dp)
            )
        }
    }
}


// ======================================================
// TIMELINE ITEM
// ======================================================

@Composable
private fun TimelineItem(
    oldStatus: String?,
    newStatus: String,
    dateTime: String,
    isLast: Boolean,
    isCurrent: Boolean
) {

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        // --------------------------------------------------
        // TIMELINE LINE + DOT
        // --------------------------------------------------

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(24.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(
                        if (isCurrent) {
                            16.dp
                        } else {
                            12.dp
                        }
                    )
                    .background(
                        color = if (isCurrent) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.outline
                        },
                        shape = CircleShape
                    )
            )

            if (!isLast) {

                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(58.dp)
                        .background(
                            color =
                                MaterialTheme.colorScheme.outline.copy(
                                    alpha = 0.4f
                                )
                        )
                )
            }
        }

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        // --------------------------------------------------
        // TIMELINE CONTENT
        // --------------------------------------------------

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {

            Column(
                modifier = Modifier.padding(14.dp)
            ) {

                if (oldStatus == null) {

                    // Initial grievance submission
                    GrievanceStatus(
                        status = newStatus
                    )

                } else {

                    // Old status → New status
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        GrievanceStatus(
                            status = oldStatus
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        Text(
                            text = "→",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        GrievanceStatus(
                            status = newStatus
                        )
                    }
                }

                if (dateTime.isNotBlank()) {

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = formatTimelineDate(dateTime),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}


// ======================================================
// TIMELINE DATE FORMAT
// ======================================================

private fun formatTimelineDate(
    value: String
): String {

    return value
        .replace("T", " ")
        .replace("Z", "")
        .take(19)
}
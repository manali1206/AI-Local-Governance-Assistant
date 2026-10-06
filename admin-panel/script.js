// =============================
// ADMIN LOGIN UI
// =============================

const loginForm = document.getElementById("loginForm");
const loginMessage = document.getElementById("message");

if (loginForm && loginMessage) {

    loginForm.addEventListener("submit", function (event) {

        event.preventDefault();

        const email = document.getElementById("email").value.trim();
        const password = document.getElementById("password").value.trim();

        if (email === "" || password === "") {
            loginMessage.textContent =
                "Please enter email and password.";
            return;
        }

        window.location.href = "dashboard.html";
    });
}

// =============================
// DASHBOARD SEARCH + FILTER
// =============================

const searchBox = document.querySelector(".search-box");
const statusFilter = document.querySelector(".status-filter");
const tableRows = document.querySelectorAll("tbody tr");

if (searchBox && statusFilter) {

    function filterGrievances() {

        const searchValue =
            searchBox.value.toLowerCase();

        const selectedStatus =
            statusFilter.value;

        tableRows.forEach(function (row) {

            const rowText =
                row.textContent.toLowerCase();

            const statusElement =
                row.querySelector(".status-badge");

            const rowStatus =
                statusElement
                    ? statusElement.textContent.trim()
                    : "";

            const matchesSearch =
                rowText.includes(searchValue);

            const matchesStatus =
                selectedStatus === "All Status" ||
                rowStatus === selectedStatus;

            row.style.display =
                matchesSearch && matchesStatus
                    ? ""
                    : "none";
        });
    }

    searchBox.addEventListener(
        "input",
        filterGrievances
    );

    statusFilter.addEventListener(
        "change",
        filterGrievances
    );
}

// =============================
// GRIEVANCE DETAILS
// =============================

const grievanceId = new URLSearchParams(window.location.search).get("id");

const grievanceData = {
    GRV001: {
        title: "Street Light Not Working",
        description: "The street light near the main road has not been working for several days.",
        user: "Citizen User",
        date: "03 Oct 2026",
        status: "Pending"
    },

    GRV002: {
        title: "Water Supply Issue",
        description: "Water supply has been interrupted in the residential area.",
        user: "Citizen User",
        date: "02 Oct 2026",
        status: "In Progress"
    },

    GRV003: {
        title: "Road Maintenance Request",
        description: "A damaged road requires maintenance for safe public travel.",
        user: "Citizen User",
        date: "01 Oct 2026",
        status: "Resolved"
    }
};

// =============================
// LOAD GRIEVANCE DETAILS
// =============================

if (grievanceId && grievanceData[grievanceId]) {

    const grievance = grievanceData[grievanceId];

    const referenceElement =
        document.getElementById("referenceId");

    const titleElement =
        document.getElementById("grievanceTitle");

    const descriptionElement =
        document.getElementById("grievanceDescription");

    const userElement =
        document.getElementById("grievanceUser");

    const dateElement =
        document.getElementById("grievanceDate");

    const statusElement =
        document.getElementById("grievanceStatus");

    if (referenceElement) {
        referenceElement.textContent = grievanceId;
    }

    if (titleElement) {
        titleElement.textContent = grievance.title;
    }

    if (descriptionElement) {
        descriptionElement.textContent =
            grievance.description;
    }

    if (userElement) {
        userElement.textContent = grievance.user;
    }

    if (dateElement) {
        dateElement.textContent = grievance.date;
    }

    if (statusElement) {
        statusElement.textContent = grievance.status;

        statusElement.className = "status-badge";

        if (grievance.status === "Pending") {
            statusElement.classList.add("pending-badge");
        } else if (grievance.status === "In Progress") {
            statusElement.classList.add("progress-badge");
        } else if (grievance.status === "Resolved") {
            statusElement.classList.add("resolved-badge");
        }
    }
}

// =============================
// DASHBOARD STATISTICS
// =============================

function updateDashboardStats() {

    const rows = document.querySelectorAll("tbody tr");

    let total = 0;
    let pending = 0;
    let inProgress = 0;
    let resolved = 0;

    rows.forEach(function(row) {

        const statusElement =
            row.querySelector(".status-badge");

        if (!statusElement) {
            return;
        }

        total++;

        const status =
            statusElement.textContent.trim();

        if (status === "Pending") {
            pending++;
        }

        if (status === "In Progress") {
            inProgress++;
        }

        if (status === "Resolved") {
            resolved++;
        }
    });

    const totalElement =
        document.getElementById("totalCount");

    const pendingElement =
        document.getElementById("pendingCount");

    const progressElement =
        document.getElementById("progressCount");

    const resolvedElement =
        document.getElementById("resolvedCount");

    if (totalElement) {
        totalElement.textContent = total;
    }

    if (pendingElement) {
        pendingElement.textContent = pending;
    }

    if (progressElement) {
        progressElement.textContent = inProgress;
    }

    if (resolvedElement) {
        resolvedElement.textContent = resolved;
    }
}

updateDashboardStats();
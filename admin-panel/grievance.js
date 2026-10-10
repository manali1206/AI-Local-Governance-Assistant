const grievances = {
    GRV001: {
        title: "Street Light Not Working",
        description: "The street light near the main road has not been working for several days. This is causing difficulty for residents and pedestrians during the night.",
        user: "Citizen User",
        date: "03 Oct 2026",
        status: "Pending"
    },

    GRV002: {
        title: "Water Supply Issue",
        description: "Water supply has been interrupted in the residential area and residents are facing difficulties.",
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

const params = new URLSearchParams(window.location.search);
const id = params.get("id");
const grievance = grievances[id];

const statusElement = document.getElementById("grievanceStatus");
const statusSelect = document.querySelector(".details-status-select");
const updateButton = document.querySelector(".update-status-button");
const statusMessage = document.getElementById("status-message");

function applyStatusStyle(status) {

    statusElement.textContent = status;
    statusElement.className = "status-badge";

    if (status === "Pending") {
        statusElement.classList.add("pending-badge");
    }

    if (status === "In Progress") {
        statusElement.classList.add("progress-badge");
    }

    if (status === "Resolved") {
        statusElement.classList.add("resolved-badge");
    }
}

if (grievance) {

    document.getElementById("referenceId").textContent = id;

    document.getElementById("grievanceTitle").textContent =
        grievance.title;

    document.getElementById("grievanceDescription").textContent =
        grievance.description;

    document.getElementById("grievanceUser").textContent =
        grievance.user;

    document.getElementById("grievanceDate").textContent =
        grievance.date;

    statusSelect.value = grievance.status;

    applyStatusStyle(grievance.status);

    updateButton.addEventListener("click", function () {

        const newStatus = statusSelect.value;

        applyStatusStyle(newStatus);

        statusMessage.textContent =
            `Status changed to "${newStatus}" for ${id}.`;

    });

} else {

    document.getElementById("referenceId").textContent = "Not Found";

    document.getElementById("grievanceTitle").textContent =
        "Grievance not found";

    document.getElementById("grievanceDescription").textContent =
        "No grievance information is available.";
}
// =============================
// ADMIN LOGIN UI
// =============================

const loginForm = document.getElementById("loginForm");
const loginMessage = document.getElementById("message");

if (loginForm && loginMessage) {

    loginForm.addEventListener("submit", function (event) {

        event.preventDefault();

        loginMessage.textContent =
            "Admin authentication will be connected to the backend.";

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
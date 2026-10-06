const citizenSearch =
    document.getElementById("citizenSearch");

const citizenStatusFilter =
    document.getElementById("citizenStatusFilter");

const citizenRows =
    document.querySelectorAll("#citizensTableBody tr");


function filterCitizens() {

    const searchValue =
        citizenSearch.value.trim().toLowerCase();

    const selectedStatus =
        citizenStatusFilter.value;


    citizenRows.forEach(function (row) {

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


if (citizenSearch) {

    citizenSearch.addEventListener(
        "input",
        filterCitizens
    );

}


if (citizenStatusFilter) {

    citizenStatusFilter.addEventListener(
        "change",
        filterCitizens
    );

}
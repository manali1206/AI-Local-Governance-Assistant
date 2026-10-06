const citizens = {

    CIT001: {

        name: "Pranaya Patil",

        email: "pranaya@example.com",

        date: "15 Sep 2026",

        status: "Active",

        grievances: [

            {
                id: "GRV001",
                title: "Street Light Not Working",
                status: "Pending"
            },

            {
                id: "GRV007",
                title: "Water Supply Issue",
                status: "Resolved"
            },

            {
                id: "GRV011",
                title: "Road Maintenance Request",
                status: "In Progress"
            }

        ]

    },


    CIT002: {

        name: "Rahul Sharma",

        email: "rahul@example.com",

        date: "18 Sep 2026",

        status: "Active",

        grievances: [

            {
                id: "GRV002",
                title: "Water Supply Issue",
                status: "In Progress"
            }

        ]

    },


    CIT003: {

        name: "Sneha Kulkarni",

        email: "sneha@example.com",

        date: "20 Sep 2026",

        status: "Active",

        grievances: [

            {
                id: "GRV003",
                title: "Road Maintenance Request",
                status: "Resolved"
            },

            {
                id: "GRV008",
                title: "Street Drainage Issue",
                status: "Pending"
            },

            {
                id: "GRV014",
                title: "Public Water Tap Issue",
                status: "Resolved"
            }

        ]

    }

};


const params =
    new URLSearchParams(
        window.location.search
    );


const id =
    params.get("id");


const citizen =
    citizens[id];


function getStatusClass(status) {

    if (status === "Pending") {

        return "pending-badge";

    }


    if (status === "In Progress") {

        return "progress-badge";

    }


    if (status === "Resolved") {

        return "resolved-badge";

    }


    return "";

}


if (citizen) {

    document.getElementById(
        "citizenId"
    ).textContent = id;


    document.getElementById(
        "citizenName"
    ).textContent = citizen.name;


    document.getElementById(
        "citizenEmail"
    ).textContent = citizen.email;


    document.getElementById(
        "citizenDate"
    ).textContent = citizen.date;


    document.getElementById(
        "citizenGrievances"
    ).textContent =
        citizen.grievances.length;


    document.getElementById(
        "citizenStatus"
    ).textContent =
        citizen.status;


    const grievanceList =
        document.getElementById(
            "citizenGrievanceList"
        );


    grievanceList.innerHTML = "";


    citizen.grievances.forEach(
        function (grievance) {

            const item =
                document.createElement("div");


            item.className =
                "citizen-grievance-item";


            item.innerHTML = `

                <div>

                    <strong>
                        ${grievance.id}
                    </strong>

                    <p>
                        ${grievance.title}
                    </p>

                </div>

                <span
                    class="status-badge ${getStatusClass(grievance.status)}"
                >
                    ${grievance.status}
                </span>

            `;


            grievanceList.appendChild(item);

        }
    );


} else {

    document.getElementById(
        "citizenId"
    ).textContent =
        "Not Found";


    document.getElementById(
        "citizenName"
    ).textContent =
        "Citizen not found";


    document.getElementById(
        "citizenEmail"
    ).textContent =
        "-";


    document.getElementById(
        "citizenDate"
    ).textContent =
        "-";


    document.getElementById(
        "citizenGrievances"
    ).textContent =
        "0";


    document.getElementById(
        "citizenGrievanceList"
    ).innerHTML =
        "<p>No citizen information available.</p>";

}
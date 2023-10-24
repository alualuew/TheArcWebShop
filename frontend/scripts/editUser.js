$(document).ready(function () {
    loadUsers();
});

function loadUsers() {
    $.ajax({
        url: "http://localhost:8080/users",
        method: "GET",
        headers: { "Authorization": sessionStorage.getItem("token") },
        success: function (users) {
            allUsers = users;
            currentPage = 1;
            addUsersToList(users);
        },
        error: function (error) {
            console.error(error);
        }
    });
}
function addUsersToList(users) {
    const divPages = $("#usersPagesButton");
    divPages.show();
    divPages.find("p").text(currentPage);

    const usersPerPage = 6;
    const startIndex = (currentPage - 1) * usersPerPage;
    const endIndex = startIndex + usersPerPage;
    const usersToShow = users.slice(startIndex, endIndex);
    const userTableBody = $("#userTableBody");

    userTableBody.empty();

    displayUserEditOrList("table-row-group", "none");

    usersToShow.forEach(function (user) {
        const row = $("<tr>");
        row.append($("<td>").text(user.id));
        row.append($("<td>").text(user.username));
        row.append($("<td class='d-none d-md-table-cell'>").text(user.email));
        row.append($("<td class='text-center'>").html(user.active ? "&#10004;&#65039;" : "&#10060;"));
        row.append($("<td class='text-center'>").html(user.admin ? "&#10004;&#65039;" : "&#10060;"));
        const userEditButton = $("<button class='btn btn-primary mx-1' id='userEditButton'><span class='text-nowrap'><span class='d-none d-sm-inline'>Bearbeiten </span><span>&#x2692;&#xFE0F;</span></span></button>");
        userEditButton.click(function () {
            editUser(user);
        });
        const userDeleteButton = $("<button class='btn btn-danger mx-1' id='userDeleteButton'>&#x1F5D1;&#xFE0F;</button>");
        userDeleteButton.click(function () {
            deleteUser(user.id);
        })

        const userHandler = $("<td class='text-end pe-0'>");
        userHandler.append(userEditButton, userDeleteButton);
        row.append(userHandler);

        userTableBody.append(row);
    });
}

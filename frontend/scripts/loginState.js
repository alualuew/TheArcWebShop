$(document).ready(function() {
    // Check for token in session storage
    if (sessionStorage.getItem('token')) {
        // Replace login button with logout button
        $('a[href="./login.html"]').replaceWith('<a href="#" class="btn btn-green m-2" id="logoutButton">Logout</a>');
        
        // Add event listener to logout button
        $('#logoutButton').click(function(e) {
            e.preventDefault(); // Prevent default action
            sessionStorage.removeItem('token');
            sessionStorage.removeItem('loginTimestamp');
            window.location.href = './index.html';
        });
    }
});

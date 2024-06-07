document.getElementById('login-form').addEventListener('submit', async function(event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const userCredentials = {
        username: username,
        password: password
    };

    try {
        const response = await fetch('http://localhost:8080/user/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userCredentials)
        });

        if (response.ok) {
            const result = await response.json();
            if (result.success) {
            // Store userId locally
             localStorage.setItem('userId', result.userId);
                // Redirect to the app if login is successful
                window.location.href = '../ui/dashboard.html';
            } else {
                alert('Invalid username or password');
            }
        } else {
            alert('Error logging in');
            console.error('Error logging in:', response.statusText);
        }
    } catch (error) {
        console.error('Network error:', error);
        alert('Network error');
    }
});

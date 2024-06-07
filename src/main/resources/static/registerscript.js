document.getElementById('register-form').addEventListener('submit', async (event) => {
    event.preventDefault(); // Prevent the default form submission

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const user = {
        username: username,
        password: password
    };

    try {
        const response = await fetch('http://localhost:8080/user/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        });

        if (response.ok) {
            const result = await response.json();
            window.location.href = 'login.html';
            console.log('User registered successfully:', result);
            alert('User registered successfully');
            // Optionally redirect or clear the form
        } else {
            console.error('Error registering user:', response.statusText);
            alert('Error registering user');
        }
    } catch (error) {
        console.error('Network error:', error);
        alert('Network error');
    }
});

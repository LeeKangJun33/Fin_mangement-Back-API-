document.getElementById('signup-form').addEventListener('submit', async (event) => {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const email = document.getElementById('email').value;

    const response = await fetch('http://localhost:8080/api/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            username,
            password,
            email,
        }),
    });

    if (response.ok) {
        alert('회원가입 성공!');
        window.location.href = '/index.html';
    } else {
        const errorData = await response.json();
        alert(`회원가입 실패: ${errorData.message}`);
    }
});

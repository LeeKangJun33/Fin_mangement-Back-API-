document.addEventListener('DOMContentLoaded', () => {
    // 차트 로드 로직
    loadChart();

    const loginButton = document.querySelector('#login-button');
    if (loginButton) {
        loginButton.addEventListener('click', handleLogin);
    }
});

function loadChart() {
    console.log('Chart loaded');
    // 차트 로드 로직
}

function handleLogin() {
    console.log('Login button clicked');
}

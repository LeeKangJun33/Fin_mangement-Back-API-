document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('login-form');
    const loginButton = document.getElementById('login-button');

    // 로그인 폼 제출 이벤트 리스너
    loginForm.addEventListener('submit', async (event) => {
        event.preventDefault(); // 기본 동작 방지

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        try {
            // API로 로그인 요청
            const response = await fetch('http://localhost:8080/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password }),
            });

            if (response.ok) {
                const data = await response.json();
                console.log('로그인 성공:', data);

                // JWT 토큰 저장 (예: localStorage)
                localStorage.setItem('token', data.token);

                // 메인 페이지로 이동
                window.location.href = '/dashboard.html';
            } else {
                const error = await response.json();
                alert(`로그인 실패: ${error.message || '알 수 없는 오류'}`);
            }
        } catch (error) {
            console.error('로그인 요청 실패:', error);
            alert('로그인 요청 중 문제가 발생했습니다.');
        }
    });
});

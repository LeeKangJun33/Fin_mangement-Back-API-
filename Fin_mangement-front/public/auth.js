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
            const response = await fetch('http://localhost:8080/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password }),
            });

            if (response.ok) {
                const data = await response.json(); // 응답에서 JWT 토큰 받기
                localStorage.setItem("token", data.token); // JWT 토큰 저장
                window.location.href = "dashboard.html"; // 로그인 성공 시 메인 페이지로 이동
            } else {
                alert("로그인 실패: " + (await response.text()));
            }
        } catch (error) {
            console.error("로그인 요청 중 오류 발생:", error);
            alert("로그인 중 오류가 발생했습니다.");
        }
    });
});

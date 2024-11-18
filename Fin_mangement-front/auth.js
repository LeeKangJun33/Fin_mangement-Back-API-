document.getElementById("registerForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const email = document.getElementById("email").value;

    try {
        const response = await fetch("http://localhost:8080/auth/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ username, password, email }),
        });

        if (response.ok) {
            console.log("Registration successful");
            window.location.href = "/auth/login.html";
        } else {
            console.error("Registration failed.");
        }
    } catch (error) {
        console.error("Error during registration:", error);
    }
});

document.addEventListener("DOMContentLoaded", async () => {
    const ctx = document.getElementById("myChart").getContext("2d");

    try {
        const token = localStorage.getItem("jwtToken");
        const response = await fetch("http://localhost:8080/api/budget-goals", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            const data = await response.json();

            new Chart(ctx, {
                type: "bar",
                data: {
                    labels: data.map(goal => goal.category),
                    datasets: [{
                        label: "Budget Goals",
                        data: data.map(goal => goal.amount),
                        backgroundColor: "rgba(75, 192, 192, 0.2)",
                        borderColor: "rgba(75, 192, 192, 1)",
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: { beginAtZero: true }
                    }
                }
            });
        } else {
            console.error("Failed to fetch budget goals");
        }
    } catch (error) {
        console.error("Error loading chart:", error);
    }
});

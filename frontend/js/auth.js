

const form = document.getElementById("login-form");

form.addEventListener("submit", async (e) => {
    
    e.preventDefault();

    const username = form.username.value;
    const password = form.password.value;

    try {
        
        const res = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: { 
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*",
            }, 
            body: JSON.stringify({ username, password })
        });


        if (!res.ok) throw new Error("Login Failed! Check your credentials please.")

        const data = await res.json();
        localStorage.setItem("token", data.token);
        console.log(localStorage.getItem("token"));
        window.location.href = "dashboard.html";
        

    } catch (error) {

        alert("Login failed! Please check your credentials.")
        
    }
});
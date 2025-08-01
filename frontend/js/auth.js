

const loginForm = document.getElementById("login-form");
const registerForm = document.getElementById("register-form");

if (loginForm) {

  loginForm.addEventListener("submit", async (e) => {

    e.preventDefault();
    
    const username = loginForm.username.value;
    const password = loginForm.password.value;

    try {

      const res = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });

      if (!res.ok) throw new Error("Login failed");

      const data = await res.json();

      localStorage.setItem("token", data.token);
      window.location.href = "dashboard.html";

    } catch (err) {
      alert("Login failed: " + err.message);
    }
  });
}

if (registerForm) {

  registerForm.addEventListener("submit", async (e) => {

    e.preventDefault();

    const username = registerForm.username.value.trim();
    const password = registerForm.password.value;
    const confirm = registerForm.confirm.value;

    if (password !== confirm) {
      alert("Passwords do not match!");
      return;
    }

    try {

      const res = await fetch("http://localhost:8080/api/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });

      if (!res.ok) throw new Error("Registration failed");

      const data = await res.json();

      localStorage.setItem("token", data.token);
      window.location.href = "dashboard.html";
      
    } catch (err) {
      alert("Registration failed: " + err.message);
    }
  });
}


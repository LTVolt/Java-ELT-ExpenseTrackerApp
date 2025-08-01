const token = localStorage.getItem("token");
if (!token) {
  window.location.href = "index.html";
}

const expenseForm = document.getElementById("expense-form");
const tableBody = document.getElementById("expenses-table");
const originalSubmit = expenseForm.onsubmit;

document.getElementById("logout").addEventListener("click", () => {
  localStorage.removeItem("token");
  window.location.href = "index.html";
});


expenseForm.addEventListener("submit", async (e) => {
  e.preventDefault();
  const form = e.target;

  const expense = {
    description: form.description.value,
    amount: parseFloat(form.amount.value).toFixed(2),
    date: form.date.value,
    category: form.category.value,
  };

  try {
    const res = await fetch("http://localhost:8080/api/expenses", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(expense),
    });

    if (!res.ok) throw new Error("Failed to add expense");

    form.reset();
    loadExpenses();
  } catch (err) {
    alert("Error adding expense");
  }
});

async function loadExpenses() {
  try {
    const res = await fetch("http://localhost:8080/api/expenses", {
      headers: { Authorization: `Bearer ${token}` },
    });

    if (!res.ok) throw new Error("Failed to fetch");

    const data = await res.json();
    tableBody.innerHTML = "";
    data.forEach((exp) => {
      const row = document.createElement("tr");
      row.classList.add("text-center");
      row.innerHTML = `
        <td>${exp.description}</td>
        <td>$${exp.amount}</td>
        <td>${exp.date}</td>
        <td>${exp.category}</td>
        <td class="d-flex gap-2 justify-content-center" >
        <button onclick="editExpense(${exp.id} )" class="btn btn-warning">Edit</button>
          <button onclick="deleteExpense(${exp.id} )" class="btn btn-warning">Delete</button>
        </td>
      </tr>
      `;
      tableBody.appendChild(row);
    });
  } catch (err) {
    alert("Error loading expenses");
  }
}

async function deleteExpense(id) {
    
    if(!confirm("Are you sure you want to delete this expense? There is no way to get it back!")) return;

    try {
        
        await fetch(`http://localhost:8080/api/expenses/${id}`, {
            method: 'DELETE',
            headers: { Authorization: `Bearer ${token}` },
        });
        loadExpenses();
    } catch (error) {

        alert("Error loading expenses: " + error);
        
    }
}

async function editExpense(id) {

    try {
        
        const res = await fetch(`http://localhost:8080/api/expenses`, {

            headers: { Authorization: `Bearer ${token}` },
        });

        const expenses = await res.json();
        const currentExpense = expenses.find(e => e.id === id);

        if (!currentExpense) return alert("Invalid expense.");

        const form = document.getElementById("expense-form");
            form.description.value = currentExpense.description;
            form.amount.value = currentExpense.amount;
            form.date.value = currentExpense.date;
            form.category.value = currentExpense.category;

        form.onsubmit = async function (e) {

            e.preventDefault();

            const updatedExpense = {
                description: form.description.value,
                amount: parseFloat(form.amount.value),
                date: form.date.value,
                category: form.category.value,
            };

            try {
                
                await fetch(`http://localhost:8080/api/expenses/${id}`, {

                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                    body: JSON.stringify(updatedExpense),
                });

                form.reset();
                loadExpenses();
                form.onsubmit = originalSubmit;

            } catch (error) {
                
                alert("Failed to update expense! " + error);

                }
            };

    } catch (error) {
        
        alert("Edit failed... " + error);
    } 
}

const summaryForm = document.getElementById("summary-form");
const summaryTable = document.getElementById("summary-table");
const summaryBody = document.getElementById("summary-body");

summaryForm.addEventListener("submit", async function (e) {
  e.preventDefault();
  const form = e.target;
  const month = form.month.value;
  const year = form.year.value;

  try {
    const res = await fetch(`http://localhost:8080/api/expenses/summary?month=${month}&year=${year}`, {
      headers: { Authorization: `Bearer ${token}` },
    });

    if (!res.ok) throw new Error("Failed to fetch summary");

    const summary = await res.json();
    summaryBody.innerHTML = "";

    Object.entries(summary).forEach(([category, total]) => {
      const row = `<tr>
        <td>${category}</td>
        <td>$${Number(total).toFixed(2)}</td>
      </tr>`;
      summaryBody.insertAdjacentHTML("beforeend", row);
    });

    summaryTable.style.display = "table";
  } catch (err) {
    alert("Error fetching summary");
  }
});


loadExpenses();

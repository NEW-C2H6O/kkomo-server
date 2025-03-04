document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("get-code").addEventListener("click", function () {
        fetch("/v1/admin/activity-code")
            .then(response => response.json())
            .then(data => {
                document.getElementById("result").innerHTML = `
                <div class="alert alert-success text-center p-3">
                    <h3>발급 코드:</h3>
                    <span class="badge badge-primary font-weight-bold" style="font-size: 4rem;">
                        ${data.data.code}
                    </span>
                </div>
            `;
            })
            .catch(error => {
                console.error("요청 실패", error);
                document.getElementById("result").textContent = "오류 발생!";
            });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("generate-code").addEventListener("click", function () {
        fetch("/v1/admin/activity-code", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById("result").innerHTML = `
                <div class="alert alert-success text-center p-3">
                    <h3>발급 코드:</h3>
                    <span class="badge badge-primary font-weight-bold" style="font-size: 4rem;">
                        ${data.data.code}
                    </span>
                </div>`
            })
            .catch(error => {
                console.error("요청 실패", error);
                document.getElementById("result").textContent = "오류 발생!";
            });
    });
});

const BASE_URL = "http://localhost:8080"
async function login(){
    const username = document.querySelector("#id1").value();
    const pwd = document.querySelector("#id2").value();
    const data = {
        username : username,
        password : pwd
    }
    const test = await request(`${BASE_URL}/login`,HTTP_METHOD.POST(data));
    console.log(test);
}

const HTTP_METHOD = {
    POST(data) {
        return {
            method: "POST",
            headers: {"Content-Type" : "application/json"},
            body: JSON.stringify(data),
        };
    },
}
const request = async (url, option) => {
    const response = await fetch(url, option);
    return response.json()
}


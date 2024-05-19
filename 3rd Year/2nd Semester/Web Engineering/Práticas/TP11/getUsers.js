var axios = require('axios')

axios.post('http://localhost:8002/users/login', {username: "jcr", password: "123"})
    .then(response => {
        console.log("Token: " + response.data.token)
        let myToken = response.data.token
        axios.get('http://localhost:8002/users?token=' + myToken)
            .then(response2 => {
                console.log(response2.data)
            })
            .catch(e => console.log("Erro: " + e))
    })
    .catch(e => console.log("Erro: " + e))
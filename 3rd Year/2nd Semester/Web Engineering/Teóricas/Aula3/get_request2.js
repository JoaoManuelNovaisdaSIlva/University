const axios = require('axios');

axios.get('http://localhost:3000/alunos/101')
    .then(resp => {
    data = resp.data;
    console.log(JSON.stringify(data));
    })
    .catch(error => {
        console.log(error);
 }); 

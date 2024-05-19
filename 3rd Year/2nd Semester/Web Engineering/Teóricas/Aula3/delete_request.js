const axios = require('axios');

axios.delete('http://localhost:3000/alunos/101', {
    }).then(resp => {
    console.log(resp.data);
    }).catch(error => {
    console.log(error);
});

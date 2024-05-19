const axios = require('axios');

axios.post('http://localhost:3000/alunos', {
    "id": "101",
    "nome": "novo",
    "dataNasc": "1999-4-19",
    "curso": "CB8",
    "anoCurso": "5",
    "instrumento": "Guitarra"
    }).then(resp => {
    console.log(resp.data);
    }).catch(error => {
    console.log(error);
});
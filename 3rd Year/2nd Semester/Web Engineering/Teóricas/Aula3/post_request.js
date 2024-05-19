const axios = require('axios');

axios.post('http://localhost:3000/instrumentos', {
    "id": "I23",
    "#text": "Castanholas"
    }).then(resp => {
    console.log(resp.data);
    }).catch(error => {
    console.log(error);
});